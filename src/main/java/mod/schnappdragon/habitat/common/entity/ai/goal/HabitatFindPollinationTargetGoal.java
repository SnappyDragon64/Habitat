package mod.schnappdragon.habitat.common.entity.ai.goal;

import mod.schnappdragon.habitat.common.block.BallCactusBlock;
import mod.schnappdragon.habitat.common.block.BallCactusFlowerBlock;
import mod.schnappdragon.habitat.common.block.GrowingBallCactusBlock;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;

public class HabitatFindPollinationTargetGoal extends Goal {
    private final Bee bee;

    public HabitatFindPollinationTargetGoal(Bee beeIn) {
        this.bee = beeIn;
    }

    public boolean canUse() {
        return this.canBeeExecute() && !this.bee.isAngry();
    }

    public boolean canContinueToUse() {
        return this.canBeeExecute() && !this.bee.isAngry();
    }

    public boolean canBeeExecute() {
        if (this.bee.getCropsGrownSincePollination() >= 10) {
            return false;
        } else if (this.bee.getRandom().nextFloat() < 0.3F) {
            return false;
        } else {
            return this.bee.hasNectar() && this.bee.isHiveValid();
        }
    }

    public void tick() {
        if (this.bee.getRandom().nextInt(30) == 0) {
            for(int i = 1; i <= 2; ++i) {
                BlockPos pos = this.bee.blockPosition().below(i);
                BlockState state = this.bee.level.getBlockState(pos);
                Block block = state.getBlock();
                boolean flag = false;
                boolean setBlockFlag = false;
                IntegerProperty integerproperty = null;
                if (state.is(BlockTags.BEE_GROWABLES)) {
                    if (block == HabitatBlocks.KABLOOM_BUSH.get()) {
                        int k = state.getValue(KabloomBushBlock.AGE);
                        if (k < 7) {
                            flag = true;
                            setBlockFlag = true;
                            integerproperty = KabloomBushBlock.AGE;
                        }
                    }

                    if (block instanceof BallCactusFlowerBlock flower && ((BallCactusFlowerBlock) block).canGrow(this.bee.level, pos)) {
                        this.bee.level.setBlockAndUpdate(pos, flower.getColor().getGrowingBallCactus().defaultBlockState());
                        flag = true;
                    }

                    if (block instanceof GrowingBallCactusBlock cactus) {
                        this.bee.level.setBlockAndUpdate(pos, cactus.getColor().getBallCactus().defaultBlockState());
                        flag = true;
                    }

                    if (block instanceof BallCactusBlock cactus) {
                        this.bee.level.setBlockAndUpdate(pos, cactus.getColor().getFloweringBallCactus().defaultBlockState());
                        flag = true;
                    }

                    if (flag) {
                        if (setBlockFlag)
                            this.bee.level.setBlockAndUpdate(pos, state.setValue(integerproperty, state.getValue(integerproperty) + 1));

                        this.bee.level.levelEvent(2005, pos, 0);
                        this.bee.incrementNumCropsGrownSincePollination();
                    }
                }
            }
        }
    }
}