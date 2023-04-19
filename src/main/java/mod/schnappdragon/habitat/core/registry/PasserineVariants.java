package mod.schnappdragon.habitat.core.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.schnappdragon.habitat.common.entity.animal.PasserineVariant;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class PasserineVariants {
    public static final DeferredRegister<PasserineVariant> PASSERINE_VARIANTS = DeferredRegister.create(new ResourceLocation(Habitat.MODID, "passerine_variants"), Habitat.MODID);
    public static final Codec<PasserineVariant> CODEC = RecordCodecBuilder.create(builder -> builder.group(Codec.INT.fieldOf("feather_color").forGetter(PasserineVariant::featherColor),
            Codec.INT.fieldOf("note_color").forGetter(PasserineVariant::noteColor),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(PasserineVariant::texture)).apply(builder, PasserineVariant::new));
    public static Supplier<IForgeRegistry<PasserineVariant>> PASSERINE_VARIANT_REGISTRY = PASSERINE_VARIANTS.makeRegistry(() ->
            new RegistryBuilder<PasserineVariant>()
                    .setMaxID(Integer.MAX_VALUE - 1)
                    .hasTags()
                    .dataPackRegistry(CODEC, CODEC)
    );
}
