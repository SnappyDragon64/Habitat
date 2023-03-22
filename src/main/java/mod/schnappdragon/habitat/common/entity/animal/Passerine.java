package mod.schnappdragon.habitat.common.entity.animal;

import com.mojang.math.Vector3f;
import mod.schnappdragon.habitat.core.misc.HabitatCriterionTriggers;
import mod.schnappdragon.habitat.core.particles.ColorableParticleOption;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.registry.PasserineVariants;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import mod.schnappdragon.habitat.core.tags.PasserineVariantTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

public class Passerine extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Integer> PREEN_COUNTER = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PECK_COUNTER = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<String> DATA_VARIANT_ID = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> DATA_SLEEPING = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.BOOLEAN);

    private int foodTicks;
    public float flap;
    public float flapSpeed;
    public float initialFlapSpeed;
    public float initialFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public Passerine(EntityType<? extends Passerine> passerine, Level worldIn) {
        super(passerine, worldIn);
        this.moveControl = new PasserineMoveControl(10, false);
        this.lookControl = new PasserineLookControl();
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new Passerine.PasserineTemptGoal(1.0D, Ingredient.of(HabitatItemTags.PASSERINE_FOOD), false));
        this.goalSelector.addGoal(3, new Passerine.FindCoverGoal(1.25D));
        this.goalSelector.addGoal(4, new Passerine.SleepGoal());
        this.goalSelector.addGoal(5, new Passerine.PreenGoal());
        this.goalSelector.addGoal(5, new Passerine.PeckGoal());
        this.goalSelector.addGoal(6, new Passerine.PasserineRandomFlyingGoal(1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new Passerine.PasserineFollowMobGoal(1.0D, 3.0F, 7.0F));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.FLYING_SPEED, 0.8F)
                .add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.3F);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    /*
     * Data Methods
     */

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREEN_COUNTER, 0);
        this.entityData.define(PECK_COUNTER, 0);
        this.entityData.define(DATA_VARIANT_ID, PasserineVariants.COMMON_SPARROW.getId().toString());
        this.entityData.define(DATA_SLEEPING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Variant", this.getVariantId());
        compound.putBoolean("Sleeping", this.isAsleep());
        compound.putInt("FoodTicks", this.foodTicks);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariantId(compound.getString("Variant"));
        this.setSleeping(compound.getBoolean("Sleeping"));
        this.foodTicks = compound.getInt("FoodTicks");
    }

    public void setPreenCounter(int counter) {
        this.entityData.set(PREEN_COUNTER, counter);
    }

    public int getPreenCounter() {
        return this.entityData.get(PREEN_COUNTER);
    }

    public boolean isPreening() {
        return this.getPreenCounter() > 0;
    }

    public void setPeckCounter(int counter) {
        this.entityData.set(PECK_COUNTER, counter);
    }

    public int getPeckCounter() {
        return this.entityData.get(PECK_COUNTER);
    }

    public boolean isPecking() {
        return this.getPeckCounter() > 0;
    }

    public void setVariantId(String id) {
        this.entityData.set(DATA_VARIANT_ID, id);
    }

    public void setVariant(PasserineVariant variant) {
        this.setVariantId(Objects.requireNonNull(this.level.registryAccess().registry(PasserineVariants.PASSERINE_VARIANT_REGISTRY.get().getRegistryKey()).get().getKey(variant)).toString());
    }

    public String getVariantId() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public PasserineVariant getVariant() {
        return Objects.requireNonNullElse(this.level.registryAccess().registry(PasserineVariants.PASSERINE_VARIANT_REGISTRY.get().getRegistryKey()).get().get(new ResourceLocation(this.getVariantId())), PasserineVariants.COMMON_SPARROW.get());
    }

    public void setSleeping(boolean isSleeping) {
        this.entityData.set(DATA_SLEEPING, isSleeping);
    }

    public void sleep() {
        this.setSleeping(true);
    }

    public void wakeUp() {
        this.setSleeping(false);
    }

    public boolean isAsleep() {
        return this.entityData.get(DATA_SLEEPING);
    }

    public void setFoodTimer() {
        this.foodTicks = 6000;
    }

    /*
     * AI Methods
     */

    @Override
    protected void customServerAiStep() {
        if (this.foodTicks > 0)
            foodTicks--;

        super.customServerAiStep();
    }

    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();

        if (this.isAsleep() || this.isImmobile()) {
            this.jumping = false;
            this.xxa = 0.0F;
            this.zza = 0.0F;
        }
    }

    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            if (this.isAsleep() && (this.isFlying() || this.level.isDay() || this.isInPowderSnow || this.isUnsafeAt(this.blockPosition()) || !this.canPerch()))
                this.wakeUp();
        }
    }

    private boolean isUnsafeAt(BlockPos pos) {
        if (this.isGoldfish() || !this.level.isRaining() || !this.level.canSeeSky(pos))
            return false;
        else if (this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY())
            return false;
        else
            return this.level.getBiome(pos).value().getPrecipitation() != Biome.Precipitation.NONE;
    }

    private boolean isActive() {
        return this.level.isDay() && !this.level.isRaining();
    }

    private boolean isNotBusy() {
        return !this.isAsleep() && !this.isPreening() && !this.isPecking();
    }

    private boolean canPerch() {
        return this.level.getBlockState(this.getOnPos()).is(HabitatBlockTags.PASSERINES_PERCHABLE_ON);
    }

    /*
     * Flying Methods
     */

    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    private void calculateFlapping() {
        this.initialFlap = this.flap;
        this.initialFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float) ((double) this.flapSpeed + (double) (!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F)
            this.flapping = 1.0F;

        this.flapping = (float) ((double) this.flapping * 0.9D);
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround && vec3.y < 0.0D)
            this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));

        this.flap += this.flapping * 2.0F;
    }

    protected boolean isFlapping() {
        return this.flyDist > this.nextFlap;
    }

    protected void onFlap() {
        this.playSound(HabitatSoundEvents.PASSERINE_FLAP.get(), 0.1F, 1.0F);
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;

        if (this.isRegularVariant() && random.nextInt(30) == 0)
            this.level.broadcastEntityEvent(this, (byte) 11);
    }

    public boolean isFlying() {
        return !this.onGround;
    }

    /*
     * Interaction Methods
     */

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(HabitatItemTags.PASSERINE_FOOD) && this.isNotBusy()) {
            if (!this.level.isClientSide && this.foodTicks == 0) {
                this.setFoodTimer();
                this.heal(1.0F);
                this.usePlayerItem(player, hand, stack);
                this.level.broadcastEntityEvent(this, (byte) 13);
                this.gameEvent(GameEvent.ENTITY_INTERACT, this);
                HabitatCriterionTriggers.FEED_PASSERINE.trigger((ServerPlayer) player);
                this.playSound(HabitatSoundEvents.PASSERINE_AMBIENT.get(), 1.0F, this.getVoicePitch());
                return InteractionResult.SUCCESS;
            }

            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            }
        }

        return super.mobInteract(player, hand);
    }

    /*
     * Spawn Methods
     */

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        PasserineVariant i = this.getVariantByBiome(worldIn);
        if (spawnDataIn instanceof Passerine.PasserineGroupData data)
            i = data.variant;
        else
            spawnDataIn = new Passerine.PasserineGroupData(i);

        this.setVariant(i);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public PasserineVariant getVariantByBiome(LevelAccessor worldIn) {
        Holder<Biome> biomeHolder = worldIn.getBiome(this.blockPosition());
        Biome biome = biomeHolder.value();
        TagKey<PasserineVariant> tag;

        if (biomeHolder.is(Biomes.FLOWER_FOREST))
            tag = PasserineVariantTags.ALL;
        else if (biomeHolder.is(BiomeTags.IS_JUNGLE))
            tag = PasserineVariantTags.JUNGLE;
        else if (biome.getBaseTemperature() >= 1.0F)
            tag = PasserineVariantTags.HOT;
        else if (biome.getBaseTemperature() < 0.5F)
            tag = PasserineVariantTags.COLD;
        else if (biome.getBaseTemperature() <= 0.6F)
            tag = PasserineVariantTags.TEMPERATE;
        else
            tag = PasserineVariantTags.COMMON;

        Optional<Holder<PasserineVariant>> variantsInTag = worldIn.registryAccess().registry(PasserineVariants.PASSERINE_VARIANT_REGISTRY.get().getRegistryKey()).flatMap(registry -> registry.getTag(tag)).flatMap(holders -> holders.getRandomElement(this.random));
        return variantsInTag.orElse(PasserineVariants.COMMON_SPARROW.getHolder().get()).get();
    }

    public static boolean checkPasserineSpawnRules(EntityType<Passerine> type, LevelAccessor worldIn, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.PASSERINES_SPAWNABLE_ON) && isBrightEnoughToSpawn(worldIn, pos);
    }

    /*
     * Hurt Method
     */

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;
        else {
            if (!this.level.isClientSide) {
                if (this.isRegularVariant() && source.getDirectEntity() != null && !source.isMagic())
                    this.level.broadcastEntityEvent(this, (byte) 12);

                if (this.isAsleep())
                    this.wakeUp();
                else if (this.isPreening())
                    this.setPreenCounter(0);
                else if (this.isPecking())
                    this.setPeckCounter(0);
            }

            return super.hurt(source, amount);
        }
    }

    /*
     * Sound Methods
     */

    public void playAmbientSound() {
        if (!this.isPreening() && !this.isPecking() && this.isActive()) {
            super.playAmbientSound();

            if (!this.level.isClientSide)
                this.level.broadcastEntityEvent(this, (byte) 13);
        }
    }

    public SoundEvent getAmbientSound() {
        return HabitatSoundEvents.PASSERINE_AMBIENT.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return HabitatSoundEvents.PASSERINE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return HabitatSoundEvents.PASSERINE_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(HabitatSoundEvents.PASSERINE_STEP.get(), 0.1F, 1.0F);
    }

    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    /*
     * Entity Event Methods
     */

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 11 -> spawnFeathers(this.getFeather(), 1);
            case 12 -> spawnFeathers(this.getFeather(), 2);
            case 13 -> this.level.addParticle(this.getNote(), this.getRandomX(0.5D), 0.6D + this.getY(), this.getRandomZ(0.5D), this.random.nextDouble(), 0.0D, 0.0D);
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnFeathers(ColorableParticleOption feather, int number) {
        for (int i = 0; i < number; i++)
            this.level.addParticle(feather, this.getRandomX(0.5D), this.getY(this.random.nextDouble() * 0.75D), this.getRandomZ(0.5D), this.random.nextGaussian() * 0.01D, 0.0D, this.random.nextGaussian() * 0.01D);
    }

    private ColorableParticleOption getFeather() {
        return new ColorableParticleOption(HabitatParticleTypes.FEATHER.get(), new Vector3f(Vec3.fromRGB24(this.getVariant().featherColor())));
    }

    private ColorableParticleOption getNote() {
        return new ColorableParticleOption(HabitatParticleTypes.NOTE.get(), new Vector3f(Vec3.fromRGB24(this.getVariant().noteColor())));
    }

    /*
     * Easter Egg Methods
     */

    public boolean isRegularVariant() {
        return !(this.isBerdly() || this.isGoldfish() || this.isTurkey() || this.isFlapjack());
    }

    public boolean isBerdly() {
        return "Berdly".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isGoldfish() {
        return this.getVariantId().equals(PasserineVariants.AMERICAN_GOLDFINCH.getId().toString()) && "Goldfish".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isTurkey() {
        return "Turkey".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isFlapjack() {
        return this.getVariantId().equals(PasserineVariants.NORTHERN_CARDINAL.getId().toString()) && "Flapjack".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    @Override
    public void die(DamageSource source) {
        if (this.isAsleep())
            this.wakeUp();
        else if (this.isPreening())
            this.setPreenCounter(0);
        else if (this.isPecking())
            this.setPeckCounter(0);

        super.die(source);
    }

    /*
     * Breeding Methods
     */

    @Override
    public boolean canMate(Animal animal) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public Passerine getBreedOffspring(ServerLevel worldIn, AgeableMob passerine) {
        return null;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    /*
     * Fall Damage Method
     */

    @Override
    public boolean causeFallDamage(float f, float f1, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pps) {
    }

    /*
     * Data
     */

    public static class PasserineGroupData extends AgeableMob.AgeableMobGroupData {
        public final PasserineVariant variant;

        public PasserineGroupData(PasserineVariant variant) {
            super(false);
            this.variant = variant;
        }
    }


    /*
     * Controllers
     */

    public class PasserineMoveControl extends FlyingMoveControl {
        public PasserineMoveControl(int maxTurns, boolean hoversInPlace) {
            super(Passerine.this, maxTurns, hoversInPlace);
        }

        public void tick() {
            if (Passerine.this.isNotBusy())
                super.tick();
        }
    }

    public class PasserineLookControl extends LookControl {
        public PasserineLookControl() {
            super(Passerine.this);
        }

        public void tick() {
            if (Passerine.this.isNotBusy())
                super.tick();
        }
    }

    /*
     * AI Goals
     */

    class PasserineTemptGoal extends TemptGoal {
        public PasserineTemptGoal(double speedModifier, Ingredient items, boolean canScare) {
            super(Passerine.this, speedModifier, items, canScare);
        }

        public boolean canUse() {
            return !Passerine.this.isAsleep() && super.canUse();
        }
    }

    class FindCoverGoal extends FleeSunGoal {
        public FindCoverGoal(double speedModifier) {
            super(Passerine.this, speedModifier);
        }

        public boolean canUse() {
            return Passerine.this.isUnsafeAt(Passerine.this.blockPosition()) && !this.isTargetPosDry() && this.setWantedPos();
        }

        private boolean isTargetPosDry() {
            return Passerine.this.getNavigation().isInProgress() && !Passerine.this.isUnsafeAt(getNavigation().getTargetPos());
        }
    }

    class SleepGoal extends Goal {
        private static final int WAIT_TIME_BEFORE_SLEEP = reducedTickDelay(140);
        private int countdown = Passerine.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);

        public SleepGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        public boolean canUse() {
            return Passerine.this.xxa == 0.0F && Passerine.this.yya == 0.0F && Passerine.this.zza == 0.0F && (this.canSleep() || Passerine.this.isAsleep());
        }

        public boolean canContinueToUse() {
            return Passerine.this.isAsleep() && this.canSleep();
        }

        private boolean canSleep() {
            if (this.countdown > 0) {
                this.countdown--;
                return false;
            } else {
                if (Passerine.this.isFlying() || Passerine.this.isPreening() || Passerine.this.isPecking() || Passerine.this.level.isDay() || Passerine.this.isInPowderSnow)
                    return false;
                else
                    return Passerine.this.canPerch();
            }
        }

        public void start() {
            Passerine.this.sleep();
            Passerine.this.getNavigation().stop();
        }

        public void stop() {
            Passerine.this.wakeUp();
            this.countdown = Passerine.this.random.nextInt(WAIT_TIME_BEFORE_SLEEP);
        }
    }

    class PreenGoal extends Goal {
        private static final int MINIMUM_WAIT_TIME = reducedTickDelay(3000);
        private int countdown = MINIMUM_WAIT_TIME + Passerine.this.random.nextInt(MINIMUM_WAIT_TIME);

        public PreenGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        public boolean canUse() {
            if (this.countdown > 0) {
                this.countdown--;
                return false;
            } else
                return Passerine.this.xxa == 0.0F && Passerine.this.yya == 0.0F && Passerine.this.zza == 0.0F && this.canPreen();
        }

        public boolean canContinueToUse() {
            return Passerine.this.getPreenCounter() > 0 && this.canPreen();
        }

        private boolean canPreen() {
            return !Passerine.this.isTurkey() && !Passerine.this.isFlying() && !Passerine.this.isAsleep() && !Passerine.this.isPecking() && !Passerine.this.isInPowderSnow;
        }

        public void start() {
            Passerine.this.setPreenCounter(this.adjustedTickDelay(40));
            Passerine.this.getNavigation().stop();
        }

        public void stop() {
            Passerine.this.setPreenCounter(0);
            this.countdown = MINIMUM_WAIT_TIME + Passerine.this.random.nextInt(MINIMUM_WAIT_TIME);
        }

        public void tick() {
            Passerine.this.setPreenCounter(Math.max(0, Passerine.this.getPreenCounter() - 1));
            if (Passerine.this.getPreenCounter() == this.adjustedTickDelay(20))
                Passerine.this.level.broadcastEntityEvent(Passerine.this, (byte) 11);
        }
    }

    class PeckGoal extends Goal {
        private static final int MINIMUM_WAIT_TIME = reducedTickDelay(3000);
        private int countdown = MINIMUM_WAIT_TIME + Passerine.this.random.nextInt(MINIMUM_WAIT_TIME);

        public PeckGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        public boolean canUse() {
            if (this.countdown > 0) {
                this.countdown--;
                return false;
            } else
                return Passerine.this.xxa == 0.0F && Passerine.this.yya == 0.0F && Passerine.this.zza == 0.0F && this.canPeck();
        }

        public boolean canContinueToUse() {
            return Passerine.this.getPeckCounter() > 0 && this.canPeck();
        }

        private boolean canPeck() {
            return !Passerine.this.isTurkey() && !Passerine.this.isFlying() && !Passerine.this.isAsleep() && !Passerine.this.isPreening() && !Passerine.this.isInPowderSnow;
        }

        public void start() {
            Passerine.this.setPeckCounter(this.adjustedTickDelay(40));
            Passerine.this.getNavigation().stop();
        }

        public void stop() {
            Passerine.this.setPeckCounter(0);
            this.countdown = MINIMUM_WAIT_TIME + Passerine.this.random.nextInt(MINIMUM_WAIT_TIME);
        }

        public void tick() {
            Passerine.this.setPeckCounter(Math.max(0, Passerine.this.getPeckCounter() - 1));
        }
    }

    class PasserineRandomFlyingGoal extends WaterAvoidingRandomStrollGoal {
        public PasserineRandomFlyingGoal(double speedModifier) {
            super(Passerine.this, speedModifier);
        }

        @Nullable
        protected Vec3 getPosition() {
            if (Passerine.this.isInWater() || Passerine.this.isUnsafeAt(Passerine.this.blockPosition()))
                return LandRandomPos.getPos(Passerine.this, 15, 15);

            float probability = Passerine.this.level.isDay() ? this.probability : 0.0F;
            Vec3 vec3 = Passerine.this.getRandom().nextFloat() >= probability ? this.getPerchablePos() : null;
            vec3 = vec3 == null ? super.getPosition() : vec3;
            return vec3 != null && !Passerine.this.isUnsafeAt(new BlockPos(vec3)) ? vec3 : null;
        }

        @Nullable
        private Vec3 getPerchablePos() {
            BlockPos blockpos = Passerine.this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

            for (BlockPos blockpos1 : BlockPos.betweenClosed(Mth.floor(Passerine.this.getX() - 3.0D), Mth.floor(Passerine.this.getY() - 6.0D), Mth.floor(Passerine.this.getZ() - 3.0D), Mth.floor(Passerine.this.getX() + 3.0D), Mth.floor(Passerine.this.getY() + 6.0D), Mth.floor(Passerine.this.getZ() + 3.0D))) {
                if (!blockpos.equals(blockpos1)) {
                    BlockState state = Passerine.this.level.getBlockState(blockpos$mutableblockpos1.setWithOffset(blockpos1, Direction.DOWN));
                    boolean flag = state.is(HabitatBlockTags.PASSERINES_PERCHABLE_ON);

                    if (flag && Passerine.this.level.isEmptyBlock(blockpos1) && Passerine.this.level.isEmptyBlock(blockpos$mutableblockpos.setWithOffset(blockpos1, Direction.UP)))
                        return Vec3.atBottomCenterOf(blockpos1);
                }
            }

            return null;
        }
    }

    class PasserineFollowMobGoal extends FollowMobGoal {
        public PasserineFollowMobGoal(double speedModifier, float stopDistance, float areaSize) {
            super(Passerine.this, speedModifier, stopDistance, areaSize);
        }

        public boolean canUse() {
            return Passerine.this.isActive() && super.canUse();
        }
    }
}