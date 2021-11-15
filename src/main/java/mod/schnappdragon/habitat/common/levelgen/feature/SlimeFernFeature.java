package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.SlimeFernBlock;
import mod.schnappdragon.habitat.common.block.WallSlimeFernBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class SlimeFernFeature extends Feature<RandomPatchConfiguration> {
    public SlimeFernFeature(Codec<RandomPatchConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
        RandomPatchConfiguration config = context.config();
        WorldGenLevel reader = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();

        ChunkPos chunkPos = new ChunkPos(pos);
        if (WorldgenRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, reader.getSeed(), 987234911L).nextInt(10) == 0) {
            int i = 0;
            BlockPos pos1 = pos.offset(7, 0, 7);
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
            Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

            for (int j = 0; j < config.tries; ++j) {
                blockpos$mutable.setWithOffset(pos1, rand.nextInt(config.xspread + 1) - rand.nextInt(config.xspread + 1), rand.nextInt(config.yspread + 1) - rand.nextInt(config.yspread + 1), rand.nextInt(config.zspread + 1) - rand.nextInt(config.zspread + 1));

                if (reader.isEmptyBlock(blockpos$mutable) || config.canReplace && reader.getBlockState(blockpos$mutable).getMaterial().isReplaceable()) {
                    for (Direction dir : directions) {
                        if (reader.getBlockState(blockpos$mutable.relative(dir)).is(Tags.Blocks.STONE)) {
                            BlockState state = config.stateProvider.getState(rand, blockpos$mutable);

                            if (state.getBlock() == HabitatBlocks.SLIME_FERN.get()) {
                                if (dir == Direction.UP)
                                    state = state.setValue(SlimeFernBlock.ON_CEILING, true);
                                else if (dir != Direction.DOWN)
                                    state = HabitatBlocks.WALL_SLIME_FERN.get().defaultBlockState().setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());
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
