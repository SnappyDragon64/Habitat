package mod.schnappdragon.habitat.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GrowableFlowerBlock extends FlowerBlock implements BonemealableBlock {
    private final Supplier<Block> tallFlowerBlock;

    public GrowableFlowerBlock(Supplier<Block> tallFlowerBlock, Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
        super(stewEffect, stewEffectDuration, properties);
        this.tallFlowerBlock = tallFlowerBlock;
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean onClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {
        TallFlowerBlock tallFlowerBlock = (TallFlowerBlock) this.tallFlowerBlock.get();
        if (tallFlowerBlock.defaultBlockState().canSurvive(world, pos) && world.isEmptyBlock(pos.above())) {
            DoublePlantBlock.placeAt(world, tallFlowerBlock.defaultBlockState(), pos, 2);
        }
    }
}
