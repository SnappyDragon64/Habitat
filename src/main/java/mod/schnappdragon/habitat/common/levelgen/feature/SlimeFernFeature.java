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

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> p_159749_) {
        return false;
    }
/*
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> context) {
        RandomPatchConfiguration config = context.config();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();
        ChunkPos chunkPos = new ChunkPos(pos);

        if (WorldgenRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, world.getSeed(), 987234911L).nextInt(10) == 0) {
            int i = 0;
            BlockPos pos1 = pos.offset(7, 0, 7);
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
            Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

            for (int j = 0; j < config.tries(); ++j) {
                int xz = config.xzSpread() + 1;
                int y = config.ySpread() + 1;
                blockpos$mutable.setWithOffset(pos1, rand.nextInt(xz) - rand.nextInt(xz), rand.nextInt(y) - rand.nextInt(y), rand.nextInt(xz) - rand.nextInt(xz));

                if (world.isEmptyBlock(blockpos$mutable)) {
                    for (Direction dir : directions) {
                        if (world.getBlockState(blockpos$mutable.relative(dir)).is(Tags.Blocks.STONE)) {
                            BlockState state = config.feature().get()..getState(rand, blockpos$mutable);

                            if (state.getBlock() == HabitatBlocks.SLIME_FERN.get()) {
                                if (dir == Direction.UP)
                                    state = state.setValue(SlimeFernBlock.ON_CEILING, true);
                                else if (dir != Direction.DOWN)
                                    state = HabitatBlocks.WALL_SLIME_FERN.get().defaultBlockState().setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());
                            }

                            config.blockPlacer.place(world, blockpos$mutable, state, rand);
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
 */
}
