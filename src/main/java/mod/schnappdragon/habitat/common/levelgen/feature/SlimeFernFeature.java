package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.WallSlimeFernBlock;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.SlimeFernConfiguration;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SlimeFernFeature extends Feature<SlimeFernConfiguration> {
    public SlimeFernFeature(Codec<SlimeFernConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<SlimeFernConfiguration> context) {
        SlimeFernConfiguration config = context.config();
        RandomSource rand = context.random();
        BlockPos pos = context.origin();
        WorldGenLevel world = context.level();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction dir : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pos, dir);

            if (world.getBlockState(blockpos$mutableblockpos).is(config.canBePlacedOn())) {
                BlockState state = switch (dir) {
                    case DOWN -> config.baseStateProvider().getState(rand, pos);
                    case UP -> config.ceilingStateProvider().getState(rand, pos);
                    default -> {
                        BlockState wallState = config.wallStateProvider().getState(rand, pos);
                        yield wallState.hasProperty(WallSlimeFernBlock.HORIZONTAL_FACING) ? wallState.setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite()) : wallState;
                    }
                };

                this.setBlock(world, pos, state);
                return true;
            }
        }

        return false;
    }
}
