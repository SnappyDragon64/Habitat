package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.AbstractSlimeFernBlock;
import mod.schnappdragon.habitat.common.block.SlimeFernBlock;
import mod.schnappdragon.habitat.common.block.WallSlimeFernBlock;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.SlimeFernFeatureConfiguration;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class SlimeFernFeature extends Feature<SlimeFernFeatureConfiguration> {
    public SlimeFernFeature(Codec<SlimeFernFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<SlimeFernFeatureConfiguration> context) {
        SlimeFernFeatureConfiguration config = context.config();
        RandomSource rand = context.random();
        BlockPos pos = context.origin();
        WorldGenLevel world = context.level();
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = config.xzSpread() + 1;
        int k = config.ySpread() + 1;
        BlockState baseState = config.baseStateProvider().getState(rand, pos);
        BlockState ceilingState = config.ceilingStateProvider().getState(rand, pos);
        BlockState wallState = config.wallStateProvider().getState(rand, pos);

        for (int l = 0; l < config.tries(); ++l) {
            blockpos$mutableblockpos.setWithOffset(pos, rand.nextInt(j) - rand.nextInt(j), rand.nextInt(k) - rand.nextInt(k), rand.nextInt(j) - rand.nextInt(j));

            if (world.isEmptyBlock(blockpos$mutableblockpos)) {
                for (Direction dir : Direction.values()) {
                    if (world.getBlockState(blockpos$mutableblockpos.relative(dir)).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        BlockState state;

                        if (dir == Direction.DOWN)
                            state = baseState;
                        else if (dir == Direction.UP)
                            state = ceilingState;
                        else
                            state = wallState.hasProperty(WallSlimeFernBlock.HORIZONTAL_FACING) ? wallState.setValue(WallSlimeFernBlock.HORIZONTAL_FACING, dir.getOpposite()) : wallState;

                        this.setBlock(world, blockpos$mutableblockpos, state/*.setValue(AbstractSlimeFernBlock.SLIMY, true)*/);

                        ++i;
                        break;
                    }
                }
            }
        }

        return i > 0;
    }
}
