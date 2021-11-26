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

public class NoteParticleOption extends ColorableParticleOptions {
    public static final Codec<NoteParticleOption> CODEC = RecordCodecBuilder.create((builder) -> builder.group(Vector3f.CODEC.fieldOf("color").forGetter((codec) -> codec.color), Codec.FLOAT.fieldOf("scale").forGetter((codec) -> codec.scale)).apply(builder, NoteParticleOption::new));
    public static final ParticleOptions.Deserializer<NoteParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public NoteParticleOption fromCommand(ParticleType<NoteParticleOption> particleType, StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = NoteParticleOption.readVector3f(reader);
            reader.expect(' ');
            float f = reader.readFloat();
            return new NoteParticleOption(vector3f, f);
        }

        public NoteParticleOption fromNetwork(ParticleType<NoteParticleOption> particleType, FriendlyByteBuf buffer) {
            return new NoteParticleOption(NoteParticleOption.readVector3f(buffer), buffer.readFloat());
        }
    };

    public NoteParticleOption(Vector3f color, float scale) {
        super(color, scale);
    }

    @Override
    public ParticleType<?> getType() {
        return HabitatParticleTypes.NOTE.get();
    }
}