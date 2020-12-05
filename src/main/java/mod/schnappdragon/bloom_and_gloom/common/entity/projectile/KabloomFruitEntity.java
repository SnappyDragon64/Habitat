package mod.schnappdragon.bloom_and_gloom.common.entity.projectile;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGEntities;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class KabloomFruitEntity extends ProjectileItemEntity {
    public KabloomFruitEntity(EntityType<? extends KabloomFruitEntity> entity, World world) {
        super(entity, world);
    }

    public KabloomFruitEntity(World worldIn, LivingEntity throwerIn) {
        super(BGEntities.KABLOOM_FRUIT.get(), throwerIn, worldIn);
    }

    public KabloomFruitEntity(World worldIn, double x, double y, double z) {
        super(BGEntities.KABLOOM_FRUIT.get(), x, y, z, worldIn);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), (float) 0);
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
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
}