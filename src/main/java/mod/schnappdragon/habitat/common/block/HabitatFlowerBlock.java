package mod.schnappdragon.habitat.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class HabitatFlowerBlock extends FlowerBlock {
    private final Supplier<Effect> stewEffect;
    private final int stewEffectDuration;

    public HabitatFlowerBlock(Supplier<Effect> stewEffect, int stewEffectDuration, Properties properties) {
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
}
