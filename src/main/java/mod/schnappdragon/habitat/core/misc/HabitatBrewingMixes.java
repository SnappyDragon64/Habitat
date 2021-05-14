package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatPotions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.Potions;

public class HabitatBrewingMixes {
    public static void registerBrewingMixes() {
        addMix(Potions.AWKWARD, HabitatItems.KABLOOM_FRUIT.get(), HabitatPotions.BLAST_ENDURANCE.get());
        addMix(HabitatPotions.BLAST_ENDURANCE.get(), Items.REDSTONE, HabitatPotions.LONG_BLAST_ENDURANCE.get());
        addMix(HabitatPotions.BLAST_ENDURANCE.get(), Items.GLOWSTONE_DUST, HabitatPotions.STRONG_BLAST_ENDURANCE.get());

        addMix(Potions.AWKWARD, HabitatItems.DRIED_BALL_CACTUS.get(), HabitatPotions.PRICKLING.get());
        addMix(HabitatPotions.PRICKLING.get(), Items.REDSTONE, HabitatPotions.LONG_PRICKLING.get());
        addMix(HabitatPotions.PRICKLING.get(), Items.GLOWSTONE_DUST, HabitatPotions.STRONG_PRICKLING.get());
    }

    public static void addMix(Potion potion, Item ingredient, Potion result) {
        PotionBrewing.addMix(potion, ingredient, result);
    }
}