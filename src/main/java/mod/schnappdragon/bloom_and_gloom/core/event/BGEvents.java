package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal.BGFindPollinationTargetGoal;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.capabilities.classes.ConsumedFairyRingMushroom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

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
            ItemStack stack = event.getItemStack();
            if (!mooshroom.isChild()) {
                if (stack.getItem() == Items.BOWL) {
                    boolean giveStew = false;

                    if (mooshroom.hasStewEffect == null && mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().getConsumedFairyRingMushroom()) {
                        event.setCancellationResult(ActionResultType.SUCCESS);
                        event.setCanceled(true);
                        giveStew = true;
                    }

                    if (!event.getWorld().isRemote) {
                        mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().setConsumedFairyRingMushroom(false);
                        if (giveStew)
                            event.getPlayer().setHeldItem(event.getHand(), DrinkHelper.fill(stack, event.getPlayer(), new ItemStack(BGItems.FAIRY_RING_MUSHROOM_STEW.get()), false));
                    }
                }

                else if (mooshroom.getMooshroomType() == MooshroomEntity.Type.BROWN && stack.getItem().isIn(ItemTags.SMALL_FLOWERS) && mooshroom.hasStewEffect == null && mooshroom.getCapability(ConsumedFairyRingMushroom.Provider.CONSUMED_FAIRY_RING_MUSHROOM_CAPABILITY).resolve().get().getConsumedFairyRingMushroom()) {
                    event.setCancellationResult(ActionResultType.SUCCESS);
                    event.setCanceled(true);

                    Optional<Pair<Effect, Integer>> optional = Optional.empty();
                    if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof FlowerBlock) {
                        FlowerBlock flower = (FlowerBlock) ((BlockItem) stack.getItem()).getBlock();
                        optional = Optional.of(Pair.of(flower.getStewEffect(), flower.getStewEffectDuration() * 2));
                    }

                    if (optional.isPresent()) {
                        Pair<Effect, Integer> pair = optional.get();
                        if (!event.getPlayer().abilities.isCreativeMode)
                            stack.shrink(1);

                        for (int j = 0; j < 4; ++j)
                            mooshroom.world.addParticle(ParticleTypes.EFFECT, mooshroom.getPosX() + mooshroom.getRNG().nextDouble() / 2.0D, mooshroom.getPosYHeight(0.5D), mooshroom.getPosZ() + mooshroom.getRNG().nextDouble() / 2.0D, 0.0D, mooshroom.getRNG().nextDouble() / 5.0D, 0.0D);

                        mooshroom.hasStewEffect = pair.getLeft();
                        mooshroom.effectDuration = pair.getRight();
                        mooshroom.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0F, 1.0F);
                    }
                }
            }
        }
    }
}