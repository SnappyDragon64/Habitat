package mod.schnappdragon.bloom_and_gloom.common.entity.ai.goal;

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
                BlockPos blockpos = this.bee.getPosition().down(i);
                BlockState blockstate = this.bee.world.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                boolean flag = false;
                IntegerProperty integerproperty = null;
                if (block.isIn(BlockTags.BEE_GROWABLES)) {
                    if (block == BGBlocks.KABLOOM_BUSH.get()) {
                        int k = blockstate.get(KabloomBushBlock.AGE);
                        if (k < 7) {
                            flag = true;
                            integerproperty = KabloomBushBlock.AGE;
                        }
                    }

                    if (flag) {
                        this.bee.world.playEvent(2005, blockpos, 0);
                        this.bee.world.setBlockState(blockpos, blockstate.with(integerproperty, blockstate.get(integerproperty) + 1));
                        this.bee.addCropCounter();
                    }
                }
            }
        }
    }
}