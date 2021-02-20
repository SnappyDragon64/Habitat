package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGPotions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.Potions;

public class BGBrewingMixes {
    public static void registerBrewingMixes() {
        addMix(Potions.AWKWARD, BGItems.KABLOOM_FRUIT.get(), BGPotions.BLAST_ENDURANCE.get());
        addMix(BGPotions.BLAST_ENDURANCE.get(), Items.REDSTONE, BGPotions.LONG_BLAST_ENDURANCE.get());
        addMix(BGPotions.BLAST_ENDURANCE.get(), Items.GLOWSTONE_DUST, BGPotions.STRONG_BLAST_ENDURANCE.get());

        addMix(Potions.AWKWARD, BGItems.ORANGE_BALL_CACTUS.get(), BGPotions.PRICKLING.get());
        addMix(Potions.AWKWARD, BGItems.PINK_BALL_CACTUS.get(), BGPotions.PRICKLING.get());
        addMix(Potions.AWKWARD, BGItems.RED_BALL_CACTUS.get(), BGPotions.PRICKLING.get());
        addMix(Potions.AWKWARD, BGItems.YELLOW_BALL_CACTUS.get(), BGPotions.PRICKLING.get());
        addMix(BGPotions.PRICKLING.get(), Items.REDSTONE, BGPotions.LONG_PRICKLING.get());
        addMix(BGPotions.PRICKLING.get(), Items.GLOWSTONE_DUST, BGPotions.STRONG_PRICKLING.get());
    }

    public static void addMix(Potion potion, Item ingredient, Potion result) {
        PotionBrewing.addMix(potion, ingredient, result);
    }
}
