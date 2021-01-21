package mod.schnappdragon.bloom_and_gloom.core.registry;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;

public class BGFoods {
    public static final Food CURED_KABLOOM_FRUIT = (new Food.Builder()).hunger(3).saturation(0.2F).effect(() -> new EffectInstance(BGEffects.BLAST_RESISTANCE.get(), 1200, 0), 1.0F).build();
}
