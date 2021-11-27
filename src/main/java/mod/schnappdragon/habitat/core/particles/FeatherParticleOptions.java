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

public class FeatherParticleOptions extends ColorableParticleOptions {
    public static final Codec<FeatherParticleOptions> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Vector3f.CODEC.fieldOf("color").forGetter((codec) -> codec.color), Codec.FLOAT.fieldOf("scale").forGetter((codec) -> codec.scale)).apply(builder, FeatherParticleOptions::new));
    public static final ParticleOptions.Deserializer<FeatherParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public FeatherParticleOptions fromCommand(ParticleType<FeatherParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = FeatherParticleOptions.readVector3f(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new FeatherParticleOptions(vector3f, f);
        }

        public FeatherParticleOptions fromNetwork(ParticleType<FeatherParticleOptions> particleType, FriendlyByteBuf buffer) {
            return new FeatherParticleOptions(FeatherParticleOptions.readVector3f(buffer), buffer.readFloat());
        }
    };

    public FeatherParticleOptions(Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<?> getType() {
        return HabitatParticleTypes.FEATHER.get();
    }
}