package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal.BGFindPollinationTargetGoal;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.capabilities.classes.ConsumedFairyRingMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.Items;
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
     * Used to reset the ConsumedFairyRingMushroom capability when the mooshroom is milked.
     */

    @SubscribeEvent
    public static void resetMooshroomCapability(PlayerInteractEvent.EntityInteract event) {
        if (event.getItemStack().getItem() == Items.BOWL && event.getTarget().getType() == EntityType.MOOSHROOM) {
            MooshroomEntity mooshroom = (MooshroomEntity) event.getTarget();
            if (!mooshroom.isChild() && mooshroom.hasStewEffect != null)
                mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().setConsumedFairyRingMushroom(false);
        }
    }
}