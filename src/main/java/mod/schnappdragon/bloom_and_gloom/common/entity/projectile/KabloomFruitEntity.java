package mod.schnappdragon.bloom_and_gloom.common.entity.projectile;

import mod.schnappdragon.bloom_and_gloom.core.misc.BGDamageSources;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGEntityTypes;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class KabloomFruitEntity extends ProjectileItemEntity {
    public KabloomFruitEntity(EntityType<? extends KabloomFruitEntity> entity, World world) {
        super(entity, world);
    }

    public KabloomFruitEntity(World worldIn, LivingEntity throwerIn) {
        super(BGEntityTypes.KABLOOM_FRUIT.get(), throwerIn, worldIn);
    }

    public KabloomFruitEntity(World worldIn, double x, double y, double z) {
        super(BGEntityTypes.KABLOOM_FRUIT.get(), x, y, z, worldIn);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), BGSoundEvents.ENTITY_KABLOOM_FRUIT_EXPLODE.get(), SoundCategory.PLAYERS, 1.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F), true);

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), (this.rand.nextFloat() - 0.5D) * 0.08D, (this.rand.nextFloat() - 0.5D) * 0.8D, (this.rand.nextFloat() - 0.5D) * 0.08D);
                this.world.addParticle(new RedstoneParticleData(0.5F, 0.8F, 0.53F, 1.0F), this.getPosX() + this.rand.nextDouble() / 2.0D * (rand.nextBoolean() ? 1 : -1), this.getPosY() + this.rand.nextDouble() / 2.0D * (rand.nextBoolean() ? 1 : -1), this.getPosZ() + this.rand.nextDouble() / 2.0D * (rand.nextBoolean() ? 1 : -1), 0.5D, 0.0D, 0.0D);
            }

            this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        Vector3d vec = result.getHitVec();
        if (this.world.getGameRules().get(GameRules.DO_ENTITY_DROPS).get()) {
            ItemEntity item = new ItemEntity(this.world, vec.getX() + 0.5F * this.rand.nextDouble() * (this.rand.nextBoolean() ? 1 : -1), vec.getY() + 0.5F * this.rand.nextDouble(), vec.getZ() + 0.5F * this.rand.nextDouble() * (this.rand.nextBoolean() ? 1 : -1), new ItemStack(BGItems.KABLOOM_SEEDS.get()));
            item.setDefaultPickupDelay();
            this.world.addEntity(item);
        }

        createExplosion();

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return BGItems.KABLOOM_FRUIT.get();
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(BGItems.KABLOOM_FRUIT.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private void createExplosion() {
        Vector3d vector3d = this.getPositionVec();

        for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(null, this.getBoundingBox().grow(0.75D))) {
            boolean flag = false;

            for(int i = 0; i < 2; ++i) {
                RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vector3d, new Vector3d(entity.getPosX(), entity.getPosYHeight(0.5D * (double) i), entity.getPosZ()), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
                if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                float dmg = 0;
                if (!entity.isImmuneToExplosions()) {
                    double dx = entity.getPosX() - this.getPosX();
                    double dy = entity.getPosYEye() - this.getPosY();
                    double dz = entity.getPosZ() - this.getPosZ();
                    double dres = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
                    if (dres != 0.0D) {
                        dx = dx / dres;
                        dy = dy / dres;
                        dz = dz / dres;
                        double df = this.getDistance(entity) > 1 ? 0.25D : 0.5D;
                        dmg = 4.0F + 4.0F * ((float) df);
                        double dred = df;
                        if (entity instanceof LivingEntity) {
                            dred = ProtectionEnchantment.getBlastDamageReduction((LivingEntity) entity, df);
                        }

                        boolean knockback = true;
                        if (entity instanceof PlayerEntity) {
                            PlayerEntity playerentity = (PlayerEntity)entity;
                            if (playerentity.isSpectator() || (playerentity.isCreative() && playerentity.abilities.isFlying)) {
                                knockback = false;
                            }
                        }

                        if (knockback)
                            entity.setMotion(entity.getMotion().add(dx * dred, dy * dred, dz * dred));
                    }
                }

                entity.attackEntityFrom(BGDamageSources.causeKabloomDamage(this, this.func_234616_v_()), dmg);
            }
        }
    }
}