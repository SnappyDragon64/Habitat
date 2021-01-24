package mod.schnappdragon.bloom_and_gloom.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;
import java.util.Map;

public class WallOrBaseItem extends BlockItem {
    protected final Block wallBlock;

    public WallOrBaseItem(Block baseBlock, Block wallBlockIn, Item.Properties propertiesIn) {
        super(baseBlock, propertiesIn);
        this.wallBlock = wallBlockIn;
    }

    @Nullable
    protected BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = this.wallBlock.getStateForPlacement(context);
        BlockState state1 = null;
        IWorldReader world = context.getWorld();
        BlockPos pos = context.getPos();

        for(Direction dir : context.getNearestLookingDirections()) {
            BlockState state2 = dir == Direction.DOWN || dir == Direction.UP ? this.getBlock().getStateForPlacement(context) : state;
            if (state2 != null && state2.isValidPosition(world, pos)) {
                state1 = state2;
                break;
            }
        }

        return state1 != null && world.placedBlockCollides(state1, pos, ISelectionContext.dummy()) ? state1 : null;
    }

    public void addToBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.addToBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.put(this.wallBlock, itemIn);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(this.wallBlock);
    }
}