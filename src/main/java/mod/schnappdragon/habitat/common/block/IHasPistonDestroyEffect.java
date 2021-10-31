package mod.schnappdragon.habitat.common.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IHasPistonDestroyEffect {
    void onPistonDestroy(Level worldIn, BlockPos pos, BlockState state);
}