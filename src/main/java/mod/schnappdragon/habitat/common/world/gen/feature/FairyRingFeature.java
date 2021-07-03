package mod.schnappdragon.habitat.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FairyRingFeature extends Feature<NoFeatureConfig> {
    public FairyRingFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (reader.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK)) {
            WeightedBlockStateProvider mushroomProvider = new WeightedBlockStateProvider().addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState(), 1).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 2), 2).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 3), 3).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 4), 3);
            boolean bigFlag = false;

            for (int i = -4; i <= 5; ++i) {
                for (int j = -4; j <= 5; ++j) {
                    BlockPos.Mutable blockpos$mutable = pos.add(i, 0, j).toMutable();
                    double distance = MathHelper.sqrt(pos.distanceSq(blockpos$mutable));

                    if (distance >= 4 && distance < 5) {
                        for (int k = 5; k >= -4; --k) {
                            BlockPos.Mutable blockpos$mutable1 = blockpos$mutable.add(0, k, 0).toMutable();
                            if (reader.isAirBlock(blockpos$mutable1) && reader.getBlockState(blockpos$mutable1.down()).isIn(Blocks.GRASS_BLOCK)) {
                                if (!bigFlag && rand.nextInt(10) == 0) {
                                    ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM;

                                    if (configuredfeature.generate(reader, generator, rand, blockpos$mutable1)) {
                                        bigFlag = true;
                                        continue;
                                    }
                                }
                                reader.setBlockState(blockpos$mutable1, mushroomProvider.getBlockState(rand, blockpos$mutable), 2);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
