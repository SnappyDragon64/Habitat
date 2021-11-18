package mod.schnappdragon.habitat.common.item;

import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.core.HabitatConfig;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class FairyRingMushroomItem extends BlockItem {
    public FairyRingMushroomItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
        if (!playerIn.level.isClientSide) {
            if (target instanceof MushroomCow mooshroom && ((MushroomCow) target).getMushroomType() == MushroomCow.MushroomType.BROWN) {
                if (mooshroom.effect == null) {
                    Pair<MobEffect, Integer> effect = getStewEffect();

                    mooshroom.effect = effect.getLeft();
                    mooshroom.effectDuration = effect.getRight();

                    if (!playerIn.getAbilities().instabuild)
                        stack.shrink(1);

                    for (int i = 0; i < 4; ++i) {
                        ((ServerLevel) playerIn.level).sendParticles(ParticleTypes.EFFECT, mooshroom.getRandomX(0.5D), mooshroom.getY(0.5D), mooshroom.getRandomZ(0.5D), 0, 0.0D, mooshroom.getRandom().nextDouble(), 0.0D, 0.2D);
                        ((ServerLevel) playerIn.level).sendParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), mooshroom.getX() + mooshroom.getRandom().nextDouble() / 2.0D, mooshroom.getY(0.5D), mooshroom.getZ() + mooshroom.getRandom().nextDouble() / 2.0D, 0, mooshroom.getRandom().nextGaussian(), 0.0D, mooshroom.getRandom().nextGaussian(), 0.01D);
                    }

                    playerIn.level.playSound(null, mooshroom, SoundEvents.MOOSHROOM_EAT, mooshroom.getSoundSource(), 2.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }

                for (int i = 0; i < 2; ++i)
                    ((ServerLevel) playerIn.level).sendParticles(ParticleTypes.SMOKE, mooshroom.getX() + mooshroom.getRandom().nextDouble() / 2.0D, mooshroom.getY(0.5D), mooshroom.getZ() + mooshroom.getRandom().nextDouble() / 2.0D, 0, 0.0D, mooshroom.getRandom().nextDouble(), 0.0D, 0.2D);
                return InteractionResult.SUCCESS;
            } else if (target.getType() == EntityType.RABBIT && target.isAlive()) {
                Rabbit rabbit = (Rabbit) target;
                rabbit.playSound(HabitatSoundEvents.RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isBaby() ? (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.5F : (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.0F);
                rabbit.discard();
                Pooka pooka = Pooka.convertRabbit(rabbit);
                playerIn.level.addFreshEntity(pooka);
                playerIn.level.broadcastEntityEvent(pooka, (byte) 15);

                if (!playerIn.getAbilities().instabuild)
                    stack.shrink(1);

                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(stack, playerIn, target, hand);
    }

    public static Pair<MobEffect, Integer> getStewEffect() {
        List<String> stewEffectPairs = Arrays.asList(StringUtils.deleteWhitespace(HabitatConfig.COMMON.suspiciousStewEffects.get()).split(","));
        String[] pair = stewEffectPairs.get((int) (Math.random() * stewEffectPairs.size())).split(":");
        MobEffect effect = MobEffect.byId(Integer.parseInt(pair[0]));

        return Pair.of(effect != null ? effect : MobEffects.GLOWING, Integer.parseInt(pair[1]) * 20);
    }
}