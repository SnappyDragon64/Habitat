package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import mod.schnappdragon.habitat.core.registry.HabitatPotions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class HabitatBrewingMixes {
    public static void registerBrewingMixes() {
        addMix(Potions.AWKWARD, HabitatItems.KABLOOM_PULP.get(), HabitatPotions.BLAST_ENDURANCE.get());
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