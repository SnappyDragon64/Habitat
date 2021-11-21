package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.particles.FeatherParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public class FeatherParticle<T extends FeatherParticleOption> extends TextureSheetParticle {
    private FeatherParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, T particle) {
        super(world, x, y, z);
        this.hasPhysics = true;
        this.friction = 0.98F;
        this.lifetime = 360 + this.random.nextInt(40);
        this.quadSize = particle.getScale() * 0.3F;
        this.gravity = 0.001F;
        float f = 0.98F + this.random.nextFloat() * 0.02F;
        this.rCol = particle.getColor().x() * f;
        this.gCol = particle.getColor().y() * f;
        this.bCol = particle.getColor().z() * f;
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime)
            this.remove();
        else if (!(this.onGround || this.level.isFluidAtPosition(new BlockPos(this.x, this.y, this.z), (fluidState) -> !fluidState.isEmpty()))) {
            float f = age * ((float) Math.PI / 18.0F);
            this.xd += (double) Mth.cos(f) * 0.02D;
            this.yd -= this.gravity * (1.0D + (double) Mth.cos(f * 2.0F));
            this.zd += this.random.nextGaussian() * 0.0005D;

            this.move(this.xd, this.yd, this.zd);

            this.xd *= this.friction;
            this.yd *= this.friction;
            this.zd *= this.friction;
        } else {
            this.age++;
            this.xd *= 0.7F;
            this.zd *= 0.7F;
        }
    }

    public static class Provider implements ParticleProvider<FeatherParticleOption> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(FeatherParticleOption particle, ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            FeatherParticle<?> featherparticle = new FeatherParticle<>(world, x, y, z, motionX, motionY, motionZ, particle);
            featherparticle.pickSprite(this.spriteSet);
            return featherparticle;
        }
    }
}
