package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import mod.schnappdragon.habitat.common.block.state.properties.VerticalSlabType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class VerticalSlabBlock extends Block implements IWaterLoggable {
    protected static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape DOUBLE_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final EnumProperty<VerticalSlabType> TYPE = HabitatBlockStateProperties.VERTICAL_SLAB_TYPE;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (state.get(TYPE) == VerticalSlabType.DOUBLE)
            return DOUBLE_SHAPE;

        switch (state.get(FACING)) {
            case NORTH:
                return NORTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return SOUTH_SHAPE;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        BlockState state = context.getWorld().getBlockState(pos);
        if (state.getBlock() == this)
            return state.with(TYPE, VerticalSlabType.DOUBLE).with(WATERLOGGED, false);

        return getDefaultState().with(WATERLOGGED, context.getWorld().getFluidState(pos).getFluid() == Fluids.WATER).with(FACING, getDirectionForPlacement(context));
    }

    private Direction getDirectionForPlacement(BlockItemUseContext context) {
        Direction face = context.getFace();
        if (face.getAxis() != Direction.Axis.Y)
            return face;
        Vector3d diff = context.getHitVec().subtract(Vector3d.copy(context.getPos())).subtract(0.5, 0, 0.5);
        return Direction.fromAngle(-Math.toDegrees(Math.atan2(diff.getX(), diff.getZ()))).getOpposite();
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && context.getItem().getItem() == this.asItem() && context.replacingClickedOnBlock() && context.getFace() == state.get(FACING) && getDirectionForPlacement(context) == state.get(FACING);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return state.get(TYPE) != VerticalSlabType.DOUBLE && IWaterLoggable.super.canContainFluid(worldIn, pos, state, fluidIn);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        if (type == PathType.WATER)
            return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);

        return false;
    }
}