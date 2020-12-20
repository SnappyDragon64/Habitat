package mod.schnappdragon.bloom_and_gloom.common.entity.projectile;

import mod.schnappdragon.bloom_and_gloom.core.misc.BGDamageSources;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGEntityTypes;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.ParametersAreNonnullByDefault;

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
            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.8D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void onImpact(RayTraceResult result) {
        Vector3d vec = result.getHitVec();

        ItemEntity item = new ItemEntity(this.world, vec.getX() + 0.5F * this.rand.nextDouble() * (this.rand.nextBoolean() ? 1 : -1), vec.getY() + 0.5F * this.rand.nextDouble(), vec.getZ() + 0.5F * this.rand.nextDouble() * (this.rand.nextBoolean() ? 1 : -1), new ItemStack(BGItems.KABLOOM_SEEDS.get()));
        item.setDefaultPickupDelay();
        this.world.addEntity(item);

        createExplosion(vec.getX(), vec.getY(), vec.getZ());

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
    }

    private void createExplosion(double x, double y, double z) {
        Explosion kabloom = new Explosion(this.world, null, BGDamageSources.causeKabloomDamage(this, this.func_234616_v_()), null, x, y, z, 0.7F, false, Explosion.Mode.NONE);
        kabloom.doExplosionA();
        this.world.playSound(x, y, z, BGSoundEvents.ENTITY_KABLOOM_FRUIT_EXPLODE.get(), SoundCategory.PLAYERS, 1.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F), true);
        this.world.addParticle(ParticleTypes.EXPLOSION, x, y, z, 1.0D, 0.0D, 0.0D);
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