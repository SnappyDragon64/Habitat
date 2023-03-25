package mod.schnappdragon.habitat.common.entity.animal;

import mod.schnappdragon.habitat.common.entity.IHabitatShearable;
import mod.schnappdragon.habitat.core.HabitatConfig;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pooka extends Rabbit implements Enemy, IHabitatShearable {
    private int aidId;
    private int aidDuration;
    private int ailmentId;
    private int ailmentDuration;
    private int forgiveTicks;
    private int aidTicks;

    public Pooka(EntityType<? extends Pooka> entityType, Level world) {
        super(entityType, world);
        this.xpReward = 3;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new Pooka.PookaPanicGoal(2.2D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new Pooka.PookaTemptGoal(1.25D, Ingredient.of(HabitatItemTags.POOKA_FOOD), false));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new AvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D));
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
    public int getExperienceReward() {
        return this.xpReward;
    }

    /*
     * Data Methods
     */

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AidId", this.aidId);
        compound.putInt("AidDuration", this.aidDuration);
        compound.putInt("AilmentId", this.ailmentId);
        compound.putInt("AilmentDuration", this.ailmentDuration);
        compound.putInt("ForgiveTicks", this.forgiveTicks);
        compound.putInt("AidTicks", this.aidTicks);
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
        this.aidTicks = compound.getInt("AidTicks");
    }

    private void setAidAndAilment(int aidI, int aidD, int ailI, int ailD) {
        this.aidId = aidI;
        this.aidDuration = aidD;
        this.ailmentId = ailI;
        this.ailmentDuration = ailD;
    }

    private void setForgiveTimer() {
        this.forgiveTicks = 12000;
    }

    private void setAidTimer() {
        this.aidTicks = (int) ((20.0F + this.random.nextFloat() * 4.0F) * (float) HabitatConfig.COMMON.pookaAidCooldown.get());
    }

    private void resetAidTimer() {
        this.aidTicks = 0;
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

        if (this.aidTicks > 0)
            aidTicks--;

        super.customServerAiStep();
    }

    private void facePoint(double x, double z) {
        this.setYRot((float) (Mth.atan2(z - this.getZ(), x - this.getX()) * (double) (180F / (float) Math.PI)) - 90.0F);
    }

    /*
     * Conversion Methods
     */

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
        this.level.gameEvent(player, GameEvent.SHEAR, pos);
        world.playSound(null, this, HabitatSoundEvents.POOKA_SHEAR.get(), source, 1.0F, 0.8F + this.random.nextFloat() * 0.4F);

        if (!this.level.isClientSide()) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5D), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            this.discard();

            Rabbit rabbit = EntityType.RABBIT.create(this.level);
            rabbit.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            rabbit.setHealth(this.getHealth());
            rabbit.yBodyRot = this.yBodyRot;
            if (this.hasCustomName()) {
                rabbit.setCustomName(this.getCustomName());
                rabbit.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isPersistenceRequired())
                rabbit.setPersistenceRequired();

            rabbit.setRabbitType(this.getRabbitType());
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

    /*
     * Taming Methods
     */

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.isFood(stack) && this.getHealth() < this.getMaxHealth() && stack.isEdible()) {
            if (!this.level.isClientSide) {
                this.usePlayerItem(player, hand, stack);
                this.heal(stack.getItem().getFoodProperties().getNutrition());
                this.gameEvent(GameEvent.ENTITY_INTERACT, this);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        InteractionResult result = super.mobInteract(player, hand);
        if (result.consumesAction())
            this.setPersistenceRequired();

        return result;
    }

    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        if (this.isFood(stack))
            this.playSound(HabitatSoundEvents.POOKA_EAT.get(), 1.0F, 1.0F);

        super.usePlayerItem(player, hand, stack);
    }

    /*
     * Breeding Methods
     */

    @Override
    public Pooka getBreedOffspring(ServerLevel serverWorld, AgeableMob entity) {
        Pooka pooka = HabitatEntityTypes.POOKA.get().create(serverWorld);
        int i = this.getRandomRabbitType(serverWorld);

        Pair<Integer, Integer> aid = this.getRandomAid();
        int aidI = aid.getLeft();
        int aidD = aid.getRight();

        Pair<Integer, Integer> ailment = this.getRandomAilment();
        int ailI = ailment.getLeft();
        int ailD = ailment.getRight();

        if (entity instanceof Pooka parent) {
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

        pooka.setRabbitType(i);
        pooka.setAidAndAilment(aidI, aidD, ailI, ailD);
        pooka.setPersistenceRequired();
        return pooka;
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(HabitatItemTags.POOKA_FOOD);
    }

    public boolean canMate(Animal animal) {
        return animal instanceof Pooka && super.canMate(animal);
    }

    /*
     * Spawn Methods
     */

    public static boolean checkPookaSpawnRules(EntityType<Pooka> pooka, LevelAccessor world, MobSpawnType reason, BlockPos pos, RandomSource rand) {
        return world.getBlockState(pos.below()).is(HabitatBlockTags.POOKA_SPAWNABLE_ON);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        Pair<Integer, Integer> aid = this.getRandomAid();
        Pair<Integer, Integer> ailment = this.getRandomAilment();
        int i = this.getRandomRabbitType(worldIn);

        if (spawnDataIn instanceof Rabbit.RabbitGroupData data)
            i = data.rabbitType;
        else
            spawnDataIn = new Rabbit.RabbitGroupData(i);

        this.setRabbitType(i);
        this.setAidAndAilment(aid.getLeft(), aid.getRight(), ailment.getLeft(), ailment.getRight());
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    private int getRandomRabbitType(LevelAccessor p_29676_) {
        Holder<Biome> holder = p_29676_.getBiome(this.blockPosition());
        int i = p_29676_.getRandom().nextInt(100);
        if (holder.value().getPrecipitation() == Biome.Precipitation.SNOW) {
            return i < 80 ? 1 : 3;
        } else if (holder.is(BiomeTags.ONLY_ALLOWS_SNOW_AND_GOLD_RABBITS)) {
            return 4;
        } else {
            return i < 50 ? 0 : (i < 90 ? 5 : 2);
        }
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

    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;
        else {
            if (!this.level.isClientSide && this.isAlive()) {
                MobEffect effect = MobEffect.byId(aidId);
                if (!this.isBaby() && effect != null)
                    this.addEffect(new MobEffectInstance(effect, aidDuration));
            }

            return super.hurt(source, amount);
        }
    }

    /*
     * Particle Status Updates
     */

    public void handleEntityEvent(byte id) {
        if (id == 14) {
            this.level.addParticle(HabitatParticleTypes.FAIRY_RING_SPORE.get(), this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), this.random.nextGaussian() * (0.01D), 0.0D, this.random.nextGaussian() * (0.01D));
        } else {
            super.handleEntityEvent(id);
        }
    }

    /*
     * AI Goals
     */

    class PookaPanicGoal extends PanicGoal {
        public PookaPanicGoal(double speedIn) {
            super(Pooka.this, speedIn);
        }

        @Override
        public void tick() {
            super.tick();
            Pooka.this.setSpeedModifier(this.speedModifier);
        }
    }

    class PookaTemptGoal extends TemptGoal {
        public PookaTemptGoal(double speed, Ingredient temptItem, boolean scaredByMovement) {
            super(Pooka.this, speed, temptItem, scaredByMovement);
        }

        @Override
        public void tick() {
            super.tick();
            MobEffect aid = MobEffect.byId(Pooka.this.aidId);

            if (!Pooka.this.isBaby() && Pooka.this.aidTicks == 0 && aid != null) {
                this.player.addEffect(new MobEffectInstance(aid, Pooka.this.aidDuration * 2));
                Pooka.this.setAidTimer();
            }
        }
    }
}