package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.common.entity.animal.PasserineVariant;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.PasserineVariants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class PasserineVariantTags {
    public static final TagKey<PasserineVariant> ALL = makeTag("all");
    public static final TagKey<PasserineVariant> JUNGLE = makeTag("jungle");
    public static final TagKey<PasserineVariant> HOT = makeTag("hot");
    public static final TagKey<PasserineVariant> COLD = makeTag("cold");
    public static final TagKey<PasserineVariant> TEMPERATE = makeTag("temperate");
    public static final TagKey<PasserineVariant> COMMON = makeTag("common");

    private static TagKey<PasserineVariant> makeTag(String id) {
        return PasserineVariants.PASSERINE_VARIANT_REGISTRY.get().tags().createTagKey(new ResourceLocation(Habitat.MODID, id));
    }
}
