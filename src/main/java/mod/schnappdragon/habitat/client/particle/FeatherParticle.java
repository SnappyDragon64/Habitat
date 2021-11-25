package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.particles.FeatherParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;

public class FeatherParticle<T extends FeatherParticleOption> extends TextureSheetParticle {
    protected float rollFactor;
    protected int rollLimit;
    protected int rollTicks;

    private FeatherParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, T particle, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.pickSprite(spriteSet);
        this.hasPhysics = true;

        this.lifetime = 360 + this.random.nextInt(40);

        float f = particle.getScale() * (0.28F + this.random.nextFloat() * 0.04F);
        this.scale(f / 0.3F);
        this.quadSize = f;

        float f1 = 0.98F + this.random.nextFloat() * 0.02F;
        this.rCol = particle.getColor().x() * f1;
        this.gCol = particle.getColor().y() * f1;
        this.bCol = particle.getColor().z() * f1;

        this.gravity = 0.02F + random.nextFloat() * 0.02F;

        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;

        this.rollFactor = (random.nextBoolean() ? -1 : 1) * (0.8F + random.nextFloat() * 4.0F) * (float) Math.PI / 180.0F;
        this.rollLimit = (6 + random.nextInt(6)) * 20;
        this.rollTicks = this.rollLimit;
        this.roll = random.nextFloat() * (2.0F * (float) Math.PI);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.oRoll = this.roll;

        if (this.age++ >= this.lifetime || this.level.getFluidState(new BlockPos(this.x, this.y, this.z)).is(FluidTags.LAVA))
            this.remove();
        else if (!this.level.getFluidState(new BlockPos(this.x, this.y, this.z)).isEmpty()) {
            this.xd *= 0.99D;
            this.yd = 0.0D;
            this.zd *= 0.99D;
            this.roll = 0.0F;
            this.age++;
        } else {
            this.yd -= 0.04 * this.gravity;

            if (!this.onGround) {
                if (this.rollTicks > 0) this.rollTicks--;
                this.roll += (float) this.rollTicks / this.rollLimit * this.rollFactor;
            }
            else
                this.age++;
        }

        this.move(xd, yd, zd);
    }

    public static class Provider implements ParticleProvider<FeatherParticleOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(FeatherParticleOption particle, ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            return new FeatherParticle<>(world, x, y, z, motionX, motionY, motionZ, particle, spriteSet);
        }
    }
}
