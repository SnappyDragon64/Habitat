package mod.schnappdragon.bloom_and_gloom.core.registry;

import com.google.common.collect.ImmutableSet;
import mod.schnappdragon.bloom_and_gloom.common.block.KabloomBushBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;

public class BGConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_RAFFLESIA = Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BGBlocks.RAFFLESIA.get().getDefaultState().with(RafflesiaBlock.AGE, 2)),
            SimpleBlockPlacer.PLACER)
            .xSpread(6)
            .ySpread(1)
            .zSpread(6)
            .tries(2)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
            .build())
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .chance(5);

    public static final ConfiguredFeature<?, ?> PATCH_KABLOOM_BUSH = Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BGBlocks.KABLOOM_BUSH.get().getDefaultState().with(KabloomBushBlock.AGE, 7)),
            SimpleBlockPlacer.PLACER)
            .xSpread(4)
            .ySpread(1)
            .zSpread(4)
            .tries(20)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
            .build())
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .chance(200);

    public static final ConfiguredFeature<?, ?> PATCH_SLIME_FERN = BGFeatures.SLIME_FERN_FEATURE.get().withConfiguration(new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BGBlocks.SLIME_FERN.get().getDefaultState()),
            SimpleBlockPlacer.PLACER)
            .xSpread(4)
            .ySpread(5)
            .zSpread(4)
            .tries(60)
            .whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
            .build())
            .range(40)
            .chance(4);

    public static void registerConfiguredFeatures() {
        register("rafflesia_patch", PATCH_RAFFLESIA);
        register("kabloom_bush_patch", PATCH_KABLOOM_BUSH);
        register("slime_fern_patch", PATCH_SLIME_FERN);
    }

    private static void register(String identifier, ConfiguredFeature<?, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BloomAndGloom.MOD_ID + ":" + identifier, configuredFeature);
    }
}