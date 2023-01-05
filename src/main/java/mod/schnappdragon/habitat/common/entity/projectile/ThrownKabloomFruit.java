package mod.schnappdragon.habitat.common.entity.projectile;

import mod.schnappdragon.habitat.core.misc.HabitatDamageSources;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class ThrownKabloomFruit extends ThrowableItemProjectile {
    public ThrownKabloomFruit(EntityType<? extends ThrownKabloomFruit> entity, Level world) {
        super(entity, world);
    }

    public ThrownKabloomFruit(Level worldIn, LivingEntity throwerIn) {
        super(HabitatEntityTypes.KABLOOM_FRUIT.get(), throwerIn, worldIn);
    }

    public ThrownKabloomFruit(Level worldIn, double x, double y, double z) {
        super(HabitatEntityTypes.KABLOOM_FRUIT.get(), x, y, z, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isOnFire())
            explode(this.position());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), HabitatSoundEvents.KABLOOM_FRUIT_EXPLODE.get(), SoundSource.NEUTRAL, 1.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F), true);

            for (int i = 0; i < 8; ++i)
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), (this.random.nextFloat() - 0.5D) * 0.08D, (this.random.nextFloat() - 0.5D) * 0.8D, (this.random.nextFloat() - 0.5D) * 0.08D);

            this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        explode(result.getLocation());
    }

    @Override
    protected Item getDefaultItem() {
        return HabitatItems.KABLOOM_FRUIT.get();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(HabitatItems.KABLOOM_FRUIT.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Vec3 limitPistonMovement(Vec3 pos) {
        explode(this.position());
        return Vec3.ZERO;
    }

    private void explode(Vec3 vector3d) {
        if (!this.level.isClientSide) {
            this.gameEvent(GameEvent.EXPLODE, this.getOwner());

            for (Entity entity : this.level.getEntities(null, this.getBoundingBox().inflate(0.8D))) {
                boolean flag = false;

                for (int i = 0; i < 2; ++i) {
                    HitResult raytraceresult = this.level.clip(new ClipContext(vector3d, new Vec3(entity.getX(), entity.getY(0.5D * (double) i), entity.getZ()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
                    if (raytraceresult.getType() == HitResult.Type.MISS) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    float dmg = 0;
                    if (!entity.ignoreExplosion()) {
                        double dx = entity.getX() - this.getX();
                        double dy = entity.getEyeY() - this.getY();
                        double dz = entity.getZ() - this.getZ();
                        double dres = Mth.sqrt((float) (dx * dx + dy * dy + dz * dz));
                        if (dres != 0.0D) {
                            dx = dx / dres;
                            dy = dy / dres;
                            dz = dz / dres;
                            double df = this.distanceTo(entity) > 1.0F ? 0.25D : 0.5D;
                            dmg = 4.0F + 4.0F * (float) df;
                            double dred = df;
                            if (entity instanceof LivingEntity livingEntity)
                                dred = ProtectionEnchantment.getExplosionKnockbackAfterDampener(livingEntity, df) * (1.0D - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));

                            Vec3 motionVec = entity.getDeltaMovement().add(dx * dred, dy * dred, dz * dred);
                            entity.setDeltaMovement(motionVec);
                            if (entity instanceof ServerPlayer player) {
                                if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying))
                                    player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), motionVec));
                            }
                        }
                    }

                    if (entity instanceof LivingEntity)
                        entity.hurt(HabitatDamageSources.causeKabloomDamage(this, this.getOwner(), true), dmg);
                    else if (entity.isAttackable())
                        entity.hurt(HabitatDamageSources.causeKabloomDamage(this, this.getOwner(), false), dmg);

                    if (this.isOnFire() && !entity.fireImmune())
                        entity.setSecondsOnFire(1);
                }
            }

            if (this.level.getGameRules().getRule(GameRules.RULE_DOENTITYDROPS).get()) {
                ItemEntity item = new ItemEntity(this.level, vector3d.x() + this.random.nextDouble() * (this.random.nextBoolean() ? 1 : -1) * 0.5F, vector3d.y() + this.random.nextDouble() / 2, vector3d.z() + this.random.nextDouble() * (this.random.nextBoolean() ? 1 : -1) * 0.5F, new ItemStack(HabitatItems.KABLOOM_PULP.get()));
                item.setDefaultPickUpDelay();
                if (this.isOnFire() && !item.fireImmune())
                    item.setSecondsOnFire(1);
                this.level.addFreshEntity(item);
            }

            if (!this.level.isClientSide) {
                this.level.broadcastEntityEvent(this, (byte) 3);
                this.discard();
            }
        }
    }
}