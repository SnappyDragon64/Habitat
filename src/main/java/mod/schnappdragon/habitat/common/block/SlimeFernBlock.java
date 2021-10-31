package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.common.block.state.properties.HabitatBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class SlimeFernBlock extends AbstractSlimeFernBlock {
    private static final VoxelShape GROUNDED_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    private static final VoxelShape ON_CEILING_SHAPE = Block.box(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final BooleanProperty ON_CEILING = HabitatBlockStateProperties.ON_CEILING;

    public SlimeFernBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ON_CEILING, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON_CEILING);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(ON_CEILING) ? ON_CEILING_SHAPE : GROUNDED_SHAPE;
    }

    /*
     * Placement Methods
     */

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        for(Direction dir : context.getNearestLookingDirections()) {
            if (dir.getAxis() == Direction.Axis.Y) {
                return this.defaultBlockState().setValue(ON_CEILING, (dir == Direction.UP));
            }
        }
        return null;
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return getBlockConnected(stateIn).getOpposite() == facing && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        Direction dir = getBlockConnected(state).getOpposite();
        BlockState state1 = worldIn.getBlockState(pos.relative(dir));
        return state1.isFaceSturdy(worldIn, pos.relative(dir), dir.getOpposite()) || state1.is(Blocks.FARMLAND);
    }

    protected static Direction getBlockConnected(BlockState state) {
        return state.getValue(ON_CEILING) ? Direction.DOWN : Direction.UP;
    }
}
