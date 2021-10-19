package mod.schnappdragon.habitat.common.entity.monster;

import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.JumpController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PookaEntity extends AnimalEntity implements IMob, IForgeShearable {
    private int jumpTicks;
    private int jumpDuration;
    private boolean wasOnGround;
    private int currentMoveTypeDuration;

    public PookaEntity(EntityType<? extends PookaEntity> entityType, World world) {
        super(entityType, world);
        this.jumpController = new PookaEntity.JumpHelperController(this);
        this.moveController = new PookaEntity.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
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
            RabbitEntity rabbit = EntityType.RABBIT.create(world);
            rabbit.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            rabbit.setHealth(this.getHealth());
            rabbit.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                rabbit.setCustomName(this.getCustomName());
                rabbit.setCustomNameVisible(this.isCustomNameVisible());
            }

            if (this.isNoDespawnRequired()) {
                rabbit.enablePersistence();
            }

            /// SET RABBIT TYPE AND DATA

            rabbit.setChild(this.isChild());
            rabbit.setInvulnerable(this.isInvulnerable());
            world.addEntity(rabbit);
        }
        return Collections.singletonList(new ItemStack(HabitatItems.FAIRY_RING_MUSHROOM.get(), 4));
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
        return HabitatEntityTypes.POOKA.get().create(serverWorld);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(HabitatItems.POOKA_SPAWN_EGG.get());
    }

    /*
     * Spawn Methods
     */

    public static boolean canPookaSpawn(EntityType<PookaEntity> pooka, IWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return true;
    }

    /*
     * Damage Methods
     */

    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn.getType() == EntityType.RABBIT) {
            RabbitEntity rabbit = (RabbitEntity) entityIn;
            rabbit.playSound( HabitatSoundEvents.ENTITY_RABBIT_CONVERTED_TO_POOKA.get(), 1.0F, rabbit.isChild() ? (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.5F : (rabbit.getRNG().nextFloat() - rabbit.getRNG().nextFloat()) * 0.2F + 1.0F);
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

    /*
     * Jumping Methods
     */

    public void updateAITasks() {
        if (this.currentMoveTypeDuration > 0)
            --this.currentMoveTypeDuration;

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            if (this.currentMoveTypeDuration == 0) {
                LivingEntity livingentity = this.getAttackTarget();
                if (livingentity != null && this.getDistanceSq(livingentity) < 16.0D) {
                    this.calculateRotationYaw(livingentity.getPosX(), livingentity.getPosZ());
                    this.moveController.setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), this.moveController.getSpeed());
                    this.startJumping();
                    this.wasOnGround = true;
                }
            }

            PookaEntity.JumpHelperController pookaentity$jumphelpercontroller = (PookaEntity.JumpHelperController) this.jumpController;
            if (!pookaentity$jumphelpercontroller.getIsJumping())
                if (this.moveController.isUpdating() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigator.getPath();
                    Vector3d vector3d = new Vector3d(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ());
                    if (path != null && !path.isFinished())
                        vector3d = path.getPosition(this);

                    this.calculateRotationYaw(vector3d.x, vector3d.z);
                    this.startJumping();
                } else if (!pookaentity$jumphelpercontroller.canJump())
                    this.enableJumpControl();
        }

        this.wasOnGround = this.onGround;
    }

    public boolean shouldSpawnRunningEffects() {
        return false;
    }

    private void calculateRotationYaw(double x, double z) {
        this.rotationYaw = (float) (MathHelper.atan2(z - this.getPosZ(), x - this.getPosX()) * (double) (180F / (float) Math.PI)) - 90.0F;
    }

    private void enableJumpControl() {
        ((PookaEntity.JumpHelperController) this.jumpController).setCanJump(true);
    }

    private void disableJumpControl() {
        ((PookaEntity.JumpHelperController) this.jumpController).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveController.getSpeed() < 2.2D)
            this.currentMoveTypeDuration = 10;
        else
            this.currentMoveTypeDuration = 1;
    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    public void livingTick() {
        super.livingTick();
        if (this.jumpTicks != this.jumpDuration)
            ++this.jumpTicks;
        else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }

    }

    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.getPosY() + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && !path.isFinished()) {
                Vector3d vector3d = path.getPosition(this);
                if (vector3d.y > this.getPosY() + 0.5D)
                    return 0.5F;
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else
            return 0.5F;
    }

    protected void jump() {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D) {
            double d1 = horizontalMag(this.getMotion());
            if (d1 < 0.01D)
                this.moveRelative(0.1F, new Vector3d(0.0D, 0.0D, 1.0D));
        }

        if (!this.world.isRemote)
            this.world.setEntityState(this, (byte) 1);
    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float p_175521_1_) {
        return this.jumpDuration == 0 ? 0.0F : ((float) this.jumpTicks + p_175521_1_) / (float) this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed) {
        this.getNavigator().setSpeed(newSpeed);
        this.moveController.setMoveTo(this.moveController.getX(), this.moveController.getY(), this.moveController.getZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping)
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 1) {
            this.handleRunningEffect();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        }
        else
            super.handleStatusUpdate(id);

    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getWidth() * 0.4F);
    }

    public class JumpHelperController extends JumpController {
        private final PookaEntity pooka;
        private boolean canJump;

        public JumpHelperController(PookaEntity pooka) {
            super(pooka);
            this.pooka = pooka;
        }

        public boolean getIsJumping() {
            return this.isJumping;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        public void tick() {
            if (this.isJumping) {
                this.pooka.startJumping();
                this.isJumping = false;
            }
        }
    }

    static class MoveHelperController extends MovementController {
        private final PookaEntity pooka;
        private double nextJumpSpeed;

        public MoveHelperController(PookaEntity pooka) {
            super(pooka);
            this.pooka = pooka;
        }

        public void tick() {
            if (this.pooka.onGround && !this.pooka.isJumping && !((PookaEntity.JumpHelperController) this.pooka.jumpController).getIsJumping())
                this.pooka.setMovementSpeed(0.0D);
            else if (this.isUpdating())
                this.pooka.setMovementSpeed(this.nextJumpSpeed);

            super.tick();
        }

        public void setMoveTo(double x, double y, double z, double speedIn) {
            if (this.pooka.isInWater())
                speedIn = 1.5D;

            super.setMoveTo(x, y, z, speedIn);
            if (speedIn > 0.0D)
                this.nextJumpSpeed = speedIn;
        }
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