package mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal;

import mod.schnappdragon.bloom_and_gloom.common.block.BallCactusBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.BallCactusSeedlingBlock;
import mod.schnappdragon.bloom_and_gloom.common.block.KabloomBushBlock;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;

public class BGFindPollinationTargetGoal extends Goal {
    private final BeeEntity bee;

    public BGFindPollinationTargetGoal(BeeEntity bee) {
        this.bee = bee;
    }

    public boolean shouldExecute() {
        return this.canBeeExecute() && !this.bee.func_233678_J__();
    }

    public boolean shouldContinueExecuting() {
        return this.canBeeExecute() && !this.bee.func_233678_J__();
    }

    public boolean canBeeExecute() {
        if (this.bee.getCropsGrownSincePollination() >= 10) {
            return false;
        } else if (this.bee.getRNG().nextFloat() < 0.3F) {
            return false;
        } else {
            return this.bee.hasNectar() && this.bee.isHiveValid();
        }
    }

    public void tick() {
        if (this.bee.getRNG().nextInt(30) == 0) {
            for(int i = 1; i <= 2; ++i) {
                BlockPos pos = this.bee.getPosition().down(i);
                BlockState state = this.bee.world.getBlockState(pos);
                Block block = state.getBlock();
                boolean flag = false;
                boolean cactusFlag = false;
                IntegerProperty integerproperty = null;
                if (block.isIn(BlockTags.BEE_GROWABLES)) {
                    if (block == BGBlocks.KABLOOM_BUSH.get()) {
                        int k = state.get(KabloomBushBlock.AGE);
                        if (k < 7) {
                            flag = true;
                            integerproperty = KabloomBushBlock.AGE;
                        }
                    }

                    if (block instanceof BallCactusSeedlingBlock) {
                        BallCactusSeedlingBlock seedling = (BallCactusSeedlingBlock) block;
                        if (state.get(BallCactusSeedlingBlock.GERMINATED))
                            this.bee.world.setBlockState(pos, seedling.getColor().getBallCactus().getDefaultState());
                        else
                            this.bee.world.setBlockState(pos, state.with(BallCactusSeedlingBlock.GERMINATED, true));
                        cactusFlag = true;
                    }

                    if (block instanceof BallCactusBlock) {
                        this.bee.world.setBlockState(pos, state.with(BallCactusBlock.FLOWERING, true));
                        cactusFlag = true;
                    }

                    if (flag) {
                        this.bee.world.playEvent(2005, pos, 0);
                        this.bee.world.setBlockState(pos, state.with(integerproperty, state.get(integerproperty) + 1));
                        this.bee.addCropCounter();
                    }

                    if (cactusFlag) {
                        this.bee.world.playEvent(2005, pos, 0);
                        this.bee.addCropCounter();
                        break;
                    }
                }
            }
        }
    }
}