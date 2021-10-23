package mod.schnappdragon.habitat.common.entity.monster;

import mod.schnappdragon.habitat.core.registry.HabitatCriterionTriggers;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PookaEntity extends RabbitEntity implements IMob, IForgeShearable {
    private static final DataParameter<Boolean> PACIFIED = EntityDataManager.createKey(PookaEntity.class, DataSerializers.BOOLEAN);
    private int aidId;
    private int aidDuration;
    private int ailmentId;
    private int ailmentDuration;

    public PookaEntity(EntityType<? extends PookaEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PookaEntity.PanicGoal(this, 2.2D));
        this.targetSelector.addGoal(1, (new PookaEntity.HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, RabbitEntity.class, 10, true, false, livingEntity -> livingEntity.getType() == EntityType.RABBIT));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, WolfEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new PookaEntity.TemptGoal(this, 1.0D, Ingredient.fromTag(HabitatItemTags.POOKA_PACIFICATION_FOOD), false));
        this.goalSelector.addGoal(4, new PookaEntity.AttackGoal(this));
        this.goalSelector.addGoal(4, new PookaEntity.AvoidEntityGoal<>(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new PookaEntity.AvoidEntityGoal<>(this, IronGolemEntity.class, 4.0F, 2.2D, 2.2D));
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
     * Data Methods
     */

    protected void registerData() {
        super.registerData();
        this.dataManager.register(PACIFIED, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("aidId", this.aidId);
        compound.putInt("aidDuration", this.aidDuration);
        compound.putInt("ailmentId", this.ailmentId);
        compound.putInt("ailmentDuration", this.ailmentDuration);
        compound.putBoolean("isPacified", this.isPacified());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAidAndAilment(
                compound.getInt("aidId"),
                compound.getInt("aidDuration"),
                compound.getInt("ailmentId"),
                compound.getInt("ailmentDuration")
        );
        this.setPacified(compound.getBoolean("isPacified"));
    }

    private void setAidAndAilment(int aidI, int aidD, int ailI, int ailD) {
        this.aidId = aidI;
        this.aidDuration = aidD;
        this.ailmentId = ailI;
        this.ailmentDuration = ailD;
    }

    public void setPacified(boolean isPacified) {
        this.dataManager.set(PACIFIED, isPacified);
    }

    public boolean isPacified() {
        return this.dataManager.get(PACIFIED);
    }

    /*
     * Conversion Methods
     */

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return this.isPacified();
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
        return Collections.singletonList(new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get()));
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

        pooka.enablePersistence();

        Pair<Integer, Integer> aid = pooka.getRandomAid();
        Pair<Integer, Integer> ailment = pooka.getRandomAilment();
        pooka.setAidAndAilment(aid.getLeft(), aid.getRight(), ailment.getLeft(), ailment.getRight());

        pooka.setRabbitType(rabbit.getRabbitType());
        pooka.setChild(rabbit.isChild());
        pooka.setInvulnerable(rabbit.isInvulnerable());
        return pooka;
    }

    /*
     * Sound Methods
     */

    public SoundCategory getSoundCategory() {
        return this.isPacified() ? SoundCategory.NEUTRAL : SoundCategory.HOSTILE;
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
     * Taming Methods
     */

    @Override
    public ActionResultType applyPlayerInteraction(PlayerEntity player, Vector3d vec, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!this.world.isRemote && stack.getItem().isIn(HabitatItemTags.POOKA_PACIFICATION_FOOD)) {
            this.enablePersistence();
            if (!player.abilities.isCreativeMode)
                stack.shrink(1);
            int roll = rand.nextInt(5);

            if (this.isPacified())
                this.heal((float) stack.getItem().getFood().getHealing());
            else if ((this.isChild() && roll > 0 || roll == 0) && this.isAlone()) {
                this.setPacified(true);
                HabitatCriterionTriggers.PACIFY_POOKA.trigger((ServerPlayerEntity) player);
                this.enablePersistence();
                this.navigator.clearPath();
                this.setAttackTarget(null);
                this.setRevengeTarget(null);
                for (int i = 0; i < 5; i++)
                    ((ServerWorld) this.world).spawnParticle(ParticleTypes.HEART, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), 0, this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian(), 0.02D);
            }
            else {
                for (int i = 0; i < 5; i++)
                    ((ServerWorld) this.world).spawnParticle(ParticleTypes.SMOKE, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), 0, this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian(), 0.02D);
            }

            return ActionResultType.SUCCESS;
        }

        return super.applyPlayerInteraction(player, vec, hand);
    }

    private boolean isAlone() {
        return this.world.getEntitiesWithinAABB(PookaEntity.class, this.getBoundingBox().grow(16.0D, 4.0D, 16.0D), pooka -> !pooka.isPacified()).size() == 1;
    }

    /*
     * Breeding Methods
     */

    @Override
    public PookaEntity func_241840_a(ServerWorld serverWorld, AgeableEntity entity) {
        PookaEntity pooka = HabitatEntityTypes.POOKA.get().create(serverWorld);
        int i = this.getRandomRabbitType(serverWorld);
        boolean pacified = false;

        Pair<Integer, Integer> aid = this.getRandomAid();
        int aidI = aid.getLeft();
        int aidD = aid.getRight();

        Pair<Integer, Integer> ailment = this.getRandomAilment();
        int ailI = ailment.getLeft();
        int ailD = ailment.getRight();

        if (entity instanceof PookaEntity) {
            PookaEntity parent = ((PookaEntity) entity);
            pacified = this.isPacified() && parent.isPacified();

            if (this.rand.nextInt(20) != 0) {
                if (this.rand.nextBoolean())
                    i = parent.getRabbitType();
                else
                    i = this.getRabbitType();
            }

            if (this.rand.nextInt(20) != 0) {
                if (this.rand.nextBoolean()) {
                    aidI = parent.aidId;
                    aidD = parent.aidDuration;
                }
                else {
                    aidI = this.aidId;
                    aidD = this.aidDuration;
                }
            }

            if (this.rand.nextInt(20) != 0) {
                if (this.rand.nextBoolean()) {
                    ailI = parent.ailmentId;
                    ailD = parent.ailmentDuration;
                }
                else {
                    ailI = this.ailmentId;
                    ailD = this.ailmentDuration;
                }
            }
        }

        pooka.setRabbitType(i);
        pooka.setPacified(pacified);
        pooka.setAidAndAilment(aidI, aidD, ailI, ailD);
        pooka.enablePersistence();
        return pooka;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem().isIn(HabitatItemTags.POOKA_BREEDING_FOOD);
    }

    /*
     * Spawn Methods
     */

    public static boolean canPookaSpawn(EntityType<PookaEntity> pooka, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        Pair<Integer, Integer> aid = this.getRandomAid();
        Pair<Integer, Integer> ailment = this.getRandomAilment();
        boolean pacified = false;
        int i = this.getRandomRabbitType(worldIn);
        int aidI = aid.getLeft();
        int aidD = aid.getRight();
        int ailI = ailment.getLeft();
        int ailD = ailment.getRight();
        if (spawnDataIn instanceof PookaEntity.PookaData) {
            PookaEntity.PookaData data = (PookaEntity.PookaData) spawnDataIn;
            i = data.typeData;
            aidI = data.aidIdData;
            aidD = data.aidDurationData;
            ailI = data.ailmentIdData;
            ailD = data.ailmentDurationData;
            pacified = data.pacifiedData;
        }
        else
            spawnDataIn = new PookaEntity.PookaData(i, aidI, aidD, ailI, ailD, false);

        this.setRabbitType(i);
        this.setAidAndAilment(aidI, aidD, ailI, ailD);
        this.setPacified(pacified);
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

    private Pair<Integer, Integer> getRandomAid() {
        List<Pair<Integer, Integer>> pairs = Arrays.asList(
                Pair.of(12, 40),
                Pair.of(8, 60),
                Pair.of(10, 80)
        );

        return pairs.get(this.rand.nextInt(3));
    }

    private Pair<Integer, Integer> getRandomAilment() {
        List<Pair<Integer, Integer>> pairs = Arrays.asList(
                Pair.of(15, 80),
                Pair.of(19, 120),
                Pair.of(18, 90)
        );

        return pairs.get(this.rand.nextInt(3));
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

            for (int j = 0; j < 8; j++)
                ((ServerWorld) this.world).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getPosXRandom(0.5D), rabbit.getPosYHeight(0.5D), rabbit.getPosZRandom(0.5D), 0, rabbit.getRNG().nextGaussian(), 0.0D, rabbit.getRNG().nextGaussian(), 0.01D);
            return false;
        }

        if (!this.isChild() && entityIn instanceof LivingEntity) {
            Effect effect = Effect.get(ailmentId);
            if (effect != null) {
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(effect, ailmentDuration * (this.world.getDifficulty() == Difficulty.HARD ? 2 : 1)));

                for (int i = 0; i < 2; i++)
                    ((ServerWorld) this.world).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), entityIn.getPosXRandom(0.5D), entityIn.getPosYHeight(0.5D), entityIn.getPosZRandom(0.5D), 0, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian(), 0.01D);
            }
        }

        this.playSound(HabitatSoundEvents.ENTITY_POOKA_ATTACK.get(), 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        Effect effect = Effect.get(aidId);
        if (!this.isChild() && effect != null) {
            this.addPotionEffect(new EffectInstance(effect, aidDuration * (this.world.getDifficulty() == Difficulty.HARD ? 2 : 1)));
            if (!this.world.isRemote) {
                for (int i = 0; i < 2; i++)
                    ((ServerWorld) this.world).spawnParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), this.getPosXRandom(0.5D), this.getPosYHeight(0.5D), this.getPosZRandom(0.5D), 0, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian(), 0.01D);
            }
        }

        if (this.isPacified() && source.getTrueSource() instanceof PlayerEntity && !source.isCreativePlayer()) {
            this.setPacified(false);
            if (!this.world.isRemote) {
                for (int i = 0; i < 5; i++)
                    ((ServerWorld) this.world).spawnParticle(ParticleTypes.ANGRY_VILLAGER, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), 0, this.rand.nextGaussian(), this.rand.nextGaussian(), this.rand.nextGaussian(), 0.02D);
            }
        }

        return !this.isInvulnerableTo(source) && super.attackEntityFrom(source, amount);
    }

    /*
     * Data
     */

    public static class PookaData extends RabbitEntity.RabbitData {
        int aidIdData;
        int aidDurationData;
        int ailmentIdData;
        int ailmentDurationData;
        boolean pacifiedData;

        public PookaData(int type, int aidId, int aidDuration, int ailmentId, int ailmentDuration, boolean pacified) {
            super(type);
            aidIdData = aidId;
            aidDurationData = aidDuration;
            ailmentIdData = ailmentId;
            ailmentDurationData = ailmentDuration;
            pacifiedData = pacified;
        }
    }

    /*
     * AI Goals
     */

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

    static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
        private final PookaEntity pooka;

        public TemptGoal(PookaEntity pooka, double speed, Ingredient temptItem, boolean scaredByMovement) {
            super(pooka, speed, temptItem, scaredByMovement);
            this.pooka = pooka;
        }

        public boolean shouldExecute() {
            return this.pooka.isPacified() && super.shouldExecute();
        }
    }

    static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        private final PookaEntity pooka;

        public HurtByTargetGoal(PookaEntity pooka) {
            super(pooka);
            this.pooka = pooka;
        }

        public boolean shouldExecute() {
            return !this.pooka.isPacified() && super.shouldExecute();
        }

        public boolean shouldContinueExecuting() {
            return !this.pooka.isPacified() && super.shouldContinueExecuting();
        }
    }

    static class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final PookaEntity pooka;

        public AvoidEntityGoal(PookaEntity pooka, Class<T> entity, float range, double v1, double v2) {
            super(pooka, entity, range, v1, v2);
            this.pooka = pooka;
        }

        public boolean shouldExecute() {
            return this.pooka.isPacified() && super.shouldExecute();
        }
    }

    static class AttackGoal extends MeleeAttackGoal {
        private final PookaEntity pooka;

        public AttackGoal(PookaEntity pooka) {
            super(pooka, 1.4D, true);
            this.pooka = pooka;
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getWidth();
        }

        public boolean shouldExecute() {
            return !pooka.isPacified() && super.shouldExecute();
        }

        public boolean shouldContinueExecuting() {
            return !pooka.isPacified() && super.shouldContinueExecuting();
        }
    }
}