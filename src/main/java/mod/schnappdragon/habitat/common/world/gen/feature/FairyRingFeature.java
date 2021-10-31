package mod.schnappdragon.habitat.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Arrays;
import java.util.Random;

public class FairyRingFeature extends Feature<NoneFeatureConfiguration> {
    public FairyRingFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(WorldGenLevel reader, ChunkGenerator generator, Random rand, BlockPos pos, NoneFeatureConfiguration config) {
        int[][] XZ_PAIRS = {
                {1, 5}, {2, 5}, {3, 4}, {4, 4}, {4, 3}, {5, 2}, {5, 1}, {5, 0},
                {0, 5}, {-1, 5}, {-2, 5}, {-3, 4}, {-4, 4}, {-4, 3}, {-5, 2}, {-5, 1},
                {-1, -5}, {-2, -5}, {-3, -4}, {-4, -4}, {-4, -3}, {-5, -2}, {-5, -1}, {-5, 0},
                {0, -5}, {1, -5}, {2, -5}, {3, -4}, {4, -4}, {4, -3}, {5, -2}, {5, -1}
        };
        WeightedStateProvider mushroomProvider = new WeightedStateProvider().add(HabitatBlocks.FAIRY_RING_MUSHROOM.get().defaultBlockState(), 1).add(HabitatBlocks.FAIRY_RING_MUSHROOM.get().defaultBlockState().setValue(FairyRingMushroomBlock.MUSHROOMS, 2), 2).add(HabitatBlocks.FAIRY_RING_MUSHROOM.get().defaultBlockState().setValue(FairyRingMushroomBlock.MUSHROOMS, 3), 3).add(HabitatBlocks.FAIRY_RING_MUSHROOM.get().defaultBlockState().setValue(FairyRingMushroomBlock.MUSHROOMS, 4), 3);
        int[] bigXZ = XZ_PAIRS[rand.nextInt(32)];

        for (int[] XZ : XZ_PAIRS) {
            for (int d = -1; d >= -6; --d) {
                BlockPos.MutableBlockPos blockpos$mutable = pos.offset(XZ[0], d, XZ[1]).mutable();
                BlockState base = reader.getBlockState(blockpos$mutable.below());
                if (reader.isEmptyBlock(blockpos$mutable) && base.canOcclude()) {
                    if (Arrays.equals(XZ, bigXZ)) {
                        ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM;

                        if (configuredfeature.place(reader, generator, rand, blockpos$mutable)) {
                            break;
                        }
                    }
                    this.setBlock(reader, blockpos$mutable, mushroomProvider.getState(rand, blockpos$mutable));
                    break;
                }
            }
        }
        return true;
    }
}