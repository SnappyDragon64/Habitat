package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.common.entity.animal.PasserineVariant;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class PasserineVariantTags {
    public static final TagKey<PasserineVariant> ALL = tag("all");
    public static final TagKey<PasserineVariant> FOREST = tag("forest");
    public static final TagKey<PasserineVariant> TEMPERATE = tag("temperate");
    public static final TagKey<PasserineVariant> WARM = tag("warm");
    public static final TagKey<PasserineVariant> BOREAL = tag("boreal");
    public static final TagKey<PasserineVariant> JUNGLE = tag("jungle");

    private static TagKey<PasserineVariant> tag(String id) {
        return TagKey.create(HabitatRegistries.Keys.PASSERINE_VARIANTS, new ResourceLocation(Habitat.MODID, id));
    }
}
