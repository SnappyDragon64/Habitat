package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.levelgen.placement.SlimeChunkFilter;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class HabitatPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Habitat.MODID);

    public static final RegistryObject<PlacedFeature> PATCH_RAFFLESIA = PLACED_FEATURES.register("rafflesia_patch", () -> new PlacedFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));
    public static final RegistryObject<PlacedFeature> PATCH_RAFFLESIA_SPARSE = PLACED_FEATURES.register("sparse_rafflesia_patch", () -> new PlacedFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PATCH_KABLOOM_BUSH = PLACED_FEATURES.register("kabloom_bush_patch", () -> new PlacedFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(225), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PATCH_SLIME_FERN = PLACED_FEATURES.register("slime_fern_patch", () -> new PlacedFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN.getHolder().get(), List.of(SlimeChunkFilter.filter(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40)), BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> PATCH_BALL_CACTUS = PLACED_FEATURES.register("ball_cactus_patch", () -> new PlacedFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> FAIRY_RING = PLACED_FEATURES.register("fairy_ring", () -> new PlacedFeature(HabitatConfiguredFeatures.FAIRY_RING.getHolder().get(), List.of()));
}