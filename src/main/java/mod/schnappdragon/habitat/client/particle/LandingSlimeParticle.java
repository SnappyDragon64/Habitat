package mod.schnappdragon.habitat.client.particle;

import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;

public class LandingSlimeParticle extends DripParticle.Landing {
    public LandingSlimeParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z, fluid);
    }

    public static class LandingSlimeFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public LandingSlimeFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LandingSlimeParticle slimeparticle = new LandingSlimeParticle(worldIn, x, y, z, Fluids.EMPTY);
            slimeparticle.setMaxAge((int) (128.0D / (Math.random() * 0.8D + 0.2D)));
            slimeparticle.setColor(0.443F, 0.675F, 0.427F);
            slimeparticle.selectSpriteRandomly(this.spriteSet);
            return slimeparticle;
        }
    }
}
