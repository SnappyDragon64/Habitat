package mod.schnappdragon.habitat.common.block;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.FlowerBlock;

import java.util.function.Supplier;

public class HabitatFlowerBlock extends FlowerBlock {
    private final Supplier<MobEffect> stewEffect;
    private final int stewEffectDuration;

    public HabitatFlowerBlock(Supplier<MobEffect> stewEffect, int stewEffectDuration, Properties properties) {
        super(MobEffects.REGENERATION, stewEffectDuration, properties);
        this.stewEffect = stewEffect;
        this.stewEffectDuration = stewEffectDuration;
    }

    @Override
    public MobEffect getSuspiciousStewEffect() {
        return this.stewEffect.get();
    }

    @Override
    public int getEffectDuration() {
        return this.stewEffectDuration * (this.stewEffect.get().isInstantenous() ? 1 : 20);
    }
}
