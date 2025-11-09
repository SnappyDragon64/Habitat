package mod.schnappdragon.habitat.common.entity.animal;

import mod.schnappdragon.habitat.common.item.PasserinePotItem;
import mod.schnappdragon.habitat.core.particles.ColorableParticleOption;
import mod.schnappdragon.habitat.core.registry.*;
import mod.schnappdragon.habitat.core.tags.HabitatBiomeTags;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import mod.schnappdragon.habitat.core.tags.PasserineVariantTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Passerine extends Animal implements FlyingAnimal, VariantHolder<PasserineVariant> {
    private static final ResourceLocation DEFAULT_VARIANT_ID = PasserineVariants.Ids.COMMON_SPARROW;

    private static final EntityDataAccessor<String> DATA_VARIANT_ID = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Integer> PREEN_COUNTER = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PECK_COUNTER = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SLEEPING = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_TRUSTING = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.BOOLEAN);

    private static final Predicate<Entity> AVOID_PLAYERS = (p_28463_) -> !p_28463_.isDiscrete() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(p_28463_);

    public static final double ALERT_RANGE = 4.0D;

    private int foodTicks;

    public float flap;
    public float flapSpeed;
    public float initialFlapSpeed;
    public float initialFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    private boolean isWet;

    public Passerine(EntityType<? extends Passerine> passerine, Level worldIn) {
        super(passerine, worldIn);
        this.moveControl = new PasserineMoveControl(10, false);
        this.lookControl = new PasserineLookControl();
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(0, new Passerine.PasserinePanicGoal(1.5D));
        this.goalSelector.addGoal(1, new Passerine.PasserineTemptGoal(1.0D, Ingredient.of(HabitatItemTags.PASSERINE_FOOD), false));
        this.goalSelector.addGoal(2, new Passerine.FindCoverGoal(1.5D));
        this.goalSelector.addGoal(3, new Passerine.SleepGoal());
        this.goalSelector.addGoal(4, new Passerine.PasserineAvoidEntityGoal<>(Player.class, 4.0F, 1.5D, 1.5D, entity -> AVOID_PLAYERS.test(entity) && !this.isTrusting()));
        this.goalSelector.addGoal(4, new Passerine.PasserineAvoidEntityGoal<>(Cat.class, 8.0F, 1.5D, 1.5D, entity -> entity instanceof TamableAnimal tamableAnimal && !(tamableAnimal.isTame() && this.isTrusting())));
        this.goalSelector.addGoal(4, new Passerine.PasserineAvoidEntityGoal<>(Ocelot.class, 8.0F, 1.5D, 1.5D));
        this.goalSelector.addGoal(4, new Passerine.PasserineAvoidEntityGoal<>(Fox.class, 8.0F, 1.5D, 1.5D));
        this.goalSelector.addGoal(5, new Passerine.PreenGoal());
        this.goalSelector.addGoal(5, new Passerine.PeckGoal());
        this.goalSelector.addGoal(6, new Passerine.FlockAndWanderGoal(1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new Passerine.PasserineFollowMobGoal(1.0D, 3.0F, 7.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.FLYING_SPEED, 0.8F)
                .add(Attributes.MOVEMENT_SPEED, 0.16F);
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.3F);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.5F;
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    @Override
    public void setLeashedTo(Entity entity, boolean sendAttachNotification) {
        super.setLeashedTo(entity, sendAttachNotification);

        if (entity instanceof Player) {
            this.setPersistenceRequired();
        }
    }

    /*
     * Despawning Methods
     */

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isPersistenceRequired();
    }

    /*
     * Data Methods
     */

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREEN_COUNTER, 0);
        this.entityData.define(PECK_COUNTER, 0);
        this.entityData.define(DATA_VARIANT_ID, DEFAULT_VARIANT_ID.toString());
        this.entityData.define(DATA_SLEEPING, false);
        this.entityData.define(DATA_TRUSTING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Variant", this.getVariantId());
        compound.putBoolean("Sleeping", this.isAsleep());
        compound.putBoolean("Trusting", this.isTrusting());
        compound.putInt("FoodTicks", this.foodTicks);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariantId(compound.getString("Variant"));
        this.setSleeping(compound.getBoolean("Sleeping"));
        this.setTrusting(compound.getBoolean("Trusting"));
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
        this.setVariantId(Objects.requireNonNull(this.level().registryAccess().registryOrThrow(HabitatRegistries.Keys.PASSERINE_VARIANTS).getKey(variant)).toString());
    }

    public String getVariantId() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    @Nullable
    public PasserineVariant getVariant() {
        Registry<PasserineVariant> registry = this.level().registryAccess().registryOrThrow(HabitatRegistries.Keys.PASSERINE_VARIANTS);
        ResourceLocation variant = new ResourceLocation(this.getVariantId());
        return registry.get(variant);
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

    public boolean isTrusting() {
        return this.entityData.get(DATA_TRUSTING);
    }

    public void setTrusting(boolean isTrusting) {
        this.entityData.set(DATA_TRUSTING, isTrusting);
    }

    public void setFoodTimer() {
        this.foodTicks = 6000;
    }

    public void saveToPotTag(ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag();

        compound.putFloat("Health", this.getHealth());

        if (this.hasCustomName()) stack.setHoverName(this.getCustomName());
        if (this.isNoAi()) compound.putBoolean("NoAI", this.isNoAi());
        if (this.isSilent()) compound.putBoolean("Silent", this.isSilent());
        if (this.isNoGravity()) compound.putBoolean("NoGravity", this.isNoGravity());
        if (this.hasGlowingTag()) compound.putBoolean("Glowing", this.hasGlowingTag());
        if (this.isInvulnerable()) compound.putBoolean("Invulnerable", this.isInvulnerable());

        compound.putString("Variant", this.getVariantId());
        compound.putInt("FoodTicks", this.foodTicks);
    }

    public void loadFromPotTag(CompoundTag compound) {
        if (compound.contains("Health", Tag.TAG_FLOAT)) this.setHealth(compound.getFloat("Health"));

        if (compound.contains("NoAI")) this.setNoAi(compound.getBoolean("NoAI"));
        if (compound.contains("Silent")) this.setSilent(compound.getBoolean("Silent"));
        if (compound.contains("NoGravity")) this.setNoGravity(compound.getBoolean("NoGravity"));
        if (compound.contains("Glowing")) this.setGlowingTag(compound.getBoolean("Glowing"));
        if (compound.contains("Invulnerable")) this.setInvulnerable(compound.getBoolean("Invulnerable"));

        if (compound.contains("Variant")) this.setVariantId(compound.getString("Variant"));
        if (compound.contains("FoodTicks")) this.foodTicks = compound.getInt("FoodTicks");
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

        if (this.isInWaterRainOrBubble() || this.isInPowderSnow) {
            this.isWet = true;
        }

        if (!this.level().isClientSide) {
            if (this.isAsleep() && (this.isFlying() || this.level().isDay() || this.isInPowderSnow || this.isUnsafeAt(this.blockPosition()) || !this.canPerch()))
                this.wakeUp();
        }
    }

    private boolean isUnsafeAt(BlockPos pos) {
        if (this.isGoldfish() || !this.level().isRaining() || !this.level().canSeeSky(pos))
            return false;
        else if (this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY())
            return false;
        else
            return this.level().getBiome(pos).value().hasPrecipitation();
    }

    private boolean isSheltered() {
        BlockPos currentPos = this.blockPosition();
        return !this.isUnsafeAt(currentPos);
    }

    private boolean isActive() {
        return this.level().isDay() && !this.level().isRaining();
    }

    private boolean isNotBusy() {
        return !this.isAsleep() && !this.isPreening() && !this.isPecking();
    }

    private boolean canPerch() {
        return this.level().getBlockState(this.getOnPos()).is(HabitatBlockTags.PASSERINES_PERCHABLE_ON);
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
        this.flapSpeed = (float) ((double) this.flapSpeed + (double) (!this.onGround() && !this.isPassenger() ? 4 : -1) * 0.3D);
        this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround() && this.flapping < 1.0F)
            this.flapping = 1.0F;

        this.flapping = (float) ((double) this.flapping * 0.9D);
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0D)
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
            this.level().broadcastEntityEvent(this, (byte) 11);
    }

    public boolean isFlying() {
        return !this.onGround();
    }

    /*
     * Interaction Methods
     */

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.FLOWER_POT)) {
            if (!level().isClientSide) {
                ItemStack pot = new ItemStack(HabitatItems.PASSERINE_IN_A_POT.get());
                this.saveToPotTag(pot);

                if (!player.getAbilities().instabuild && stack.getCount() == 1) {
                    player.setItemInHand(hand, pot);
                } else {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    player.addItem(pot);
                }

                this.level().broadcastEntityEvent(this, (byte) 12);
                this.level().playSound(null, getX(), getY(), getZ(), HabitatSoundEvents.PASSERINE_PICKUP.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);

                discard();
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        if (stack.is(HabitatItemTags.PASSERINE_FOOD) && isNotBusy()) {
            if (!level().isClientSide && foodTicks == 0) {
                setFoodTimer();

                heal(1.0F);

                usePlayerItem(player, hand, stack);

                setPersistenceRequired();
                this.setTrusting(true);

                level().broadcastEntityEvent(this, (byte) 13);
                gameEvent(GameEvent.ENTITY_INTERACT, this);
                playSound(HabitatSoundEvents.PASSERINE_AMBIENT.get(), 1.0F, getVoicePitch());

                return InteractionResult.SUCCESS;
            }

            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }


    /*
     * Spawn Methods
     */

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag compound) {
        PasserineVariant i = this.getVariantByBiome(worldIn);

        if (spawnDataIn instanceof Passerine.PasserineGroupData data)
            i = data.variant;
        else
            spawnDataIn = new Passerine.PasserineGroupData(i);

        this.setVariant(i);

        if (reason == MobSpawnType.BUCKET) {
            this.setPersistenceRequired();
            this.setTrusting(true);

            if (compound != null) {
                this.loadFromPotTag(compound);
            }
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, compound);
    }

    public PasserineVariant getVariantByBiome(LevelAccessor world) {
        Holder<Biome> biome = world.getBiome(this.blockPosition());
        TagKey<PasserineVariant> tag;

        if (biome.is(HabitatBiomeTags.SPAWNS_JUNGLE_PASSERINES)) {
            tag = PasserineVariantTags.JUNGLE;
        } else if (biome.is(HabitatBiomeTags.SPAWNS_WARM_PASSERINES)) {
            tag = PasserineVariantTags.WARM;
        } else if (biome.is(HabitatBiomeTags.SPAWNS_BOREAL_PASSERINES)) {
            tag = PasserineVariantTags.BOREAL;
        } else if (biome.is(HabitatBiomeTags.SPAWNS_TEMPERATE_PASSERINES)) {
            tag = PasserineVariantTags.TEMPERATE;
        } else {
            tag = PasserineVariantTags.FOREST;
        }

        return world.registryAccess()
                .registryOrThrow(HabitatRegistries.Keys.PASSERINE_VARIANTS)
                .getTag(tag)
                .flatMap(h -> h.getRandomElement(this.random))
                .map(Holder::get)
                .orElse(null);
    }

    public static boolean checkPasserineSpawnRules(EntityType<Passerine> type, LevelAccessor worldIn, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return worldIn.getBlockState(pos.below()).is(HabitatBlockTags.PASSERINES_SPAWNABLE_ON) && isBrightEnoughToSpawn(worldIn, pos);
    }

    /*
     * Damage Methods
     */

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;
        else {
            if (!this.level().isClientSide) {
                if (source.getEntity() instanceof LivingEntity hurtByEntity) {
                    this.level().getEntitiesOfClass(Passerine.class, this.getBoundingBox().inflate(ALERT_RANGE)).forEach(
                            p -> p.setLastHurtByMob(hurtByEntity)
                    );
                }

                if (this.isRegularVariant() && source.getDirectEntity() != null && !source.is(DamageTypeTags.NO_IMPACT))
                    this.level().broadcastEntityEvent(this, (byte) 12);

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

    @Override
    public void die(DamageSource source) {
        this.wakeUp();
        this.setPreenCounter(0);
        this.setPeckCounter(0);
        super.die(source);
    }

    /*
     * Sound Methods
     */

    public void playAmbientSound() {
        if (!this.isPreening() && !this.isPecking() && this.isActive()) {
            super.playAmbientSound();

            if (!this.level().isClientSide)
                this.level().broadcastEntityEvent(this, (byte) 13);
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
            case 13 -> this.level().addParticle(this.getNote(), this.getRandomX(0.5D), 0.6D + this.getY(), this.getRandomZ(0.5D), this.random.nextDouble(), 0.0D, 0.0D);
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnFeathers(ColorableParticleOption feather, int number) {
        for (int i = 0; i < number; i++)
            this.level().addParticle(feather, this.getRandomX(0.5D), this.getY(this.random.nextDouble() * 0.75D), this.getRandomZ(0.5D), this.random.nextGaussian() * 0.01D, 0.0D, this.random.nextGaussian() * 0.01D);
    }

    private ColorableParticleOption getFeather() {
        return new ColorableParticleOption(HabitatParticleTypes.FEATHER.get(), Vec3.fromRGB24(this.getFeatherColor()).toVector3f());
    }

    private int getFeatherColor() {
        return this.getVariant() == null ? 7488818 : this.getVariant().featherColor();
    }

    private ColorableParticleOption getNote() {
        return new ColorableParticleOption(HabitatParticleTypes.NOTE.get(), Vec3.fromRGB24(this.getNoteColor()).toVector3f());
    }

    private int getNoteColor() {
        return this.getVariant() == null ? 16730112 : this.getVariant().featherColor();
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
        return this.getVariantId().equals(PasserineVariants.Ids.AMERICAN_GOLDFINCH.toString()) && "Goldfish".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isTurkey() {
        return "Turkey".equals(ChatFormatting.stripFormatting(this.getName().getString()));
    }

    public boolean isFlapjack() {
        return this.getVariantId().equals(PasserineVariants.Ids.NORTHERN_CARDINAL.toString()) && "Flapjack".equals(ChatFormatting.stripFormatting(this.getName().getString()));
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

    class PasserinePanicGoal extends PanicGoal {
        public PasserinePanicGoal(double speedModifier) {
            super(Passerine.this, speedModifier);
        }

        @Override
        protected boolean findRandomPosition() {
            Vec3 direction = Passerine.this.getViewVector(0.0F);

            Vec3 fleeToPos = HoverRandomPos.getPos(Passerine.this, 16, 7, direction.x, direction.z, (float) Math.PI * 2.0F, 3, 1);

            if (fleeToPos == null) {
                fleeToPos = AirAndWaterRandomPos.getPos(Passerine.this, 16, 7, 0, direction.x, direction.z, Math.PI * 2.0D);
            }

            if (fleeToPos != null) {
                this.posX = fleeToPos.x;
                this.posY = fleeToPos.y;
                this.posZ = fleeToPos.z;
                return true;
            }

            return false;
        }
    }

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
                if (Passerine.this.isFlying() || Passerine.this.isPreening() || Passerine.this.isPecking() || Passerine.this.level().isDay() || Passerine.this.isInPowderSnow)
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

    class PasserineAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        private final TargetingConditions avoidEntityTargeting;

        public PasserineAvoidEntityGoal(Class<T> entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
            this(entityClassToAvoid, e -> true, maxDistance, walkSpeedModifier, sprintSpeedModifier, e -> true);
        }

        public PasserineAvoidEntityGoal(Class<T> entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
            this(entityClassToAvoid, e -> true, maxDistance, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);
        }

        private PasserineAvoidEntityGoal(Class<T> entityClassToAvoid, Predicate<LivingEntity> avoidPredicate, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
            super(Passerine.this, entityClassToAvoid, avoidPredicate, maxDistance, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);
            this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDistance).selector(predicateOnAvoidEntity.and(avoidPredicate));
        }

        public boolean canUse() {
            this.toAvoid = Passerine.this.level().getNearestEntity(Passerine.this.level().getEntitiesOfClass(this.avoidClass, Passerine.this.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist), (p_148078_) -> true), this.avoidEntityTargeting, Passerine.this, Passerine.this.getX(), Passerine.this.getY(), Passerine.this.getZ());

            if (this.toAvoid == null) {
                return false;
            }

            Vec3 vec3 = this.getPosition();

            if (vec3 == null) {
                return false;
            }

            if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(Passerine.this)) {
                return false;
            }

            this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
            return this.path != null;
        }

        protected Vec3 getPosition() {
            Vec3 awayDir = Passerine.this.position().subtract(this.toAvoid.position()).normalize();

            Vec3 fleeToPos = HoverRandomPos.getPos(Passerine.this, 16, 7, awayDir.x, awayDir.z, (float) Math.PI / 2.0F, 3, 1);

            if (fleeToPos == null) {
                fleeToPos = AirAndWaterRandomPos.getPos(Passerine.this, 16, 7, 0, awayDir.x, awayDir.z, Math.PI / 2.0F);
            }

            return fleeToPos;

        }
    }

    abstract class IdleGoal extends Goal {
        private static final int MINIMUM_WAIT_TIME = reducedTickDelay(1200);
        private int countdown = MINIMUM_WAIT_TIME + Passerine.this.random.nextInt(MINIMUM_WAIT_TIME);
        private final Supplier<Integer> getCounter;
        private final Consumer<Integer> setCounter;
        private final Supplier<Boolean> isIdling;

        public IdleGoal(Supplier<Integer> getCounter, Consumer<Integer> setCounter, Supplier<Boolean> isIdling) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
            this.getCounter = getCounter;
            this.setCounter = setCounter;
            this.isIdling = isIdling;
        }

        public boolean canUse() {
            if (this.countdown > 0) {
                this.countdown--;
                return false;
            } else
                return Passerine.this.xxa == 0.0F && Passerine.this.yya == 0.0F && Passerine.this.zza == 0.0F && this.canIdle();
        }

        public boolean canContinueToUse() {
            return this.getCounter.get() > 0 && this.canIdle();
        }

        private boolean canIdle() {
            return !Passerine.this.isTurkey() && !Passerine.this.isFlying() && (Passerine.this.isNotBusy() || this.isIdling.get()) && !Passerine.this.isInPowderSnow;
        }

        public void start() {
            this.setCounter.accept(this.adjustedTickDelay(40));
            Passerine.this.getNavigation().stop();
        }

        public void stop() {
            this.setCounter.accept(0);
            this.countdown = MINIMUM_WAIT_TIME + Passerine.this.random.nextInt(MINIMUM_WAIT_TIME);
        }

        public void tick() {
            this.setCounter.accept(Math.max(0, this.getCounter.get() - 1));
        }

        public void setCountdown(int countdown) {
            this.countdown = countdown;
        }
    }

    class PreenGoal extends IdleGoal {
        public PreenGoal() {
            super(Passerine.this::getPreenCounter, Passerine.this::setPreenCounter, Passerine.this::isPreening);
        }

        public boolean canUse() {
            if (Passerine.this.isWet) {
                this.setCountdown(0);
                Passerine.this.isWet = false;
            }

            return super.canUse();
        }

        public void tick() {
            super.tick();

            if (Passerine.this.getPreenCounter() == this.adjustedTickDelay(20))
                Passerine.this.level().broadcastEntityEvent(Passerine.this, (byte) 11);
        }
    }

    class PeckGoal extends IdleGoal {
        public PeckGoal() {
            super(Passerine.this::getPeckCounter, Passerine.this::setPeckCounter, Passerine.this::isPecking);
        }
    }

    class FlockAndWanderGoal extends WaterAvoidingRandomFlyingGoal {
        private static final int PERCH_SEARCH_RANGE_HORIZONTAL = 3;
        private static final int PERCH_SEARCH_DOWNWARD = 3;
        private static final int PERCH_SEARCH_UPWARD = 6;

        private static final int PERCH_SEARCH_ATTEMPTS_DAY = 16;
        private static final int PERCH_SEARCH_ATTEMPTS_NIGHT = 64;

        public static final double FLOCK_SEARCH_RANGE = 8.0D;

        public static final int LAND_SEARCH_RADIUS = 15;
        public static final int LAND_SEARCH_VERTICAL_RANGE = 15;

        private static final float CHANCE_WANDER_BASE = 0.1F;
        private static final float CHANCE_WANDER_PITY = 0.05F;

        private int wanderPity = 0;

        public FlockAndWanderGoal(double speedModifier) {
            super(Passerine.this, speedModifier);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (Passerine.this.level().isRaining() && Passerine.this.isSheltered()) {
                return false;
            }

            return Passerine.this.isNotBusy() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return Passerine.this.getNavigation().isInProgress() && Passerine.this.isNotBusy();
        }

        @Nullable
        protected Vec3 getPosition() {
            List<Passerine> flock = findFlock();
            Vec3 center = calculateFlockCenter(flock);
            Vec3 perch = getPerchPos(center);

            if (perch != null) {
                wanderPity = 0;
                return perch;
            }

            if (Passerine.this.isInWater()) {
                return LandRandomPos.getPos(Passerine.this, LAND_SEARCH_RADIUS, LAND_SEARCH_VERTICAL_RANGE);
            }

            if (Passerine.this.getRandom().nextFloat() < getWanderOdds()) {
                wanderPity = 0;
                return super.getPosition();
            }

            wanderPity++;
            return null;
        }

        @Nullable
        private Vec3 getPerchPos(Vec3 center) {
            BlockPos origin = new BlockPos((int) center.x(), (int) center.y(), (int) center.z());
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            int trials = Passerine.this.level().isNight() ? PERCH_SEARCH_ATTEMPTS_NIGHT : PERCH_SEARCH_ATTEMPTS_DAY;
            int verticalRange = PERCH_SEARCH_DOWNWARD + PERCH_SEARCH_UPWARD + 1;

            for (int i = 0; i < trials; ++i) {
                int x = origin.getX() + Passerine.this.getRandom().nextInt(PERCH_SEARCH_RANGE_HORIZONTAL * 2 + 1) - PERCH_SEARCH_RANGE_HORIZONTAL;
                int y = origin.getY() + Passerine.this.getRandom().nextInt(verticalRange) - PERCH_SEARCH_DOWNWARD;
                int z = origin.getZ() + Passerine.this.getRandom().nextInt(PERCH_SEARCH_RANGE_HORIZONTAL * 2 + 1) - PERCH_SEARCH_RANGE_HORIZONTAL;
                mutablePos.set(x, y, z);

                if (Passerine.this.isUnsafeAt(mutablePos)) {
                    continue;
                }

                BlockState belowState = Passerine.this.level().getBlockState(mutablePos.below());
                if (belowState.is(HabitatBlockTags.PASSERINES_PERCHABLE_ON) &&
                        Passerine.this.level().isEmptyBlock(mutablePos) &&
                        Passerine.this.level().isEmptyBlock(mutablePos.above())) {
                    return Vec3.atBottomCenterOf(mutablePos);
                }
            }
            return null;
        }

        private List<Passerine> findFlock() {
            return Passerine.this.level().getEntitiesOfClass(Passerine.class, Passerine.this.getBoundingBox().inflate(FLOCK_SEARCH_RANGE),
                    p -> !p.is(Passerine.this) && p.getVariantId().equals(Passerine.this.getVariantId()));
        }

        private Vec3 calculateFlockCenter(List<Passerine> flock) {
            if (flock.isEmpty()) {
                return Passerine.this.position();
            }

            Vec3 centroid = Passerine.this.position();

            for (Passerine passerine : flock) {
                centroid = centroid.add(passerine.position());
            }

            int count = flock.size() + 1;
            return centroid.scale(1.0D / count);
        }

        private float getWanderOdds() {
            return Math.min(1.0F, CHANCE_WANDER_BASE + wanderPity * CHANCE_WANDER_PITY);
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