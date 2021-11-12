package mod.schnappdragon.habitat.core.mixin;

import mod.schnappdragon.habitat.common.block.HasPistonDestroyEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Mixin(PistonBaseBlock.class)
public class PistonDestroyEffectsMixin {
    @Inject(
            method = "moveBlocks(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Z)Z",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void habitat_pistonDestroyEffectsMixin(Level worldIn, BlockPos pos, Direction directionIn, boolean extending, CallbackInfoReturnable<Boolean> cir, BlockPos blockpos, PistonStructureResolver pistonblockstructurehelper, Map<BlockPos, BlockState> map, List<BlockPos> list, List<BlockState> list1, List<BlockPos> list2, BlockState[] ablockstate, Direction direction, int j, int k, BlockPos blockpos2, BlockState blockstate1) {
        if (blockstate1.getBlock() instanceof HasPistonDestroyEffect)
            ((HasPistonDestroyEffect) blockstate1.getBlock()).onPistonDestroy(worldIn, blockpos2, blockstate1);
    }
}