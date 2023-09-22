package mod.schnappdragon.habitat.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;

public class BlowballPuffParticle extends TextureSheetParticle {
    private final float rollFactor;

    private BlowballPuffParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.pickSprite(spriteSet);

        this.lifetime = (int) (320 + random.nextDouble() * 320);

        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;

        this.oRoll = -Mth.PI / 4;
        this.roll = -Mth.PI / 4;
        this.rollFactor = Mth.PI * Mth.sqrt((float) (Mth.square(motionX) + Mth.square(motionY) + Mth.square(motionZ)));
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.oRoll = this.roll;
        BlockPos pos = new BlockPos((int) this.x, (int) this.y, (int) this.z);

        if (this.age++ >= this.lifetime || this.level.getFluidState(pos).is(FluidTags.LAVA))
            this.remove();
        else if (!this.level.getFluidState(pos).isEmpty()) {
            this.xd = 0.0D;
            this.yd = 0.0D;
            this.zd = 0.0D;
            this.roll = 0.0F;
            this.age++;
        } else {
            if (!this.onGround) {
                this.roll = -Mth.PI / 4 + Mth.sin(this.age * this.rollFactor) * Mth.PI / 4.0F;
            } else
                this.age++;
        }

        this.move(xd, yd, zd);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BlowballPuffParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}