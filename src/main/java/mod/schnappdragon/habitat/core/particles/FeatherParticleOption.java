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

public class FeatherParticleOption extends ColorableParticleOptions {
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

    public FeatherParticleOption(Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<?> getType() {
        return HabitatParticleTypes.FEATHER.get();
    }
}