package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;
import mod.schnappdragon.bloom_and_gloom.common.block.SlimeFernBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.WallSlimeFernBlock;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeature;

import java.util.Random;

public class SlimeFernFeature extends Feature<BlockClusterFeatureConfig> {
    public SlimeFernFeature(Codec<BlockClusterFeatureConfig> p_i231979_1_) {
        super(p_i231979_1_);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        ChunkPos chunkPos = new ChunkPos(pos);
        if (SharedSeedRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, reader.getSeed(), 987234911L).nextInt(10) == 0) {
            int i = 0;
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
            Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

            for (int j = 0; j < config.tryCount; ++j) {
                blockpos$mutable.setAndOffset(pos, rand.nextInt(config.xSpread + 1) - rand.nextInt(config.xSpread + 1), rand.nextInt(config.ySpread + 1) - rand.nextInt(config.ySpread + 1), rand.nextInt(config.zSpread + 1) - rand.nextInt(config.zSpread + 1));

                if ((reader.isAirBlock(blockpos$mutable) || config.isReplaceable && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable())) {
                    for (Direction dir : directions) {
                        if (reader.getBlockState(blockpos$mutable.offset(dir)).isSolidSide(reader, blockpos$mutable, dir.getOpposite())) {
                            BlockState state;
                            if (dir == Direction.DOWN)
                                state = BGBlocks.SLIME_FERN.get().getDefaultState();
                            else if (dir == Direction.UP)
                                state = BGBlocks.SLIME_FERN.get().getDefaultState().with(SlimeFernBlock.ON_CEILING, true);
                            else
                                state = BGBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());

                            config.blockPlacer.place(reader, blockpos$mutable, state, rand);
                            ++i;
                            break;
                        }
                    }
                    /*
                    if (reader.getBlockState(blockpos$mutable.down()).isSolidSide(reader, blockpos$mutable, Direction.UP)) {
                        config.blockPlacer.place(reader, blockpos$mutable, BGBlocks.SLIME_FERN.get().getDefaultState(), rand);
                        ++i;
                    } else if (reader.getBlockState(blockpos$mutable.north()).isSolidSide(reader, blockpos$mutable, Direction.SOUTH)) {
                        config.blockPlacer.place(reader, blockpos$mutable, BGBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, Direction.SOUTH), rand);
                        ++i;
                    } else if (reader.getBlockState(blockpos$mutable.east()).isSolidSide(reader, blockpos$mutable, Direction.WEST)) {
                        config.blockPlacer.place(reader, blockpos$mutable, BGBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, Direction.WEST), rand);
                        ++i;
                    } else if (reader.getBlockState(blockpos$mutable.south()).isSolidSide(reader, blockpos$mutable, Direction.NORTH)) {
                        config.blockPlacer.place(reader, blockpos$mutable, BGBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, Direction.NORTH), rand);
                        ++i;
                    } else if (reader.getBlockState(blockpos$mutable.west()).isSolidSide(reader, blockpos$mutable, Direction.EAST)) {
                        config.blockPlacer.place(reader, blockpos$mutable, BGBlocks.WALL_SLIME_FERN.get().getDefaultState().with(WallSlimeFernBlock.HORIZONTAL_FACING, Direction.EAST), rand);
                        ++i;
                    } else if (reader.getBlockState(blockpos$mutable.up()).isSolidSide(reader, blockpos$mutable, Direction.DOWN)) {
                        config.blockPlacer.place(reader, blockpos$mutable, BGBlocks.SLIME_FERN.get().getDefaultState().with(SlimeFernBlock.ON_CEILING, true), rand);
                        ++i;
                    }
                     */
                }
            }
            return i > 0;
        }
        return false;
    }
}
