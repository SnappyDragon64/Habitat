package mod.schnappdragon.bloom_and_gloom.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class BGFlowerBlock extends FlowerBlock {
    private final Supplier<Effect> stewEffect;
    private final int stewEffectDuration;

    public BGFlowerBlock(Supplier<Effect> stewEffect, int stewEffectDuration, Properties properties) {
        super(Effects.REGENERATION, stewEffectDuration, properties);
        this.stewEffect = stewEffect;
        this.stewEffectDuration = stewEffectDuration;
    }

    @Override
    public Effect getStewEffect() {
        return this.stewEffect.get();
    }

    @Override
    public int getStewEffectDuration() {
        return this.stewEffectDuration * (this.stewEffect.get().isInstant() ? 1 : 20);
    }

    /*
     * Block Flammability Methods
     */

    @Override
    public int getFlammability(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader worldIn, BlockPos pos, Direction face) {
        return 60;
    }

}
