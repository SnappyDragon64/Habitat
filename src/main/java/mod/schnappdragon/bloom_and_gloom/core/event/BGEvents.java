package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal.BGFindPollinationTargetGoal;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.capabilities.classes.ConsumedFairyRingMushroom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGEvents {

    /*
     * Used to add BGFindPollinationTargetGoal to bees on spawn.
     */

    @SubscribeEvent
    public static void addPollinationGoal(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.BEE) {
            BeeEntity bee = (BeeEntity) entity;
            bee.goalSelector.addGoal(7, new BGFindPollinationTargetGoal(bee));
        }
    }

    /*
     * Used to add and modify mooshroom interaction with fairy ring mushrooms.
     */

    @SubscribeEvent
    public static void modifyMooshroomInteraction(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getTarget().getType() == EntityType.MOOSHROOM) {
            MooshroomEntity mooshroom = (MooshroomEntity) event.getTarget();
            if (!mooshroom.isChild()) {
                if (event.getItemStack().getItem() == Items.BOWL) {
                    boolean giveStew = false;

                    if (mooshroom.hasStewEffect == null && mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().getConsumedFairyRingMushroom()) {
                        event.setCancellationResult(ActionResultType.SUCCESS);
                        event.setCanceled(true);
                        giveStew = true;
                    }

                    if (!event.getWorld().isRemote) {
                        mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().setConsumedFairyRingMushroom(false);
                        if (giveStew)
                            event.getPlayer().setHeldItem(event.getHand(), DrinkHelper.fill(event.getItemStack(), event.getPlayer(), new ItemStack(BGItems.FAIRY_RING_MUSHROOM_STEW.get()), false));
                    }
                }
            }
        }
    }
}