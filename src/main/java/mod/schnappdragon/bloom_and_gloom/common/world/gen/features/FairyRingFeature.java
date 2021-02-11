package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;
import mod.schnappdragon.bloom_and_gloom.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

public class FairyRingFeature extends Feature<NoFeatureConfig> {
    public FairyRingFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        if (reader.getBlockState(pos.down()).isIn(Blocks.GRASS_BLOCK)) {
            int spread = 2 + (rand.nextInt(10) == 0 ? 1 : (rand.nextInt(6) == 0) ? -1 : 0);

            ArrayList<BlockPos.Mutable> ring = new ArrayList<>();
            ArrayList<BlockPos.Mutable> circle = new ArrayList<>();

            for (int i = -spread; i <= spread + 1; i++) {
                for (int j = -spread; j <= spread + 1; j++) {
                    BlockPos.Mutable blockpos$mutable = pos.add(i, 0, j).toMutable();
                    double distance = MathHelper.sqrt(pos.distanceSq(blockpos$mutable));

                    if (distance >= spread & distance < spread + 1) {
                        if ((reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable() || reader.isAirBlock(blockpos$mutable)) && reader.getBlockState(blockpos$mutable.down()).isIn(Blocks.GRASS_BLOCK))
                            ring.add(blockpos$mutable);
                        else
                            return false;
                    }

                    if (distance < spread) {
                        if ((reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable() || reader.isAirBlock(blockpos$mutable)) && reader.getBlockState(blockpos$mutable.down()).isIn(Blocks.GRASS_BLOCK)) {
                            circle.add(blockpos$mutable);
                        }
                        else
                            return false;
                    }
                }
            }

            for (BlockPos.Mutable blockpos$mutable : circle) {
                reader.setBlockState(blockpos$mutable, Blocks.AIR.getDefaultState(), 2);
                if (reader.getBlockState(blockpos$mutable.up()).getMaterial().isReplaceable())
                    reader.setBlockState(blockpos$mutable.up(), Blocks.AIR.getDefaultState(), 2);
            }

            WeightedBlockStateProvider blockStateProvider = new WeightedBlockStateProvider().addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState(), 1).addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 2), 2).addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 3), 3).addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 4), 3);

            for (BlockPos.Mutable blockpos$mutable : ring) {
                reader.setBlockState(blockpos$mutable, blockStateProvider.getBlockState(rand, blockpos$mutable), 2);
                if (reader.getBlockState(blockpos$mutable.up()).getMaterial().isReplaceable())
                    reader.setBlockState(blockpos$mutable.up(), Blocks.AIR.getDefaultState(), 2);
            }

            return true;
        }
        return false;
    }
}
