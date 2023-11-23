package mod.schnappdragon.habitat.common.entity.animal;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class Pooka extends Rabbit {
    public Pooka(EntityType<? extends Pooka> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void jumpFromGround() {
        if (!this.level().isClientSide)
            this.level().broadcastEntityEvent(this, (byte) 14);

        super.jumpFromGround();
    }

    public static boolean checkPookaSpawnRules(EntityType<Pooka> entityType, LevelAccessor world, MobSpawnType spawnType, BlockPos pos, RandomSource rand) {
        return world.getBlockState(pos.below()).is(BlockTags.RABBITS_SPAWNABLE_ON) && isBrightEnoughToSpawn(world, pos);
    }

    /*
     * Sound Methods
     */

    protected SoundEvent getJumpSound() {
        return HabitatSoundEvents.POOKA_JUMP.get();
    }

    protected SoundEvent getAmbientSound() {
        return HabitatSoundEvents.POOKA_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return HabitatSoundEvents.POOKA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return HabitatSoundEvents.POOKA_DEATH.get();
    }

    public void handleEntityEvent(byte id) {
        if (id == 14) {
            this.level().addParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), this.random.nextGaussian() * (0.01D), 0.0D, this.random.nextGaussian() * (0.01D));
        } else {
            super.handleEntityEvent(id);
        }
    }
}