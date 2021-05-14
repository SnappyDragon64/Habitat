package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatEffects;
import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;

public class HabitatFoods {
    public static final Food KABLOOM_PULP = new Food.Builder().hunger(2).saturation(0.3F).effect(() -> new EffectInstance(HabitatEffects.BLAST_ENDURANCE.get(), 400, 0), 1.0F).fastToEat().build();
    public static final Food DRIED_BALL_CACTUS = new Food.Builder().hunger(2).saturation(0.1F).build();
    public static final Food FAIRY_RING_MUSHROOM_STEW = new Food.Builder().hunger(6).saturation(0.6F).build();
}