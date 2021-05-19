package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.LanternBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WoodPostBlock extends Block implements IWaterLoggable {
    private static final VoxelShape SHAPE_X = makeCuboidShape(0.0F, 6.0F, 6.0F, 16.0F, 10.0F, 10.0F);
    private static final VoxelShape SHAPE_Y = makeCuboidShape(6.0F, 0.0F, 6.0F, 10.0F, 16.0F, 10.0F);
    private static final VoxelShape SHAPE_Z = makeCuboidShape(6.0F, 6.0F, 0.0F, 10.0F, 10.0F, 16.0F);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty[] CHAINED = new BooleanProperty[] {
            HabitatBlockStateProperties.CHAINED_DOWN,
            HabitatBlockStateProperties.CHAINED_UP,
            HabitatBlockStateProperties.CHAINED_NORTH,
            HabitatBlockStateProperties.CHAINED_SOUTH,
            HabitatBlockStateProperties.CHAINED_WEST,
            HabitatBlockStateProperties.CHAINED_EAST
    };

    private final Supplier<Block> strippedBlock;

    public WoodPostBlock(Properties properties) {
        this(null, properties);
    }

    public WoodPostBlock(Supplier<Block> block, Properties properties) {
        super(properties);
        this.strippedBlock = block;
        BlockState defaultState = this.stateContainer.getBaseState().with(WATERLOGGED, false).with(AXIS, Axis.Y);
        for (BooleanProperty property : CHAINED)
            defaultState = defaultState.with(property, false);
        this.setDefaultState(defaultState);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case X:
                return SHAPE_X;
            case Y:
                return SHAPE_Y;
            default:
                return SHAPE_Z;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
        for (BooleanProperty property : CHAINED)
            builder.add(property);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getRelevantState(context.getWorld(), context.getPos(), context.getFace().getAxis());
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        BlockState newState = this.getRelevantState(worldIn, pos, state.get(AXIS));
        if (!newState.equals(state))
            worldIn.setBlockState(pos, newState);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if (toolType == ToolType.AXE && strippedBlock != null && strippedBlock.get() instanceof WoodPostBlock) {
            BlockState newState = strippedBlock.get().getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)).with(AXIS, state.get(AXIS));
            for (BooleanProperty property : CHAINED) {
                newState = newState.with(property, state.get(property));
            }
            return newState;
        }
        return super.getToolModifiedState(state, world, pos, player, stack, toolType);
    }

    private BlockState getRelevantState(World world, BlockPos pos, Axis axis) {
        BlockState state = this.getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER).with(AXIS, axis);

        for (Direction direction : Direction.values()) {
            if (direction.getAxis() == axis)
                continue;

            BlockState sideState = world.getBlockState(pos.offset(direction));
            if ((sideState.getBlock() instanceof ChainBlock && sideState.get(ChainBlock.AXIS) == direction.getAxis()) || (direction == Direction.DOWN && sideState.getBlock() instanceof LanternBlock && sideState.get(LanternBlock.HANGING)))
                state = state.with(CHAINED[direction.ordinal()], true);
        }

        return state;
    }
}