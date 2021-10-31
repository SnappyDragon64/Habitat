package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WoodPostBlock extends Block implements SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE_X = box(0.0F, 6.0F, 6.0F, 16.0F, 10.0F, 10.0F);
    private static final VoxelShape SHAPE_Y = box(6.0F, 0.0F, 6.0F, 10.0F, 16.0F, 10.0F);
    private static final VoxelShape SHAPE_Z = box(6.0F, 6.0F, 0.0F, 10.0F, 10.0F, 16.0F);

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
        BlockState defaultState = this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(AXIS, Axis.Y);
        for (BooleanProperty property : CHAINED)
            defaultState = defaultState.setValue(property, false);
        this.registerDefaultState(defaultState);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(AXIS)) {
            case X:
                return SHAPE_X;
            case Y:
                return SHAPE_Y;
            default:
                return SHAPE_Z;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
        for (BooleanProperty property : CHAINED)
            builder.add(property);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getRelevantState(context.getLevel(), context.getClickedPos(), context.getClickedFace().getAxis());
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        BlockState newState = this.getRelevantState(worldIn, pos, state.getValue(AXIS));
        if (!newState.equals(state))
            worldIn.setBlockAndUpdate(pos, newState);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
        if (ToolActions.AXE_STRIP.equals(toolAction) && strippedBlock != null && strippedBlock.get() instanceof WoodPostBlock) {
            BlockState newState = strippedBlock.get().defaultBlockState().setValue(WATERLOGGED, state.getValue(WATERLOGGED)).setValue(AXIS, state.getValue(AXIS));
            for (BooleanProperty property : CHAINED) {
                newState = newState.setValue(property, state.getValue(property));
            }
            return newState;
        }
        return super.getToolModifiedState(state, world, pos, player, stack, toolAction);
    }

    private BlockState getRelevantState(Level world, BlockPos pos, Axis axis) {
        BlockState state = this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER).setValue(AXIS, axis);

        for (Direction direction : Direction.values()) {
            if (direction.getAxis() == axis)
                continue;

            BlockState sideState = world.getBlockState(pos.relative(direction));
            if ((sideState.getBlock() instanceof ChainBlock && sideState.getValue(ChainBlock.AXIS) == direction.getAxis()) || (direction == Direction.DOWN && sideState.getBlock() instanceof LanternBlock && sideState.getValue(LanternBlock.HANGING)))
                state = state.setValue(CHAINED[direction.ordinal()], true);
        }

        return state;
    }
}