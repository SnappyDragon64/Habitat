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

public class NoteParticleOptions extends ColorableParticleOptions {
    public static final Codec<NoteParticleOptions> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Vector3f.CODEC.fieldOf("color").forGetter((codec) -> codec.color), Codec.FLOAT.fieldOf("scale").forGetter((codec) -> codec.scale)).apply(builder, NoteParticleOptions::new));
    public static final ParticleOptions.Deserializer<NoteParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public NoteParticleOptions fromCommand(ParticleType<NoteParticleOptions> particleType, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = NoteParticleOptions.readVector3f(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new NoteParticleOptions(vector3f, f);
        }

        public NoteParticleOptions fromNetwork(ParticleType<NoteParticleOptions> particleType, FriendlyByteBuf buffer) {
            return new NoteParticleOptions(NoteParticleOptions.readVector3f(buffer), buffer.readFloat());
        }
    };

    public NoteParticleOptions(Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<?> getType() {
        return HabitatParticleTypes.NOTE.get();
    }
}