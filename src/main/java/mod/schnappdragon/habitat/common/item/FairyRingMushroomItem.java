package mod.schnappdragon.habitat.common.item;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FairyRingMushroomItem extends BlockItem {
    public FairyRingMushroomItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!playerIn.world.isRemote && target instanceof MooshroomEntity && ((MooshroomEntity) target).getMooshroomType() == MooshroomEntity.Type.BROWN) {
            MooshroomEntity mooshroom = (MooshroomEntity) target;
            if (mooshroom.hasStewEffect == null) {
                List<Item> flowers = ItemTags.SMALL_FLOWERS.getAllElements().stream().filter((item) -> item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof FlowerBlock).collect(Collectors.toList());
                Optional<Item> flower = flowers.stream().skip((int) (flowers.size() * Math.random())).findFirst();

                if (flower.isPresent()) {
                    FlowerBlock flowerBlock = (FlowerBlock) ((BlockItem) flower.get()).getBlock();
                    mooshroom.hasStewEffect = flowerBlock.getStewEffect();
                    mooshroom.effectDuration = flowerBlock.getStewEffectDuration() * 2;

                    if (!playerIn.abilities.isCreativeMode)
                        stack.shrink(1);

                    for (int j = 0; j < 4; ++j) {
                        ((ServerWorld) playerIn.world).spawnParticle(ParticleTypes.EFFECT, mooshroom.getPosX() + mooshroom.getRNG().nextDouble() / 2.0D, mooshroom.getPosYHeight(0.5D), mooshroom.getPosZ() + mooshroom.getRNG().nextDouble() / 2.0D, 0, 0.0D, mooshroom.getRNG().nextDouble(), 0.0D, 0.2D);
                        ((ServerWorld) playerIn.world).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), mooshroom.getPosX() + mooshroom.getRNG().nextDouble() / 2.0D, mooshroom.getPosYHeight(0.5D), mooshroom.getPosZ() + mooshroom.getRNG().nextDouble() / 2.0D, 0, mooshroom.getRNG().nextGaussian(), 0.0D, mooshroom.getRNG().nextGaussian(), 0.01D);
                    }

                    mooshroom.world.playSound(null, mooshroom.getPosX(), mooshroom.getPosY(), mooshroom.getPosZ(), SoundEvents.ENTITY_MOOSHROOM_EAT, mooshroom.getSoundCategory(), 2.0F, 1.0F);
                    return ActionResultType.SUCCESS;
                }
            }

            for (int i = 0; i < 2; ++i)
                ((ServerWorld) playerIn.world).spawnParticle(ParticleTypes.SMOKE, mooshroom.getPosX() + mooshroom.getRNG().nextDouble() / 2.0D, mooshroom.getPosYHeight(0.5D), mooshroom.getPosZ() + mooshroom.getRNG().nextDouble() / 2.0D, 0, 0.0D, mooshroom.getRNG().nextDouble(), 0.0D, 0.2D);
            return ActionResultType.SUCCESS;
        }

        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }
}