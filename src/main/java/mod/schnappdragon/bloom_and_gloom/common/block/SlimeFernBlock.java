package mod.schnappdragon.bloom_and_gloom.common.block;

import mod.schnappdragon.bloom_and_gloom.common.state.properties.BGBlockStateProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class SlimeFernBlock extends AbstractSlimeFernBlock {
    private static final VoxelShape GROUNDED_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    private static final VoxelShape ON_CEILING_SHAPE = Block.makeCuboidShape(0.0D, 1.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public static final BooleanProperty ON_CEILING = BGBlockStateProperties.ON_CEILING;

    public SlimeFernBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(ON_CEILING, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ON_CEILING);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(ON_CEILING) ? ON_CEILING_SHAPE : GROUNDED_SHAPE;
    }

    /*
     * Placement Methods
     */

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        for(Direction dir : context.getNearestLookingDirections()) {
            if (dir.getAxis() == Direction.Axis.Y) {
                return this.getDefaultState().with(ON_CEILING, (dir == Direction.UP));
            }
        }
        return null;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return getBlockConnected(stateIn).getOpposite() == facing && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction dir = getBlockConnected(state).getOpposite();
        BlockState state1 = worldIn.getBlockState(pos.offset(dir));
        return state1.isSolidSide(worldIn, pos.offset(dir), dir.getOpposite()) || state1.isIn(Blocks.FARMLAND);
    }

    protected static Direction getBlockConnected(BlockState state) {
        return state.get(ON_CEILING) ? Direction.DOWN : Direction.UP;
    }
}
