package mod.schnappdragon.habitat.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import java.util.Locale;

public class FeatherParticleOption implements ParticleOptions {
    public static final Codec<FeatherParticleOption> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Vector3f.CODEC.fieldOf("color").forGetter((codec) -> codec.color), Codec.FLOAT.fieldOf("scale").forGetter((codec) -> codec.scale)).apply(builder, FeatherParticleOption::new));
    public static final ParticleOptions.Deserializer<FeatherParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public FeatherParticleOption fromCommand(ParticleType<FeatherParticleOption> particleType, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = FeatherParticleOption.readVector3f(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new FeatherParticleOption(vector3f, f);
        }

        public FeatherParticleOption fromNetwork(ParticleType<FeatherParticleOption> particleType, FriendlyByteBuf buffer) {
            return new FeatherParticleOption(FeatherParticleOption.readVector3f(buffer), buffer.readFloat());
        }
    };

    protected final Vector3f color;
    protected final float scale;

    public FeatherParticleOption(Vector3f color, float scale) {
        this.color = color;
        this.scale = Mth.clamp(scale, 0.01F, 4.0F);
    }

    public static Vector3f readVector3f(StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        float f = reader.readFloat();
        reader.expect(' ');
        float f1 = reader.readFloat();
        reader.expect(' ');
        float f2 = reader.readFloat();
        return new Vector3f(f, f1, f2);
    }

    public static Vector3f readVector3f(FriendlyByteBuf buffer) {
        return new Vector3f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
    }

    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.color.x());
        buffer.writeFloat(this.color.y());
        buffer.writeFloat(this.color.z());
        buffer.writeFloat(this.scale);
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", this.getType().getRegistryName(), this.color.x(), this.color.y(), this.color.z(), this.scale);
    }

    public Vector3f getColor() {
        return this.color;
    }

    public float getScale() {
        return this.scale;
    }

    public ParticleType<?> getType() {
        return HabitatParticleTypes.FEATHER.get();
    }
}