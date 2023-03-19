package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.DirectionalBlockFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class DirectionalBlockFeature extends Feature<DirectionalBlockFeatureConfiguration> {
    public DirectionalBlockFeature(Codec<DirectionalBlockFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<DirectionalBlockFeatureConfiguration> context) {
        DirectionalBlockFeatureConfiguration config = context.config();
        RandomSource rand = context.random();
        BlockPos pos = context.origin();
        WorldGenLevel world = context.level();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (Direction dir : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pos, dir);

            if (world.getBlockState(blockpos$mutableblockpos).is(config.canBePlacedOn())) {
                BlockState state = config.toPlace().getState(rand, pos).setValue(DirectionalBlock.FACING, dir.getOpposite());
                this.setBlock(world, pos, state);
                return true;
            }
        }

        return false;
    }
}
