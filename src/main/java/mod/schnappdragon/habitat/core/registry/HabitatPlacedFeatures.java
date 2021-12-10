package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.levelgen.placement.SlimeChunkFilter;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class HabitatPlacedFeatures {
    public static final PlacedFeature PATCH_RAFFLESIA = HabitatConfiguredFeatures.PATCH_RAFFLESIA.placed(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final PlacedFeature PATCH_RAFFLESIA_SPARSE = HabitatConfiguredFeatures.PATCH_RAFFLESIA.placed(RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final PlacedFeature PATCH_KABLOOM_BUSH = HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH.placed(RarityFilter.onAverageOnceEvery(300), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final PlacedFeature PATCH_SLIME_FERN = HabitatConfiguredFeatures.PATCH_SLIME_FERN.placed(SlimeChunkFilter.filter(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40)), BiomeFilter.biome());

    public static final PlacedFeature PATCH_BALL_CACTUS = HabitatConfiguredFeatures.PATCH_BALL_CACTUS.placed(RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final PlacedFeature FAIRY_RING = HabitatConfiguredFeatures.FAIRY_RING.placed();

    public static void registerPlacedFeatures() {
        register("rafflesia_patch", PATCH_RAFFLESIA);
        register("sparse_rafflesia_patch", PATCH_RAFFLESIA_SPARSE);
        register("kabloom_bush_patch", PATCH_KABLOOM_BUSH);
        register("slime_fern_patch", PATCH_SLIME_FERN);
        register("ball_cactus_patch", PATCH_BALL_CACTUS);
        register("fairy_ring", FAIRY_RING);
    }

    private static void register(String id, PlacedFeature placedFeature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Habitat.MODID, id), placedFeature);
    }
}