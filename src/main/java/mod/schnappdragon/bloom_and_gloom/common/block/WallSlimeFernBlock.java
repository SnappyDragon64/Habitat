package mod.schnappdragon.bloom_and_gloom.common.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;
import java.util.Map;

public class WallSlimeFernBlock extends AbstractSlimeFernBlock {
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(0.0D, 2.0D, 1.0D, 16.0D, 14.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(0.0D, 2.0D, 0.0D, 16.0D, 14.0D, 15.0D), Direction.WEST, Block.makeCuboidShape(1.0D, 2.0D, 0.0D, 16.0D, 14.0D, 16.0D), Direction.EAST, Block.makeCuboidShape(0.0D, 2.0D, 0.0D, 15.0D, 14.0D, 16.0D)));

    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;

    public WallSlimeFernBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(state.get(HORIZONTAL_FACING));
    }

    /*
     * Placement Methods
     */

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction dir = state.get(HORIZONTAL_FACING);
        BlockPos pos1 = pos.offset(dir.getOpposite());
        BlockState state1 = worldIn.getBlockState(pos1);
        return state1.isSolidSide(worldIn, pos1, dir);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.getDefaultState();

        for(Direction dir : context.getNearestLookingDirections()) {
            if (dir.getAxis().isHorizontal()) {
                state = state.with(HORIZONTAL_FACING, dir.getOpposite());
                if (state.isValidPosition(context.getWorld(), context.getPos())) {
                    return state;
                }
            }
        }
        return null;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing.getOpposite() == stateIn.get(HORIZONTAL_FACING) && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn;
    }
}