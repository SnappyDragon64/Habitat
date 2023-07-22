package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.HugeBallCactusConfiguration;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HugeBallCactusFeature extends Feature<HugeBallCactusConfiguration> {
    public HugeBallCactusFeature(Codec<HugeBallCactusConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeBallCactusConfiguration> context) {
        HugeBallCactusConfiguration config = context.config();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();

        if (canPlace(world, pos, config)) {
            for (int i = 0; i <= 2; i++) {
                placeLayer(world, pos, rand, i, config);
            }

            return true;
        }

        return false;
    }

    private boolean canPlace(WorldGenLevel world, BlockPos pos, HugeBallCactusConfiguration config) {
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        blockpos$mutable.setWithOffset(pos, 0, -1, 0);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                blockpos$mutable.setWithOffset(pos, i, -1, j);
                if (!world.getBlockState(blockpos$mutable).is(config.canBePlacedOn()))
                    return false;
            }
        }

        for (int i = 0; i <= 2; i++) {
            blockpos$mutable.setWithOffset(pos, 0, i, 0);

            if (!world.getBlockState(blockpos$mutable).canBeReplaced())
                return false;
        }

        return true;
    }

    private void placeLayer(WorldGenLevel world, BlockPos pos, RandomSource rand, int layer, HugeBallCactusConfiguration config) {
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (layer <= 1 || Mth.abs(i) + Mth.abs(j) != 2) {
                    blockpos$mutable.setWithOffset(pos, i, layer, j);
                    this.setBlock(world, blockpos$mutable, rand, config);
                }
            }
        }
    }

    protected void setBlock(WorldGenLevel world, BlockPos.MutableBlockPos pos, RandomSource rand, HugeBallCactusConfiguration config) {
        if (world.getBlockState(pos).canBeReplaced()) {
            BlockState state = (rand.nextFloat() < config.floweringCactusChance() ? config.floweringCactusProvider() : config.cactusProvider()).getState(rand, pos);
            this.setBlock(world, pos, state);
        }
    }
}
