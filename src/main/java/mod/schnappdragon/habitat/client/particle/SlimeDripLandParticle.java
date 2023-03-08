package mod.schnappdragon.habitat.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class SlimeDripLandParticle extends DripParticle.DripLandParticle {
    public SlimeDripLandParticle(ClientLevel world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z, fluid);
    }

    public static class LandingSlimeProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public LandingSlimeProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SlimeDripLandParticle slimeparticle = new SlimeDripLandParticle(worldIn, x, y, z, Fluids.EMPTY);
            slimeparticle.setLifetime((int) (128.0D / (Math.random() * 0.8D + 0.2D)));
            slimeparticle.setColor(0.443F, 0.675F, 0.427F);
            slimeparticle.pickSprite(this.spriteSet);
            return slimeparticle;
        }
    }
}
