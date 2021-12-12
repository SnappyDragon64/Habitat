package mod.schnappdragon.habitat.common.entity.animal;

import com.mojang.math.Vector3f;
import mod.schnappdragon.habitat.core.misc.HabitatCriterionTriggers;
import mod.schnappdragon.habitat.core.misc.HabitatDamageSources;
import mod.schnappdragon.habitat.core.particles.ColorableParticleOption;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.GameRules;
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
import java.util.Random;

public class Passerine extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SLEEPING = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.BOOLEAN);

    public float flap;
    public float flapSpeed;
    public float initialFlapSpeed;
    public float initialFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    private Passerine.PreenGoal preenGoal;
    private int preenAnim;

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
        this.preenGoal = new Passerine.PreenGoal();
        this.goalSelector.addGoal(5, this.preenGoal);
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
        this.entityData.define(DATA_VARIANT_ID, 0);
        this.entityData.define(DATA_SLEEPING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
        compound.putBoolean("Sleeping", this.isAsleep());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setSleeping(compound.getBoolean("Sleeping"));
    }

    public void setVariant(int id) {
        this.entityData.set(DATA_VARIANT_ID, id);
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(DATA_VARIANT_ID), 0, 9);
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

    /*
     * AI Methods
     */

    protected void customServerAiStep() {
        this.preenAnim = this.preenGoal.getRemainingPreeningTicks();
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

        if (this.level.isClientSide && this.preenAnim > 0)
            this.preenAnim--;
    }

    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            if (this.isAsleep() && (this.isFlying() || this.level.isDay() || this.isInPowderSnow || this.isUnsafeAt(this.blockPosition()) || !this.canPerch()))
                this.wakeUp();
            else if (this.isPreening() && this.isInPowderSnow)
                this.preenGoal.stopPreening();
        }
    }

    public boolean isPreening() {
        return this.preenAnim > 0;
    }

    public int getRemainingPreeningTicks() {
        return this.preenAnim;
    }

    private boolean isNotBusy() {
        return !this.isAsleep() && !this.isPreening();
    }

    private boolean isUnsafeAt(BlockPos pos) {
        if (this.isGoldfish() || !this.level.isRaining() || !this.level.canSeeSky(pos))
            return false;
        else if (this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY())
            return false;
        else
            return this.level.getBiome(pos).getPrecipitation() != Biome.Precipitation.NONE;
    }

    private boolean isActive() {
        return this.level.isDay() && !this.level.isRaining();
    }

    private boolean canPerch() {
        return this.level.getBlockState(this.getOnPos()).is(HabitatBlockTags.PASSERINE_PERCHABLE_ON);
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

        if (!this.isEasterEgg() && random.nextInt(30) == 0)
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
            if (!this.level.isClientSide) {
                this.heal(1.0F);
                this.usePlayerItem(player, hand, stack);
                this.level.broadcastEntityEvent(this, (byte) 13);
                this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                HabitatCriterionTriggers.FEED_PASSERINE.trigger((ServerPlayer) player);
                this.playSound(HabitatSoundEvents.PASSERINE_AMBIENT.get(), 1.0F, this.getVoicePitch());
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    /*
     * Spawn Methods
     */

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        int i = this.getVariantByBiome(worldIn);
        if (spawnDataIn instanceof Passerine.PasserineGroupData data)
            i = data.variant;
        else
            spawnDataIn = new Passerine.PasserineGroupData(i);

        this.setVariant(i);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public int getVariantByBiome(LevelAccessor worldIn) {
        Biome biome = worldIn.getBiome(this.blockPosition());
        Optional<ResourceKey<Biome>> optional = worldIn.getBiomeName(this.blockPosition());

        if (Objects.equals(optional, Optional.of(Biomes.FLOWER_FOREST))) // All variants
            return this.random.nextInt(10);
        else if (biome.getBiomeCategory() == Biome.BiomeCategory.JUNGLE) // Jungle variants
            return this.random.nextBoolean() ? 1 : 8;
        else if (biome.getBaseTemperature() >= 1.0F) // Hot biomes
            return this.random.nextBoolean() ? 3 : 6;
        else if (biome.getBaseTemperature() < 0.5F) // Cold biomes
            return new int[]{2, 3, 5, 7}[this.random.nextInt(4)];
        else if (biome.getBaseTemperature() <= 0.6F) // Birch Forests, etc.
            return new int[]{0, 2, 3, 4, 5, 7}[this.random.nextInt(6)];
        else
            return new int[]{0, 3, 5, 6, 7, 9}[this.random.nextInt(6)];
    }

    public static boolean checkPasserineSpawnRules(EntityType<Passerine> type, LevelAccessor worldIn, MobSpawnType spawnType, BlockPos pos, Random random) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.PASSERINE_SPAWNABLE_ON) && isBrightEnoughToSpawn(worldIn, pos);
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
                if (source.getDirectEntity() != null && !this.isEasterEgg())
                    this.level.broadcastEntityEvent(this, (byte) 12);

                if (this.isAsleep())
                    this.wakeUp();
                else if (this.isPreening())
                    this.preenGoal.stopPreening();
            }

            return super.hurt(source, amount);
        }
    }

    /*
     * Sound Methods
     */

    public void playAmbientSound() {
        if (!this.isPreening() && this.isActive()) {
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
            case 14 -> this.preenAnim = 40;
            case 15 -> this.preenAnim = 0;
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnFeathers(ColorableParticleOption feather, int number) {
        for (int i = 0; i < number; i++)
            this.level.addParticle(feather, this.getRandomX(0.5D), this.getY(this.random.nextDouble() * 0.75D), this.getRandomZ(0.5D), this.random.nextGaussian() * 0.01D, 0.0D, this.random.nextGaussian() * 0.01D);
    }

    private ColorableParticleOption getFeather() {
        int color = Passerine.Variant.getFeatherColorByVariant(this.getVariant());
        return new ColorableParticleOption(HabitatParticleTypes.FEATHER.get(), new Vector3f(Vec3.fromRGB24(color)));
    }

    private ColorableParticleOption getNote() {
        int color = Passerine.Variant.getNoteColorByVariant(this.getVariant());
        return new ColorableParticleOption(HabitatParticleTypes.NOTE.get(), new Vector3f(Vec3.fromRGB24(color)));
    }

    /*
     * Easter Egg Methods
     */

    public boolean isEasterEgg() {
        return this.isBerdly() || this.isGoldfish() || this.isTurkey();
    }

    public boolean isBerdly() {
        return "Berdly".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isGoldfish() {
        return this.getVariant() == 0 && "Goldfish".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isTurkey() {
        return "Turkey".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        if (!this.level.isClientSide && this.dead && this.isBerdly() && source == DamageSource.FREEZE && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES)) {
            this.getCombatTracker().recordDamage(HabitatDamageSources.SNOWGRAVE, this.getHealth(), this.getMaxHealth());
            ((ServerLevel) this.level).getServer().getPlayerList().broadcastMessage(this.getCombatTracker().getDeathMessage(), ChatType.SYSTEM, Util.NIL_UUID);
        }
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
        public final int variant;

        public PasserineGroupData(int variantId) {
            super(false);
            this.variant = variantId;
        }
    }

    /*
     * Variant
     */

    public enum Variant {
        AMERICAN_GOLDFINCH(16052497, 16775680),
        BALI_MYNA(16777215, 8703),
        BLUE_JAY(4815308, 24063),
        COMMON_SPARROW(7488818, 16730112),
        EASTERN_BLUEBIRD(5012138, 16744192),
        EURASIAN_BULLFINCH(796479, 16711726),
        FLAME_ROBIN(6248013, 16739840),
        NORTHERN_CARDINAL(13183262, 16714752),
        RED_THROATED_PARROTFINCH(4487992, 16713728),
        VIOLET_BACKED_STARLING(6435209, 9175295);

        private static final Variant[] VARIANTS = Variant.values();
        private final int featherColor;
        private final int noteColor;

        Variant(int featherColor, int noteColor) {
            this.featherColor = featherColor;
            this.noteColor = noteColor;
        }

        public static int getFeatherColorByVariant(int id) {
            return getVariantById(id).featherColor;
        }

        public static int getNoteColorByVariant(int id) {
            return getVariantById(id).noteColor;
        }

        private static Variant getVariantById(int id) {
            return VARIANTS[Mth.clamp(id, 0, 9)];
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
        private int countdown = Passerine.this.random.nextInt(140);

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
                if (Passerine.this.isFlying() || Passerine.this.isPreening() || Passerine.this.level.isDay() || Passerine.this.isInPowderSnow)
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
            this.countdown = Passerine.this.random.nextInt(140);
        }
    }

    class PreenGoal extends Goal {
        private int countdown = 2800 + Passerine.this.random.nextInt(2800);
        private int preenAnim;

        public PreenGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        public boolean canUse() {
            if (this.countdown > 0) {
                this.countdown--;
                return false;
            } else
                return Passerine.this.xxa == 0.0F && Passerine.this.yya == 0.0F && Passerine.this.zza == 0.0F && (this.canPreen() || Passerine.this.isPreening());
        }

        public boolean canContinueToUse() {
            return this.preenAnim > 0 && this.canPreen();
        }

        private boolean canPreen() {
            return !Passerine.this.isTurkey() && !Passerine.this.isFlying() && !Passerine.this.isAsleep() && !Passerine.this.isInPowderSnow;
        }

        public void start() {
            this.preenAnim = 40;
            Passerine.this.getNavigation().stop();
            Passerine.this.level.broadcastEntityEvent(Passerine.this, (byte) 14);
        }

        public void stop() {
            this.preenAnim = 0;
            this.countdown = 2800 + Passerine.this.random.nextInt(2800);
            Passerine.this.level.broadcastEntityEvent(Passerine.this, (byte) 15);
        }

        public void tick() {
            if (this.preenAnim > 0) {
                this.preenAnim--;

                if (this.preenAnim == 20)
                    Passerine.this.level.broadcastEntityEvent(Passerine.this, (byte) 11);
            }
        }

        public int getRemainingPreeningTicks() {
            return this.preenAnim;
        }

        public void stopPreening() {
            this.preenAnim = 0;
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
                    boolean flag = state.is(HabitatBlockTags.PASSERINE_PERCHABLE_ON);

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