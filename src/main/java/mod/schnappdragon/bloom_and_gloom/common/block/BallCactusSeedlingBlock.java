package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.common.misc.BallCactusColor;
import mod.schnappdragon.bloom_and_gloom.common.state.properties.BGBlockStateProperties;
import net.minecraft.block.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class BallCactusSeedlingBlock extends AbstractBallCactusBlock implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D);
    public static final BooleanProperty GERMINATED = BGBlockStateProperties.GERMINATED;

    public BallCactusSeedlingBlock(BallCactusColor color, AbstractBlock.Properties properties) {
        super(color, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(GERMINATED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GERMINATED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(GERMINATED) ? VoxelShapes.empty() : SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.get(GERMINATED) ? VoxelShapes.empty() : SHAPE;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return super.getRaytraceShape(state, worldIn, pos);
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(Blocks.CACTUS) || super.isValidGround(state, worldIn, pos);
    }

    /*
     * Growth Methods
     */

    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (notOnCactus(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state,random.nextInt(10) == 0)) {
            if (state.get(GERMINATED))
                worldIn.setBlockState(pos, color.getBallCactus().getDefaultState());
            else
                worldIn.setBlockState(pos, state.with(GERMINATED, true));
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return notOnCactus((World) worldIn, pos);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return notOnCactus(worldIn, pos);
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        if (state.get(GERMINATED))
            worldIn.setBlockState(pos, color.getBallCactus().getDefaultState());
        else
            worldIn.setBlockState(pos, state.with(GERMINATED, true));
    }

    private boolean notOnCactus(World worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.down()).isIn(Blocks.CACTUS);
    }
}