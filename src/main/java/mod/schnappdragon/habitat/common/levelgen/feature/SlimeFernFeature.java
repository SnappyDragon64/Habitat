package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.AbstractSlimeFernBlock;
import mod.schnappdragon.habitat.common.block.SlimeFernBlock;
import mod.schnappdragon.habitat.common.block.WallSlimeFernBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SlimeFernFeature extends Feature<NoneFeatureConfiguration> {
    public SlimeFernFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        int i = 0;
        BlockPos centrePos = pos.offset(7, 0, 7);
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP};

        for (int j = 0; j < 64; ++j) {
            blockpos$mutable.setWithOffset(centrePos, rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(4) - rand.nextInt(4));

            if (world.isEmptyBlock(blockpos$mutable)) {
                for (Direction dir : directions) {
                    if (world.getBlockState(blockpos$mutable.relative(dir)).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        BlockState state;
                        if (dir == Direction.DOWN)
                            state = HabitatBlocks.SLIME_FERN.get().defaultBlockState();
                        else if (dir == Direction.UP)
                            state = HabitatBlocks.SLIME_FERN.get().defaultBlockState().setValue(SlimeFernBlock.ON_CEILING, true);
                        else
                            state = HabitatBlocks.WALL_SLIME_FERN.get().defaultBlockState().setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite());

                        this.setBlock(world, blockpos$mutable, state.setValue(AbstractSlimeFernBlock.SLIMY, true));

                        ++i;
                        break;
                    }
                }
            }
        }

        return i > 0;
    }
}
