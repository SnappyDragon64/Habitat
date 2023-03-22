package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

import java.util.function.Supplier;

public class HabitatComposterChances {
    public static void registerComposterChances() {
        put(HabitatItems.RAFFLESIA, 0.65F);

        put(HabitatItems.KABLOOM_PULP, 0.3F);
        put(HabitatItems.KABLOOM_FRUIT, 0.5F);
        put(HabitatItems.KABLOOM_FRUIT_PILE, 1.0F);
        put(HabitatItems.KABLOOM_PULP_BLOCK, 1.0F);

        put(HabitatItems.SLIME_FERN, 0.65F);

        put(HabitatItems.ORANGE_BALL_CACTUS_FLOWER, 0.3F);
        put(HabitatItems.PINK_BALL_CACTUS_FLOWER, 0.3F);
        put(HabitatItems.RED_BALL_CACTUS_FLOWER, 0.3F);
        put(HabitatItems.YELLOW_BALL_CACTUS_FLOWER, 0.3F);
        put(HabitatItems.ORANGE_BALL_CACTUS, 0.5F);
        put(HabitatItems.PINK_BALL_CACTUS, 0.5F);
        put(HabitatItems.RED_BALL_CACTUS, 0.5F);
        put(HabitatItems.YELLOW_BALL_CACTUS, 0.5F);
        put(HabitatItems.DRIED_BALL_CACTUS, 0.3F);

        put(HabitatItems.FAIRY_RING_MUSHROOM, 0.65F);
        put(HabitatItems.FAIRY_RING_MUSHROOM_BLOCK, 0.65F);
        put(HabitatItems.FAIRY_RING_MUSHROOM_STEM, 0.65F);
        put(HabitatItems.FAIRYLIGHT, 0.65F);

        put(HabitatItems.EDELWEISS, 0.65F);

        put(HabitatItems.ORANGE_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.PINK_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.RED_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.YELLOW_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.FLOWERING_ORANGE_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.FLOWERING_PINK_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.FLOWERING_RED_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.FLOWERING_YELLOW_BALL_CACTUS_BLOCK, 0.5F);
        put(HabitatItems.DRIED_BALL_CACTUS_BLOCK, 0.5F);

        put(HabitatItems.PURPLE_ANTHURIUM, 0.65F);
        put(HabitatItems.RED_ANTHURIUM, 0.65F);
        put(HabitatItems.WHITE_ANTHURIUM, 0.65F);
        put(HabitatItems.YELLOW_ANTHURIUM, 0.65F);
        put(HabitatItems.TALL_PURPLE_ANTHURIUM, 0.65F);
        put(HabitatItems.TALL_RED_ANTHURIUM, 0.65F);
        put(HabitatItems.TALL_WHITE_ANTHURIUM, 0.65F);
        put(HabitatItems.TALL_YELLOW_ANTHURIUM, 0.65F);

        put(HabitatItems.DREADBUD, 0.65F);
    }

    private static void put(Supplier<Item> item, float value) {
        ComposterBlock.COMPOSTABLES.put(item.get(), value);
    }
}