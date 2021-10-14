package mod.schnappdragon.habitat.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHasPistonDestroyEffect {
    void onPistonDestroy(World worldIn, BlockPos pos, BlockState state);
}