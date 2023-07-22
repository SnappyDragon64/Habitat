package mod.schnappdragon.habitat.common.entity.animal;

import mod.schnappdragon.habitat.common.entity.IHabitatShearable;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Pooka extends Rabbit implements IHabitatShearable {
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

    @Override
    public boolean isShearable(@Nonnull ItemStack item, Level world, BlockPos pos) {
        return this.isAlive() && !this.isBaby();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
        return onSheared(player, item, world, pos, fortune, SoundSource.PLAYERS);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune, SoundSource source) {
        this.level().gameEvent(player, GameEvent.SHEAR, pos);
        world.playSound(null, this, HabitatSoundEvents.POOKA_SHEAR.get(), source, 1.0F, 0.8F + this.random.nextFloat() * 0.4F);

        if (!this.level().isClientSide()) {
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.discard();

            Rabbit rabbit = EntityType.RABBIT.create(this.level());
            rabbit.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            rabbit.setHealth(this.getHealth());
            rabbit.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                rabbit.setCustomName(this.getCustomName());
                rabbit.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired())
                rabbit.setPersistenceRequired();

            rabbit.setVariant(this.getVariant());
            rabbit.setBaby(this.isBaby());
            rabbit.setInvulnerable(this.isInvulnerable());
            world.addFreshEntity(rabbit);
        }

        return List.of(new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get(), 2));
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