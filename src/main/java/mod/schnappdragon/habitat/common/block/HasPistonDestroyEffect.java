package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface HasPistonDestroyEffect {
    void onPistonDestroy(Level worldIn, BlockPos pos, BlockState state);
}