package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;

public class FallingSlimeParticle extends DripParticle.FallAndLandParticle {
    private FallingSlimeParticle(ClientLevel world, double x, double y, double z, Fluid fluid, ParticleOptions particleData) {
        super(world, x, y, z, fluid, particleData);
    }

    protected void postMoveUpdate() {
        if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            this.level.playLocalSound(this.x + 0.5D, this.y, this.z + 0.5D, HabitatSoundEvents.BLOCK_SLIME_FERN_DROP.get(), SoundSource.BLOCKS, 0.3F + this.level.random.nextFloat() * 2.0F / 3.0F, 1.0F, false);
        }
    }

    public static class FallingSlimeFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteSet;

        public FallingSlimeFactory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FallingSlimeParticle slimeparticle = new FallingSlimeParticle(worldIn, x, y, z, Fluids.EMPTY, HabitatParticleTypes.LANDING_SLIME.get());
            slimeparticle.gravity = 0.01F;
            slimeparticle.setColor(0.463F, 0.745F, 0.427F);
            slimeparticle.pickSprite(this.spriteSet);
            return slimeparticle;
        }
    }
}