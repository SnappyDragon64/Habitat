package mod.schnappdragon.habitat.common.entity.monster;

import mod.schnappdragon.habitat.core.HabitatConfig;
import mod.schnappdragon.habitat.core.registry.*;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.tags.HabitatEntityTypeTags;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.IForgeShearable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class Pooka extends Rabbit implements Enemy, IForgeShearable {
    private static final EntityDataAccessor<String> DATA_STATE = SynchedEntityData.defineId(Pooka.class, EntityDataSerializers.STRING);
    private int aidId;
    private int aidDuration;
    private int ailmentId;
    private int ailmentDuration;
    private int forgiveTicks = 0;

    public Pooka(EntityType<? extends Pooka> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 3;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PookaPanicGoal(this, 2.2D));
        this.targetSelector.addGoal(1, (new PookaHurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new PookaNearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new PookaNearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, mob -> mob.getType().is(HabitatEntityTypeTags.POOKA_ATTACK_TARGETS)));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new PookaTemptGoal(this, 1.25D, Ingredient.of(HabitatItemTags.POOKA_FOOD), false));
        this.goalSelector.addGoal(4, new PookaMeleeAttackGoal(this));
        this.goalSelector.addGoal(4, new PookaAvoidEntityGoal<>(this, Mob.class, 10.0F, 2.2D, 2.2D, mob -> mob.getType().is(HabitatEntityTypeTags.PACIFIED_POOKA_SCARED_BY)));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 10.0F));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 8.0D);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(HabitatItems.POOKA_SPAWN_EGG.get());
    }

    @Override
    protected int getExperienceReward(Player player) {
        return this.xpReward;
    }

    /*
     * Data Methods
     */

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STATE, Pooka.State.HOSTILE.id);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AidId", this.aidId);
        compound.putInt("AidDuration", this.aidDuration);
        compound.putInt("AilmentId", this.ailmentId);
        compound.putInt("AilmentDuration", this.ailmentDuration);
        compound.putInt("ForgiveTicks", this.forgiveTicks);
        compound.putString("State", this.getStateId());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAidAndAilment(
                compound.getInt("AidId"),
                compound.getInt("AidDuration"),
                compound.getInt("AilmentId"),
                compound.getInt("AilmentDuration")
        );
        this.forgiveTicks = compound.getInt("ForgiveTicks");
        this.setState(compound.getString("State"));
    }

    private void setAidAndAilment(int aidI, int aidD, int ailI, int ailD) {
        this.aidId = aidI;
        this.aidDuration = aidD;
        this.ailmentId = ailI;
        this.ailmentDuration = ailD;
    }

    private void setState(Pooka.State state) {
        this.setState(state.id);
    }

    private void setState(String state) {
        this.entityData.set(DATA_STATE, state);
    }

    public Pooka.State getState() {
        return Pooka.State.getById(this.getStateId());
    }

    public String getStateId() {
        return this.entityData.get(DATA_STATE);
    }

    public boolean isHostile() {
        return this.getState().equals(Pooka.State.HOSTILE);
    }

    public boolean isPacified() {
        return this.getState().equals(Pooka.State.PACIFIED);
    }

    public boolean isPassive() {
        return this.getState().equals(Pooka.State.PASSIVE);
    }

    private void setForgiveTimer() {
        this.forgiveTicks = 12000;
    }

    /*
     * Update AI Tasks
     */

    @Override
    public void jumpFromGround() {
        if (!this.level.isClientSide)
            this.level.broadcastEntityEvent(this, (byte) 14);

        super.jumpFromGround();
    }

    @Override
    public void customServerAiStep() {
        if (this.forgiveTicks > 0)
            forgiveTicks--;

        if (this.onGround && this.isHostile() && this.jumpDelayTicks == 0) {
            LivingEntity livingentity = this.getTarget();
            if (livingentity != null && this.distanceToSqr(livingentity) < 16.0D) {
                this.facePoint(livingentity.getX(), livingentity.getZ());
                this.moveControl.setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), this.moveControl.getSpeedModifier());
                this.startJumping();
                this.wasOnGround = true;
            }
        }

        super.customServerAiStep();
    }

    private void facePoint(double x, double z) {
        this.setYRot((float) (Mth.atan2(z - this.getZ(), x - this.getX()) * (double) (180F / (float) Math.PI)) - 90.0F);
    }

    /*
     * Leash Methods
     */

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isHostile();
    }

    @Override
    protected void tickLeash() {
        super.tickLeash();

        if (this.isHostile())
            this.dropLeash(true, true);
    }

    /*
     * Conversion Methods
     */

    @Override
    public boolean isShearable(@Nonnull ItemStack item, Level world, BlockPos pos) {
        return this.isAlive() && !this.isHostile() && !this.isBaby();
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
        this.level.gameEvent(player, GameEvent.SHEAR, pos);
        world.playSound(null, this, HabitatSoundEvents.POOKA_SHEAR.get(), SoundSource.HOSTILE, 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
        if (!this.level.isClientSide()) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.discard();
            world.addFreshEntity(convertPooka(this));
        }
        return Collections.singletonList(new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get()));
    }

    public static Rabbit convertPooka(Pooka pooka) {
        Rabbit rabbit = EntityType.RABBIT.create(pooka.level);
        rabbit.moveTo(pooka.getX(), pooka.getY(), pooka.getZ(), pooka.getYRot(), pooka.getXRot());
        rabbit.setHealth(pooka.getHealth());
        rabbit.yBodyRot = pooka.yBodyRot;
        if (pooka.hasCustomName()) {
            rabbit.setCustomName(pooka.getCustomName());
            rabbit.setCustomNameVisible(pooka.isCustomNameVisible());
        }

        if (pooka.isPersistenceRequired()) {
            rabbit.setPersistenceRequired();
        }

        rabbit.setRabbitType(pooka.getRabbitType());
        rabbit.setBaby(pooka.isBaby());
        rabbit.setInvulnerable(pooka.isInvulnerable());
        return rabbit;
    }

    public static Pooka convertRabbit(Rabbit rabbit) {
        Pooka pooka = HabitatEntityTypes.POOKA.get().create(rabbit.level);
        pooka.moveTo(rabbit.getX(), rabbit.getY(), rabbit.getZ(), rabbit.getYRot(), rabbit.getXRot());
        pooka.setHealth(rabbit.getHealth());
        pooka.yBodyRot = rabbit.yBodyRot;
        if (rabbit.hasCustomName()) {
            pooka.setCustomName(rabbit.getCustomName());
            pooka.setCustomNameVisible(rabbit.isCustomNameVisible());
        }

        pooka.setPersistenceRequired();
        pooka.setForgiveTimer();

        Pair<Integer, Integer> aid = pooka.getRandomAid();
        Pair<Integer, Integer> ailment = pooka.getRandomAilment();
        pooka.setAidAndAilment(aid.getLeft(), aid.getRight(), ailment.getLeft(), ailment.getRight());

        pooka.setRabbitType(rabbit.getRabbitType());
        pooka.setBaby(rabbit.isBaby());
        pooka.setInvulnerable(rabbit.isInvulnerable());
        return pooka;
    }

    /*
     * Sound Methods
     */

    public SoundSource getSoundSource() {
        return this.isHostile() ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
    }

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

    /*
     * Taming Methods
     */

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(HabitatItemTags.POOKA_FEEDING_FOOD)) {
            if (!this.level.isClientSide) {
                this.setPersistenceRequired();
                if (!player.getAbilities().instabuild)
                    stack.shrink(1);
                int roll = random.nextInt(5);

                if (!this.isHostile()) {
                    if (this.isPacified() && this.random.nextInt(50) == 1)
                        this.setState(Pooka.State.PASSIVE);

                    FoodProperties food = stack.getItem().getFoodProperties();
                    this.heal(food != null ? food.getNutrition() : 1.0F);
                    this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                    this.level.broadcastEntityEvent(this, (byte) 18);
                } else if (this.forgiveTicks == 0 && (this.isBaby() && roll > 0 || roll == 0) && this.isAlone()) {
                    this.setState(Pooka.State.PACIFIED);
                    this.playSound(HabitatSoundEvents.POOKA_PACIFY.get(), 1.0F, 1.0F);
                    HabitatCriterionTriggers.PACIFY_POOKA.trigger((ServerPlayer) player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setLastHurtByMob(null);
                    this.level.broadcastEntityEvent(this, (byte) 18);
                } else {
                    if (this.forgiveTicks > 0)
                        this.forgiveTicks -= this.forgiveTicks * 0.1D;

                    this.level.broadcastEntityEvent(this, (byte) 12);
                }
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.interactAt(player, vec, hand);
    }

    private boolean isAlone() {
        return this.level.getEntitiesOfClass(Pooka.class, this.getBoundingBox().inflate(16.0D, 10.0D, 16.0D), Pooka::isHostile).size() == 1;
    }

    /*
     * Breeding Methods
     */

    @Override
    public Pooka getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
        Pooka pooka = HabitatEntityTypes.POOKA.get().create(serverWorld);
        Pooka.State state = Pooka.State.HOSTILE;
        int i = this.getRandomRabbitType(serverWorld);

        Pair<Integer, Integer> aid = this.getRandomAid();
        int aidI = aid.getLeft();
        int aidD = aid.getRight();

        Pair<Integer, Integer> ailment = this.getRandomAilment();
        int ailI = ailment.getLeft();
        int ailD = ailment.getRight();

        if (entity instanceof Pooka parent) {
            if (!this.isHostile() && !parent.isHostile())
                state = Pooka.State.PASSIVE;
            else if (this.isHostile() && parent.isPassive() || this.isPassive() && parent.isHostile())
                state = Pooka.State.PACIFIED;

            if (this.random.nextInt(20) != 0) {
                if (this.random.nextBoolean())
                    i = parent.getRabbitType();
                else
                    i = this.getRabbitType();
            }

            if (this.random.nextInt(20) != 0) {
                if (this.random.nextBoolean()) {
                    aidI = parent.aidId;
                    aidD = parent.aidDuration;
                } else {
                    aidI = this.aidId;
                    aidD = this.aidDuration;
                }
            }

            if (this.random.nextInt(20) != 0) {
                if (this.random.nextBoolean()) {
                    ailI = parent.ailmentId;
                    ailD = parent.ailmentDuration;
                } else {
                    ailI = this.ailmentId;
                    ailD = this.ailmentDuration;
                }
            }
        }

        pooka.setState(state);
        pooka.setRabbitType(i);
        pooka.setAidAndAilment(aidI, aidD, ailI, ailD);
        pooka.setPersistenceRequired();
        return pooka;
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(HabitatItemTags.POOKA_BREEDING_FOOD);
    }

    /*
     * Spawn Methods
     */

    public static boolean checkPookaSpawnRules(EntityType<Pooka> pooka, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand) {
        return world.getBlockStates(new AABB(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))).anyMatch(state -> state.is(HabitatBlockTags.ALLOW_POOKA_SPAWNING));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        Pair<Integer, Integer> aid = this.getRandomAid();
        Pair<Integer, Integer> ailment = this.getRandomAilment();
        Pooka.State state = Pooka.State.HOSTILE;
        int i = this.getRandomRabbitType(worldIn);
        int aidI = aid.getLeft();
        int aidD = aid.getRight();
        int ailI = ailment.getLeft();
        int ailD = ailment.getRight();
        if (spawnDataIn instanceof PookaGroupData data) {
            i = data.rabbitType;
            aidI = data.aidIdData;
            aidD = data.aidDurationData;
            ailI = data.ailmentIdData;
            ailD = data.ailmentDurationData;
            state = data.stateData;
        } else
            spawnDataIn = new PookaGroupData(i, aidI, aidD, ailI, ailD, Pooka.State.HOSTILE);

        this.setRabbitType(i);
        this.setAidAndAilment(aidI, aidD, ailI, ailD);
        this.setState(state);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    private int getRandomRabbitType(LevelAccessor world) {
        Biome biome = world.getBiome(this.blockPosition());
        int i = this.random.nextInt(100);
        if (biome.getPrecipitation() == Biome.Precipitation.SNOW)
            return i < 80 ? 1 : 3;
        else if (biome.getBiomeCategory() == Biome.BiomeCategory.DESERT)
            return 4;
        else
            return i < 50 ? 0 : (i < 90 ? 5 : 2);
    }

    private Pair<Integer, Integer> getRandomAid() {
        return getEffect(HabitatConfig.COMMON.pookaPositiveEffects);
    }

    private Pair<Integer, Integer> getRandomAilment() {
        return getEffect(HabitatConfig.COMMON.pookaNegativeEffects);
    }

    private Pair<Integer, Integer> getEffect(ForgeConfigSpec.ConfigValue<String> config) {
        List<String> stewEffectPairs = Arrays.asList(StringUtils.deleteWhitespace(config.get()).split(","));
        String[] pair = stewEffectPairs.get(this.random.nextInt(stewEffectPairs.size())).split(":");

        return Pair.of(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]) * 20);
    }

    /*
     * Damage Methods
     */

    public boolean doHurtTarget(Entity entityIn) {
        if (entityIn.getType() == EntityType.RABBIT && entityIn.isAlive() && !entityIn.isInvulnerableTo(DamageSource.mobAttack(this))) {
            this.playSound(HabitatSoundEvents.POOKA_ATTACK.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.gameEvent(GameEvent.ENTITY_DAMAGED, this);

            Rabbit rabbit = (Rabbit) entityIn;
            rabbit.playSound(HabitatSoundEvents.RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isBaby() ? (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.5F : (rabbit.getRandom().nextFloat() - rabbit.getRandom().nextFloat()) * 0.2F + 1.0F);
            rabbit.discard();
            this.level.addFreshEntity(convertRabbit(rabbit));

            for (int i = 0; i < 8; i++)
                ((ServerLevel) this.level).sendParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), rabbit.getRandomX(0.5D), rabbit.getY(0.5D), rabbit.getRandomZ(0.5D), 0, rabbit.getRandom().nextGaussian(), 0.0D, rabbit.getRandom().nextGaussian(), 0.01D);
            return false;
        }

        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
            this.setLastHurtMob(entityIn);
            this.playSound(HabitatSoundEvents.POOKA_ATTACK.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            if (!this.isBaby() && entityIn instanceof LivingEntity) {
                MobEffect effect = MobEffect.byId(ailmentId);

                if (effect != null)
                    ((LivingEntity) entityIn).addEffect(new MobEffectInstance(effect, ailmentDuration * (this.level.getDifficulty() == Difficulty.HARD ? 2 : 1)));
            }
        }

        return flag;
    }

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;
        else {
            MobEffect effect = MobEffect.byId(aidId);
            if (!this.isBaby() && effect != null)
                this.addEffect(new MobEffectInstance(effect, aidDuration));

            if (this.isPacified() && source.getEntity() instanceof Player && !source.isCreativePlayer()) {
                this.setState(Pooka.State.HOSTILE);
                this.setForgiveTimer();
                this.level.broadcastEntityEvent(this, (byte) 13);
            }

            return super.hurt(source, amount);
        }
    }

    /*
     * Particle Status Updates
     */

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 12 -> spawnParticles(ParticleTypes.SMOKE, 7, true);
            case 13 -> spawnParticles(ParticleTypes.ANGRY_VILLAGER, 7, true);
            case 14 -> spawnParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), 1, false);
            case 15 -> spawnParticles(HabitatParticleTypes.FAIRY_RING_SPORE.get(), 8, false);
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnParticles(ParticleOptions particle, int number, boolean vanillaPresets) {
        for (int i = 0; i < number; i++) {
            double d0 = this.random.nextGaussian() * (vanillaPresets ? 0.02D : 0.01D);
            double d1 = vanillaPresets ? this.random.nextGaussian() * 0.02D : 0.0D;
            double d2 = this.random.nextGaussian() * (vanillaPresets ? 0.02D : 0.01D);
            double d3 = vanillaPresets ? 0.5D : 0.0D;
            this.level.addParticle(particle, this.getRandomX(0.5D + d3), this.getRandomY() + d3, this.getRandomZ(0.5D + d3), d0, d1, d2);
        }
    }

    /*
     * State
     */

    public enum State {
        HOSTILE("hostile"),
        PACIFIED("pacified"),
        PASSIVE("passive");

        private final String id;

        State(String id) {
            this.id = id;
        }

        public static Pooka.State getById(String id) {
            return switch(id) {
                case "pacified" -> Pooka.State.PACIFIED;
                case "passive" -> Pooka.State.PASSIVE;
                default -> Pooka.State.HOSTILE;
            };
        }
    }

    /*
     * Data
     */

    public static class PookaGroupData extends Rabbit.RabbitGroupData {
        int aidIdData;
        int aidDurationData;
        int ailmentIdData;
        int ailmentDurationData;
        Pooka.State stateData;

        public PookaGroupData(int type, int aidId, int aidDuration, int ailmentId, int ailmentDuration, Pooka.State state) {
            super(type);
            aidIdData = aidId;
            aidDurationData = aidDuration;
            ailmentIdData = ailmentId;
            ailmentDurationData = ailmentDuration;
            stateData = state;
        }
    }

    /*
     * AI Goals
     */

    class PookaPanicGoal extends PanicGoal {
        public PookaPanicGoal(Pooka pooka, double speedIn) {
            super(pooka, speedIn);
        }

        @Override
        public void tick() {
            super.tick();
            Pooka.this.setSpeedModifier(this.speedModifier);
        }
    }

    class PookaTemptGoal extends TemptGoal {
        public PookaTemptGoal(Pooka pooka, double speed, Ingredient temptItem, boolean scaredByMovement) {
            super(pooka, speed, temptItem, scaredByMovement);
        }

        @Override
        public boolean canUse() {
            return !Pooka.this.isHostile() && super.canUse();
        }

        @Override
        public void tick() {
            super.tick();
            MobEffect aid = MobEffect.byId(Pooka.this.aidId);

            if (!Pooka.this.isBaby() && Pooka.this.getRandom().nextInt(240) == 0 && aid != null)
                this.player.addEffect(new MobEffectInstance(aid, Pooka.this.aidDuration));
        }
    }

    class PookaHurtByTargetGoal extends HurtByTargetGoal {
        public PookaHurtByTargetGoal(Pooka pooka) {
            super(pooka);
        }

        @Override
        public boolean canUse() {
            return Pooka.this.isHostile() && super.canUse();
        }

        @Override
        protected void alertOthers() {
            double d0 = this.getFollowDistance();
            AABB axisalignedbb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(d0, 10.0D, d0);
            List<Pooka> list = this.mob.level.getEntitiesOfClass(Pooka.class, axisalignedbb);
            Iterator<Pooka> iterator = list.iterator();

            while (true) {
                Pooka pooka;
                do {
                    if (!iterator.hasNext())
                        return;

                    pooka = iterator.next();
                }
                while (this.mob == pooka || pooka.getTarget() != null || pooka.isAlliedTo(this.mob.getLastHurtByMob()));

                if (this.mob.getLastHurtByMob() instanceof Player && pooka.isPacified()) {
                    pooka.setState(Pooka.State.HOSTILE);
                    pooka.setForgiveTimer();
                    pooka.level.broadcastEntityEvent(pooka, (byte) 13);
                }
                this.alertOther(pooka, this.mob.getLastHurtByMob());
            }
        }
    }

    class PookaNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public PookaNearestAttackableTargetGoal(Pooka pooka, Class<T> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(pooka, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
        }

        public PookaNearestAttackableTargetGoal(Pooka pooka, Class<T> targetClassIn, boolean checkSight) {
            super(pooka, targetClassIn, checkSight);
        }

        @Override
        public boolean canUse() {
            return Pooka.this.isHostile() && super.canUse();
        }
    }

    class PookaAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
        public PookaAvoidEntityGoal(Pooka pooka, Class<T> entity, float range, double v1, double v2, Predicate<LivingEntity> predicate) {
            super(pooka, entity, range, v1, v2, predicate);
        }

        @Override
        public boolean canUse() {
            return !Pooka.this.isHostile() && super.canUse();
        }
    }

    class PookaMeleeAttackGoal extends MeleeAttackGoal {
        public PookaMeleeAttackGoal(Pooka pooka) {
            super(pooka, 1.4D, true);
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();
        }

        @Override
        public boolean canUse() {
            return Pooka.this.isHostile() && super.canUse();
        }
    }
}