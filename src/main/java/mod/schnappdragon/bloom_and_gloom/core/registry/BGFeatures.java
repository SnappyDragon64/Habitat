package mod.schnappdragon.bloom_and_gloom.core.registry;

import com.google.common.collect.ImmutableSet;
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
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

import static mod.schnappdragon.bloom_and_gloom.common.block.RafflesiaBlock.AGE;

public class BGFeatures {
    public static final ConfiguredFeature<?, ?> RAFFLESIA_PATCH = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, BloomAndGloom.MOD_ID + ":" + "rafflesia_patch",
            Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BGBlocks.RAFFLESIA_BLOCK.get().getDefaultState().with(AGE, 2)),
                    new SimpleBlockPlacer())).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK))
                    .xSpread(4)
                    .ySpread(4)
                    .zSpread(4)
                    .tries(2)
                    .build())
                    .withPlacement(Features.Placements.PATCH_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(2, 0.4F, 1))));
}