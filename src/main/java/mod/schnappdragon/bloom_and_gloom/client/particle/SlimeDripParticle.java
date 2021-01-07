package mod.schnappdragon.bloom_and_gloom.client.particle;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGParticleTypes;
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
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeDripParticle {
    @OnlyIn(Dist.CLIENT)
    public static class DrippingSlimeFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteWithAge;

        public DrippingSlimeFactory(IAnimatedSprite spriteWithAge) {
            this.spriteWithAge = spriteWithAge;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DripParticle.Dripping dripparticle$dripping = new DripParticle.Dripping(worldIn, x, y, z, Fluids.EMPTY, BGParticleTypes.FALLING_SLIME.get());
            dripparticle$dripping.particleGravity *= 0.01F;
            dripparticle$dripping.maxAge = 1;
            dripparticle$dripping.setColor(0.423F, 0.785F, 0.427F);
            dripparticle$dripping.selectSpriteRandomly(this.spriteWithAge);
            return dripparticle$dripping;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class FallingSlimeFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingSlimeFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DripParticle dripparticle = new SlimeDripParticle.FallingSlimeParticle(worldIn, x, y, z, Fluids.EMPTY, BGParticleTypes.LANDING_SLIME.get());
            dripparticle.particleGravity = 0.01F;
            dripparticle.setColor(0.463F, 0.745F, 0.427F);
            dripparticle.selectSpriteRandomly(this.spriteSet);
            return dripparticle;
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class FallingSlimeParticle extends DripParticle.FallingLiquidParticle {
        private FallingSlimeParticle(ClientWorld world, double x, double y, double z, Fluid fluid, IParticleData particleData) {
            super(world, x, y, z, fluid, particleData);
        }

        protected void updateMotion() {
            if (this.onGround) {
                this.setExpired();
                this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                //this.world.playSound(this.posX + 0.5D, this.posY, this.posZ + 0.5D, SoundEvents.BLOCK_BEEHIVE_DROP, SoundCategory.BLOCKS, 0.3F + this.world.rand.nextFloat() * 2.0F / 3.0F, 1.0F, false);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class LandingSlimeFactory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public LandingSlimeFactory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DripParticle dripparticle = new DripParticle.Landing(worldIn, x, y, z, Fluids.EMPTY);
            dripparticle.maxAge = (int)(128.0D / (Math.random() * 0.8D + 0.2D));
            dripparticle.setColor(0.443F, 0.675F, 0.427F);
            dripparticle.selectSpriteRandomly(this.spriteSet);
            return dripparticle;
        }
    }
}