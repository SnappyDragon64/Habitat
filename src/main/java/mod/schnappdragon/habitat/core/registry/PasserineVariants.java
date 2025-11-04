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
    public static final DeferredRegister<PasserineVariant> PASSERINE_VARIANTS = DeferredRegister.create(HabitatRegistries.Keys.PASSERINE_VARIANTS, Habitat.MODID);
    public static final Codec<PasserineVariant> CODEC = RecordCodecBuilder.create(builder -> builder.group(Codec.INT.fieldOf("feather_color").forGetter(PasserineVariant::featherColor),
            Codec.INT.fieldOf("note_color").forGetter(PasserineVariant::noteColor),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(PasserineVariant::texture)).apply(builder, PasserineVariant::new));
    public static Supplier<IForgeRegistry<PasserineVariant>> PASSERINE_VARIANT_REGISTRY = PASSERINE_VARIANTS.makeRegistry(() ->
            new RegistryBuilder<PasserineVariant>()
                    .hasTags()
    );

    public static final class Ids {
        public static final ResourceLocation AMERICAN_GOLDFINCH = id("american_goldfinch");
        public static final ResourceLocation BALI_MYNA = id("bali_myna");
        public static final ResourceLocation COMMON_SPARROW = id("common_sparrow");
        public static final ResourceLocation EASTERN_BLUEBIRD = id("eastern_bluebird");
        public static final ResourceLocation EURASIAN_BULLFINCH = id("eurasian_bullfinch");
        public static final ResourceLocation FLAME_ROBIN = id("flame_robin");
        public static final ResourceLocation NORTHERN_CARDINAL = id("northern_cardinal");
        public static final ResourceLocation RED_THROATED_PARROTFINCH = id("red_throated_parrotfinch");
        public static final ResourceLocation VIOLET_BACKED_STARLING = id("violet_backed_starling");

    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(Habitat.MODID, path);
    }
}
