package mod.schnappdragon.habitat.common.entity.animal;

import com.mojang.math.Vector3f;
import mod.schnappdragon.habitat.core.misc.HabitatDamageSources;
import mod.schnappdragon.habitat.core.particles.FeatherParticleOption;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.tags.HabitatItemTags;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

public class Passerine extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Passerine.class, EntityDataSerializers.INT);
    public float flap;
    public float flapSpeed;
    public float initialFlapSpeed;
    public float initialFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;

    public Passerine(EntityType<? extends Passerine> passerine, Level worldIn) {
        super(passerine, worldIn);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0D, Ingredient.of(HabitatItemTags.PASSERINE_FOOD), false));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(HabitatItems.PASSERINE_SPAWN_EGG.get());
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.3F);
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

    public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
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
        this.nextFlap = this.flyDist + this.flapSpeed / 2.0F;

        if (random.nextInt(30) == 0)
            this.level.broadcastEntityEvent(this, (byte) 11);
    }

    public boolean isFlying() {
        return !this.onGround;
    }

    /*
     * Interaction Method
     */

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(HabitatItemTags.PASSERINE_FOOD)) {
            if (!this.level.isClientSide) {
                if (!player.getAbilities().instabuild)
                    stack.shrink(1);

                this.heal(1.0F);
                this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.interactAt(player, vec, hand);
    }

    /*
     * Spawn Methods
     */

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.setVariant(this.random.nextInt(6));
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableMob.AgeableMobGroupData(false);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public static boolean checkPasserineSpawnRules(EntityType<Passerine> type, LevelAccessor worldIn, MobSpawnType spawnType, BlockPos pos, Random random) {
        BlockState state = worldIn.getBlockState(pos.below());
        return (state.is(BlockTags.LEAVES) || state.is(Blocks.GRASS_BLOCK) || state.is(BlockTags.LOGS) || state.is(Blocks.AIR)) && worldIn.getRawBrightness(pos, 0) > 8;
    }

    /*
     * Hurt Method
     */

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source))
            return false;
        else {
            if (source.getDirectEntity() != null)
                this.level.broadcastEntityEvent(this, (byte) 12);
            return super.hurt(source, amount);
        }
    }

    /*
     * Particle Methods
     */

    public void handleEntityEvent(byte id) {
        switch (id) {
            case 11 -> spawnFeathers(this.getParticle(), 1);
            case 12 -> spawnFeathers(this.getParticle(), 2);
            default -> super.handleEntityEvent(id);
        }
    }

    protected void spawnFeathers(ParticleOptions particle, int number) {
        for (int i = 0; i < number; i++)
            this.level.addParticle(particle, this.getRandomX(0.5D), this.getY(this.random.nextDouble() * 0.8D), this.getRandomZ(0.5D), this.random.nextGaussian() * 0.01D, 0.0D, this.random.nextGaussian() * 0.01D);
    }

    private ParticleOptions getParticle() {
        if (this.isBerdly()) return new FeatherParticleOption(unpackColor(4699131), 0.33F);

        ParticleOptions particle;
        switch (this.getVariant()) {
            case 0 -> particle = new FeatherParticleOption(unpackColor(16052497), 0.33F);
            case 1 -> particle = new FeatherParticleOption(unpackColor(16777215), 0.33F);
            case 2 -> particle = new FeatherParticleOption(unpackColor(7488818), 0.33F);
            case 3 -> particle = new FeatherParticleOption(unpackColor(5012138), 0.33F);
            case 4 -> particle = new FeatherParticleOption(unpackColor(796479), 0.33F);
            case 5 -> particle = new FeatherParticleOption(unpackColor(13183262), 0.33F);
            default -> particle = new FeatherParticleOption(unpackColor(0), 0.33F);
        }
        return particle;
    }

    private static Vector3f unpackColor(int packed) {
        return new Vector3f(Vec3.fromRGB24(packed));
    }

    /*
     * Berdly Methods
     */

    public boolean isBerdly() {
        return "Berdly".equals(ChatFormatting.stripFormatting(this.getName().getString()));
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
    public boolean canMate(Animal pOtherAnimal) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
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
     * Data Methods
     */

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VARIANT_ID, 0);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    public void setVariant(int id) {
        this.entityData.set(DATA_VARIANT_ID, id);
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(DATA_VARIANT_ID), 0, 5);
    }
}