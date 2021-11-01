package mod.schnappdragon.habitat.common.block.entity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class HabitatTrappedChestBlockEntity extends HabitatChestBlockEntity {
    public HabitatTrappedChestBlockEntity(BlockPos pos, BlockState state) {
        super(HabitatBlockEntityTypes.TRAPPED_CHEST.get(), pos, state);
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int i, int j) {
        super.signalOpenCount(level, pos, state, i, j);
        if (i != j) {
            Block block = state.getBlock();
            level.updateNeighborsAt(pos, block);
            level.updateNeighborsAt(pos.below(), block);
        }
    }
}