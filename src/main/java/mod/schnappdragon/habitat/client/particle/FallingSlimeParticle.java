package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundCategory;

public class FallingSlimeParticle extends DripParticle.FallingLiquidParticle {
    private FallingSlimeParticle(ClientWorld world, double x, double y, double z, Fluid fluid, IParticleData particleData) {
        super(world, x, y, z, fluid, particleData);
    }

    protected void updateMotion() {
        if (this.onGround) {
            this.setExpired();
            this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            this.world.playSound(this.posX + 0.5D, this.posY, this.posZ + 0.5D, HabitatSoundEvents.BLOCK_SLIME_FERN_DROP.get(), SoundCategory.BLOCKS, 0.3F + this.world.rand.nextFloat() * 2.0F / 3.0F, 1.0F, false);
        }
    }

    public static class FallingSlimeFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingSlimeFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FallingSlimeParticle slimeparticle = new FallingSlimeParticle(worldIn, x, y, z, Fluids.EMPTY, HabitatParticleTypes.LANDING_SLIME.get());
            slimeparticle.particleGravity = 0.01F;
            slimeparticle.setColor(0.463F, 0.745F, 0.427F);
            slimeparticle.selectSpriteRandomly(this.spriteSet);
            return slimeparticle;
        }
    }
}