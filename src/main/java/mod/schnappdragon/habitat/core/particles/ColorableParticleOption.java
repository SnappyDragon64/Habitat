package mod.schnappdragon.habitat.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import java.util.Locale;

public class ColorableParticleOption implements ParticleOptions {
    public static final ParticleOptions.Deserializer<ColorableParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public ColorableParticleOption fromCommand(ParticleType<ColorableParticleOption> type, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = ColorableParticleOption.readVector3f(reader);
            return new ColorableParticleOption(type, vector3f);
        }

        public ColorableParticleOption fromNetwork(ParticleType<ColorableParticleOption> type, FriendlyByteBuf buffer) {
            return new ColorableParticleOption(type, ColorableParticleOption.readVector3f(buffer));
        }
    };
    protected final ParticleType<ColorableParticleOption> type;
    protected final Vector3f color;

    public static Codec<ColorableParticleOption> codec(ParticleType<ColorableParticleOption> type) {
        return RecordCodecBuilder.create((builder) -> builder.group(Vector3f.CODEC.fieldOf("color").forGetter((codec) -> codec.color)).apply(builder, (color) -> new ColorableParticleOption(type, color)));
    }

    public ColorableParticleOption(ParticleType<ColorableParticleOption> type, Vector3f color) {
        this.type = type;
        this.color = color;
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
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", this.getType().getRegistryName(), this.color.x(), this.color.y(), this.color.z());
    }

    public Vector3f getColor() {
        return this.color;
    }

    public ParticleType<ColorableParticleOption> getType() {
        return this.type;
    }
}
