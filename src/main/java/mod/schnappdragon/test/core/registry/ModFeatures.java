package mod.schnappdragon.test.core.registry;

import com.google.common.collect.ImmutableSet;
import mod.schnappdragon.test.core.Test;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;

import static mod.schnappdragon.test.common.block.RafflesiaBlock.AGE;
import static mod.schnappdragon.test.common.block.RafflesiaBlock.HORIZONTAL_FACING;


public class ModFeatures {

    public static final ConfiguredFeature<?, ?> RAFFLESIA_PATCH = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Test.MOD_ID + ":" + "rafflesia_patch",
            Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
                    new WeightedBlockStateProvider().addWeightedBlockstate(ModBlocks.RAFFLESIA_BLOCK.get().getDefaultState().with(AGE, 2), 1)
                            .addWeightedBlockstate(ModBlocks.RAFFLESIA_BLOCK.get().getDefaultState().with(HORIZONTAL_FACING, Direction.SOUTH).with(AGE, 2), 1)
                            .addWeightedBlockstate(ModBlocks.RAFFLESIA_BLOCK.get().getDefaultState().with(HORIZONTAL_FACING, Direction.EAST).with(AGE, 2), 1)
                            .addWeightedBlockstate(ModBlocks.RAFFLESIA_BLOCK.get().getDefaultState().with(HORIZONTAL_FACING, Direction.WEST).with(AGE, 2), 1),
                    new SimpleBlockPlacer())).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.PODZOL))
                    .xSpread(4)
                    .ySpread(4)
                    .zSpread(4)
                    .tries(2)
                    .build())
                    .withPlacement(Features.Placements.PATCH_PLACEMENT).withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 10))));
}