package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.effect.MobEffectInstance;

public class HabitatFoods {
    public static final FoodProperties KABLOOM_PULP = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).effect(() -> new MobEffectInstance(HabitatEffects.BLAST_ENDURANCE.get(), 400, 0), 1.0F).fast().build();
    public static final FoodProperties DRIED_BALL_CACTUS = new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build();
}