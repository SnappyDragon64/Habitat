package mod.schnappdragon.habitat.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;
import java.util.Map;

public class WallOrBaseItem extends BlockItem {
    protected final Block wallBlock;

    public WallOrBaseItem(Block baseBlock, Block wallBlockIn, Item.Properties propertiesIn) {
        super(baseBlock, propertiesIn);
        this.wallBlock = wallBlockIn;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = this.wallBlock.getStateForPlacement(context);
        BlockState state1 = null;
        LevelReader world = context.getLevel();
        BlockPos pos = context.getClickedPos();

        for (Direction dir : context.getNearestLookingDirections()) {
            BlockState state2 = dir == Direction.DOWN || dir == Direction.UP ? this.getBlock().getStateForPlacement(context) : state;
            if (state2 != null && state2.canSurvive(world, pos)) {
                state1 = state2;
                break;
            }
        }

        return state1 != null && world.isUnobstructed(state1, pos, CollisionContext.empty()) ? state1 : null;
    }

    public void registerBlocks(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.registerBlocks(blockToItemMap, itemIn);
        blockToItemMap.put(this.wallBlock, itemIn);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(this.wallBlock);
    }
}