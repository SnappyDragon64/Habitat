package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.particles.FeatherParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;

public class FeatherParticle<T extends FeatherParticleOption> extends TextureSheetParticle {
    private FeatherParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, T particle) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.hasPhysics = true;
        this.gravity = 0.002F;
        this.lifetime = 160 + this.random.nextInt(40);
        float f = 0.98F + this.random.nextFloat() * 0.02F;
        this.rCol = particle.getColor().x() * f;
        this.gCol = particle.getColor().y() * f;
        this.bCol = particle.getColor().z() * f;
        this.quadSize = particle.getScale() * 0.3F;
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
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else if (!this.onGround) {
            this.xd = this.xd + (double) Mth.cos(age) * this.random.nextDouble() * 0.1D;
            this.yd -= this.gravity;
            this.zd = this.zd + (double) Mth.sin(age) * this.random.nextDouble() * 0.1D;
            this.move(this.xd, this.yd, this.zd);
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
