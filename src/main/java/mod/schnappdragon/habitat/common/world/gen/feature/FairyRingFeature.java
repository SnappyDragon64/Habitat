package mod.schnappdragon.habitat.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Arrays;
import java.util.Random;

public class FairyRingFeature extends Feature<NoFeatureConfig> {
    public FairyRingFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int[][] XZ_PAIRS = {
                {1, 5}, {2, 5}, {3, 4}, {4, 4}, {4, 3}, {5, 2}, {5, 1}, {5, 0},
                {0, 5}, {-1, 5}, {-2, 5}, {-3, 4}, {-4, 4}, {-4, 3}, {-5, 2}, {-5, 1},
                {-1, -5}, {-2, -5}, {-3, -4}, {-4, -4}, {-4, -3}, {-5, -2}, {-5, -1}, {-5, 0},
                {0, -5}, {1, -5}, {2, -5}, {3, -4}, {4, -4}, {4, -3}, {5, -2}, {5, -1}
        };
        WeightedBlockStateProvider mushroomProvider = new WeightedBlockStateProvider().addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState(), 1).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 2), 2).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 3), 3).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 4), 3);
        int[] bigXZ = XZ_PAIRS[rand.nextInt(32)];

        for (int[] XZ : XZ_PAIRS) {
            for (int d = -1; d >= -6; --d) {
                BlockPos.Mutable blockpos$mutable = pos.add(XZ[0], d, XZ[1]).toMutable();
                BlockState base = reader.getBlockState(blockpos$mutable.down());
                if (reader.isAirBlock(blockpos$mutable) && base.isSolid()) {
                    if (Arrays.equals(XZ, bigXZ)) {
                        ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM;

                        if (configuredfeature.generate(reader, generator, rand, blockpos$mutable)) {
                            break;
                        }
                    }
                    this.setBlockState(reader, blockpos$mutable, mushroomProvider.getBlockState(rand, blockpos$mutable));
                    break;
                }
            }
        }
        return true;
    }
}