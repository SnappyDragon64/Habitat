package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class BloomingDreadbudBlock extends FlowerBlock {
    private final Supplier<Block> nextStage;
    private final float threshold;

    public BloomingDreadbudBlock(Supplier<Block> nextStage, float threshold, Supplier<MobEffect> stewEffect, int stewDuration, Properties properties) {
        super(stewEffect, stewDuration, properties);
        this.nextStage = nextStage;
        this.threshold = threshold;
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getCurrentDifficultyAt(pos).getEffectiveDifficulty() >= this.threshold)
            world.setBlockAndUpdate(pos, this.nextStage.get().defaultBlockState());
    }
}
