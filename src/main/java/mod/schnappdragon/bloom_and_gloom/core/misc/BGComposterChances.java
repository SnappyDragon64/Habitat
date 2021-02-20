package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

public class BGComposterChances {
    public static void registerComposterChances() {
        put(BGItems.RAFFLESIA_SEED.get(), 0.3F);

        put(BGItems.KABLOOM_SEEDS.get(), 0.3F);
        put(BGItems.KABLOOM_FRUIT.get(), 0.65F);
        put(BGItems.CANDIED_KABLOOM_FRUIT.get(), 0.85F);

        put(BGItems.SLIME_FERN.get(), 0.65F);

        put(BGItems.ORANGE_BALL_CACTUS_FLOWER.get(), 0.3F);
        put(BGItems.PINK_BALL_CACTUS_FLOWER.get(), 0.3F);
        put(BGItems.RED_BALL_CACTUS_FLOWER.get(), 0.3F);
        put(BGItems.YELLOW_BALL_CACTUS_FLOWER.get(), 0.3F);
        put(BGItems.ORANGE_BALL_CACTUS.get(), 0.5F);
        put(BGItems.PINK_BALL_CACTUS.get(), 0.5F);
        put(BGItems.RED_BALL_CACTUS.get(), 0.5F);
        put(BGItems.YELLOW_BALL_CACTUS.get(), 0.5F);
        put(BGItems.ROASTED_CACTUS.get(), 0.5F);

        put(BGItems.FAIRY_RING_MUSHROOM.get(), 0.65F);
        put(BGItems.FAIRY_RING_MUSHROOM_BLOCK.get(), 0.65F);
        put(BGItems.FAIRY_RING_MUSHROOM_STEM.get(), 0.65F);
        put(BGItems.FAIRYLIGHT.get(), 0.65F);
    }

    private static void put(IItemProvider item, float value) {
        ComposterBlock.CHANCES.put(item, value);
    }
}