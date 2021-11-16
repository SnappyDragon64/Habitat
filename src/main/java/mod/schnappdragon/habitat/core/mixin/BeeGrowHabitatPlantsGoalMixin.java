package mod.schnappdragon.habitat.core.mixin;

import mod.schnappdragon.habitat.common.block.BallCactusBlock;
import mod.schnappdragon.habitat.common.block.BallCactusFlowerBlock;
import mod.schnappdragon.habitat.common.block.GrowingBallCactusBlock;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Bee.BeeGrowCropGoal.class)
public class BeeGrowHabitatPlantsGoalMixin {
    @Shadow
    @Final
    Bee this$0;

    @Inject(
            method = "tick()V",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/Tag;)Z"),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void habitat_beeGrowHabitatPlantsGoalMixin(CallbackInfo ci, int chance, BlockPos pos, BlockState state, Block block) {
        IntegerProperty property = null;
        BlockState nextState = null;

        if (block == HabitatBlocks.KABLOOM_BUSH.get() && state.getValue(KabloomBushBlock.AGE) < 7)
            property = KabloomBushBlock.AGE;
        else if (block instanceof BallCactusFlowerBlock flower && flower.canGrow(this$0.level, pos))
            nextState = flower.getColor().getGrowingBallCactus().defaultBlockState();
        else if (block instanceof GrowingBallCactusBlock cactus)
            nextState = cactus.getColor().getBallCactus().defaultBlockState();
        else if (block instanceof BallCactusBlock cactus)
            nextState = cactus.getColor().getFloweringBallCactus().defaultBlockState();

        if (property != null)
            nextState = state.setValue(property, state.getValue(property) + 1);

        if (nextState != null) {
            this$0.level.setBlockAndUpdate(pos, nextState);
            this$0.level.levelEvent(2005, pos, 0);
            this$0.incrementNumCropsGrownSincePollination();
        }
    }
}