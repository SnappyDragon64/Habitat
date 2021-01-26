package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;
import mod.schnappdragon.bloom_and_gloom.common.block.SlimeFernBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.WallSlimeFernBlock;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SlimeFernFeature extends Feature<BlockClusterFeatureConfig> {
    public SlimeFernFeature(Codec<BlockClusterFeatureConfig> p_i231979_1_) {
        super(p_i231979_1_);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        ChunkPos chunkPos = new ChunkPos(pos);
        if (SharedSeedRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, reader.getSeed(), 987234911L).nextInt(10) == 0) {
            int i = 0;
            BlockPos pos1 = pos.add(7, 0, 7);
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

            for (int j = 0; j < config.tryCount; ++j) {
                blockpos$mutable.setAndOffset(pos1, rand.nextInt(config.xSpread + 1) - rand.nextInt(config.xSpread + 1), rand.nextInt(config.ySpread + 1) - rand.nextInt(config.ySpread + 1), rand.nextInt(config.zSpread + 1) - rand.nextInt(config.zSpread + 1));

                if ((reader.isAirBlock(blockpos$mutable) || config.isReplaceable && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable())) {
                    for (Direction dir : directions) {
                        if (reader.getBlockState(blockpos$mutable.offset(dir)).isSolidSide(reader, blockpos$mutable, dir.getOpposite())) {
                            BlockState state = config.stateProvider.getBlockState(rand, blockpos$mutable);
                            if (state.getBlock() == BGBlocks.SLIME_FERN.get()) {
                                if (dir == Direction.UP)
                                    state = state.with(SlimeFernBlock.ON_CEILING, true);
                                else if (dir != Direction.DOWN)
                                    state = BGBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());
                            }

                            config.blockPlacer.place(reader, blockpos$mutable, state, rand);
                            ++i;
                            break;
                        }
                    }
                }
            }
            return i > 0;
        }
        return false;
    }
}
