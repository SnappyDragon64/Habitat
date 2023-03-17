package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class HugeBallCactusBlock extends AbstractHugeBallCactusBlock implements BonemealableBlock {
    public HugeBallCactusBlock(BallCactusColor color, Properties properties) {
        super(color, properties);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(10) == 0)) {
            worldIn.setBlockAndUpdate(pos, getColor().getFloweringBallCactusBlock().defaultBlockState());
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean onClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {
        world.setBlockAndUpdate(pos, getColor().getFloweringBallCactusBlock().defaultBlockState());
    }
}
