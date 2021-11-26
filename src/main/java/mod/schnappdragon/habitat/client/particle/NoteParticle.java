package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.particles.NoteParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;

public class NoteParticle<T extends NoteParticleOption> extends TextureSheetParticle {
    private NoteParticle(ClientLevel world, double x, double y, double z, T particle, SpriteSet spriteSet) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.speedUpWhenYMotionIsBlocked = true;
        this.pickSprite(spriteSet);
        this.friction = 0.66F;

        this.xd *= 0.01F;
        this.yd *= 0.01F;
        this.zd *= 0.01F;
        this.yd += 0.2D;

        float f1 = 0.98F + this.random.nextFloat() * 0.02F;
        this.rCol = particle.getColor().x() * f1;
        this.gCol = particle.getColor().y() * f1;
        this.bCol = particle.getColor().z() * f1;

        this.quadSize *= particle.getScale();
        this.lifetime = 5;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public float getQuadSize(float scale) {
        return this.quadSize * Mth.clamp(((float) this.age + scale) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public static class Provider implements ParticleProvider<NoteParticleOption> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(NoteParticleOption particle, ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
            return new NoteParticle<>(world, x, y, z, particle, sprite);
        }
    }
}