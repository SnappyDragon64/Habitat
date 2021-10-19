package mod.schnappdragon.habitat.common.entity.monster;

import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PookaEntity extends RabbitEntity implements IMob, IForgeShearable {
    public PookaEntity(EntityType<? extends PookaEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PookaEntity.PanicGoal(this, 2.2D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WolfEntity.class, true));
        this.goalSelector.addGoal(4, new PookaEntity.AttackGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 3.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F)
                .createMutableAttribute(Attributes.ARMOR, 8.0D);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(HabitatItems.POOKA_SPAWN_EGG.get());
    }

    /*
     * Conversion Methods
     */

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, HabitatSoundEvents.ENTITY_POOKA_SHEAR.get(), SoundCategory.HOSTILE, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        if (!this.world.isRemote()) {
            ((ServerWorld) this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5D), this.getPosZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.remove();
            world.addEntity(convertPooka(this));
        }
        return Collections.singletonList(new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get(), 2));
    }

    public static RabbitEntity convertPooka(PookaEntity pooka) {
        RabbitEntity rabbit = EntityType.RABBIT.create(pooka.world);
        rabbit.setLocationAndAngles(pooka.getPosX(), pooka.getPosY(), pooka.getPosZ(), pooka.rotationYaw, pooka.rotationPitch);
        rabbit.setHealth(pooka.getHealth());
        rabbit.renderYawOffset = pooka.renderYawOffset;
        if (pooka.hasCustomName()) {
            rabbit.setCustomName(pooka.getCustomName());
            rabbit.setCustomNameVisible(pooka.isCustomNameVisible());
        }

        if (pooka.isNoDespawnRequired()) {
            rabbit.enablePersistence();
        }

        rabbit.setRabbitType(pooka.getRabbitType());
        rabbit.setChild(pooka.isChild());
        rabbit.setInvulnerable(pooka.isInvulnerable());
        return rabbit;
    }

    public static PookaEntity convertRabbit(RabbitEntity rabbit) {
        PookaEntity pooka = HabitatEntityTypes.POOKA.get().create(rabbit.world);
        pooka.setLocationAndAngles(rabbit.getPosX(), rabbit.getPosY(), rabbit.getPosZ(), rabbit.rotationYaw, rabbit.rotationPitch);
        pooka.setHealth(rabbit.getHealth());
        pooka.renderYawOffset = rabbit.renderYawOffset;
        if (rabbit.hasCustomName()) {
            pooka.setCustomName(rabbit.getCustomName());
            pooka.setCustomNameVisible(rabbit.isCustomNameVisible());
        }

        if (rabbit.isNoDespawnRequired())
            pooka.enablePersistence();

        pooka.setRabbitType(rabbit.getRabbitType());
        pooka.setChild(rabbit.isChild());
        pooka.setInvulnerable(rabbit.isInvulnerable());
        return pooka;
    }

    /*
     * Sound Methods
     */

    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEvent getJumpSound() {
        return HabitatSoundEvents.ENTITY_POOKA_JUMP.get();
    }

    protected SoundEvent getAmbientSound() {
        return HabitatSoundEvents.ENTITY_POOKA_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return HabitatSoundEvents.ENTITY_POOKA_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return HabitatSoundEvents.ENTITY_POOKA_DEATH.get();
    }

    /*
     * Breeding Methods
     */

    @Override
    public PookaEntity func_241840_a(ServerWorld serverWorld, AgeableEntity entity) {
        PookaEntity pooka = HabitatEntityTypes.POOKA.get().create(serverWorld);
        int i = this.getRandomRabbitType(serverWorld);
        if (this.rand.nextInt(20) != 0) {
            if (entity instanceof RabbitEntity && this.rand.nextBoolean())
                i = ((RabbitEntity) entity).getRabbitType();
            else
                i = this.getRabbitType();
        }

        pooka.setRabbitType(i);
        return pooka;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    /*
     * Spawn Methods
     */

    public static boolean canPookaSpawn(EntityType<PookaEntity> pooka, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return true;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        int i = this.getRandomRabbitType(worldIn);
        if (spawnDataIn instanceof RabbitEntity.RabbitData)
            i = ((RabbitEntity.RabbitData) spawnDataIn).typeData;
        else
            spawnDataIn = new RabbitEntity.RabbitData(i);

        this.setRabbitType(i);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    private int getRandomRabbitType(IWorld world) {
        Biome biome = world.getBiome(this.getPosition());
        int i = this.rand.nextInt(100);
        if (biome.getPrecipitation() == Biome.RainType.SNOW)
            return i < 80 ? 1 : 3;
        else if (biome.getCategory() == Biome.Category.DESERT)
            return 4;
        else
            return i < 50 ? 0 : (i < 90 ? 5 : 2);
    }

    /*
     * Damage Methods
     */

    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn.getType() == EntityType.RABBIT) {
            RabbitEntity rabbit = (RabbitEntity) entityIn;
            rabbit.playSound(HabitatSoundEvents.ENTITY_RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isChild() ? (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.5F : (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.0F);
            rabbit.remove();
            this.world.addEntity(convertRabbit(rabbit));

            for (int j = 0; j < 8; ++j)
                ((ServerWorld) this.world).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getPosXRandom(0.5D), rabbit.getPosYHeight(0.5D), rabbit.getPosZRandom(0.5D), 0, rabbit.getRNG().nextGaussian(), 0.0D, rabbit.getRNG().nextGaussian(), 0.01D);
            return false;
        }

        this.playSound(HabitatSoundEvents.ENTITY_POOKA_ATTACK.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !this.isInvulnerableTo(source) && super.attackEntityFrom(source, amount);
    }

    static class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
        private final PookaEntity pooka;

        public PanicGoal(PookaEntity pooka, double speedIn) {
            super(pooka, speedIn);
            this.pooka = pooka;
        }

        public void tick() {
            super.tick();
            this.pooka.setMovementSpeed(this.speed);
        }
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(PookaEntity pooka) {
            super(pooka, 1.4D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getWidth();
        }
    }
}