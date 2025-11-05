package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class HabitatBiomeTags {
    public static final TagKey<Biome> SPAWNS_PASSERINES = tag("spawns_passerines");
    public static final TagKey<Biome> SPAWNS_FOREST_PASSERINES = tag("spawns_forest_passerines");
    public static final TagKey<Biome> SPAWNS_TEMPERATE_PASSERINES = tag("spawns_temperate_passerines");
    public static final TagKey<Biome> SPAWNS_WARM_PASSERINES = tag("spawns_warm_passerines");
    public static final TagKey<Biome> SPAWNS_BOREAL_PASSERINES = tag("spawns_boreal_passerines");
    public static final TagKey<Biome> SPAWNS_JUNGLE_PASSERINES = tag("spawns_jungle_passerines");

    private static TagKey<Biome> tag(String id) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(Habitat.MODID, id));
    }
}
