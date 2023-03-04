package mod.schnappdragon.habitat.core.registry;

import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mod.schnappdragon.habitat.common.entity.animal.PasserineVariant;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PasserineVariants {
    public static final DeferredRegister<PasserineVariant> PASSERINE_VARIANTS = DeferredRegister.create(new ResourceLocation(Habitat.MODID, "passerine_variants"), Habitat.MODID);
    public static final Codec<PasserineVariant> CODEC = RecordCodecBuilder.create(builder -> builder.group(Codec.INT.fieldOf("featherColor").forGetter(PasserineVariant::featherColor),
            Codec.INT.fieldOf("noteColor").forGetter(PasserineVariant::noteColor),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(PasserineVariant::texture)).apply(builder, PasserineVariant::new));
    public static Supplier<IForgeRegistry<PasserineVariant>> PASSERINE_VARIANT_REGISTRY = PASSERINE_VARIANTS.makeRegistry(() ->
            new RegistryBuilder<PasserineVariant>()
                    .setMaxID(Integer.MAX_VALUE - 1)
                    .hasTags()
                    .dataPackRegistry(CODEC, CODEC)
    );

    public static final RegistryObject<PasserineVariant> AMERICAN_GOLDFINCH = PASSERINE_VARIANTS.register("american_goldfinch", () -> new PasserineVariant(16052497, 16775680, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/american_goldfinch.png")));
    public static final RegistryObject<PasserineVariant> BALI_MYNA = PASSERINE_VARIANTS.register("bali_myna", () -> new PasserineVariant(16777215, 8703, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/bali_myna.png")));
    public static final RegistryObject<PasserineVariant> BLUE_JAY = PASSERINE_VARIANTS.register("blue_jay", () -> new PasserineVariant(4815308, 24063, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/blue_jay.png")));
    public static final RegistryObject<PasserineVariant> COMMON_SPARROW = PASSERINE_VARIANTS.register("common_sparrow", () -> new PasserineVariant(7488818, 16730112, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/common_sparrow.png")));
    public static final RegistryObject<PasserineVariant> EASTERN_BLUEBIRD = PASSERINE_VARIANTS.register("eastern_bluebird", () -> new PasserineVariant(5012138, 16744192, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/eastern_bluebird.png")));
    public static final RegistryObject<PasserineVariant> EURASIAN_BULLFINCH = PASSERINE_VARIANTS.register("eurasian_bullfinch", () -> new PasserineVariant(796479, 16711726, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/eurasian_bullfinch.png")));
    public static final RegistryObject<PasserineVariant> FLAME_ROBIN = PASSERINE_VARIANTS.register("flame_robin", () -> new PasserineVariant(6248013, 16739840, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/flame_robin.png")));
    public static final RegistryObject<PasserineVariant> NORTHERN_CARDINAL = PASSERINE_VARIANTS.register("northern_cardinal", () -> new PasserineVariant(13183262, 16714752, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/northern_cardinal.png")));
    public static final RegistryObject<PasserineVariant> RED_THROATED_PARROTFINCH = PASSERINE_VARIANTS.register("red_throated_parrotfinch", () -> new PasserineVariant(4487992, 16713728, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/red_throated_parrotfinch.png")));
    public static final RegistryObject<PasserineVariant> VIOLET_BACKED_STARLING = PASSERINE_VARIANTS.register("violet_backed_starling", () -> new PasserineVariant(6435209, 9175295, new ResourceLocation(Habitat.MODID, "textures/entity/passerine/violet_backed_starling.png")));
}
