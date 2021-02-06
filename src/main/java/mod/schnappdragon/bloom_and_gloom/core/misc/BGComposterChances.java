package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.block.ComposterBlock;

public class BGComposterChances {
    public static void registerComposterChances() {
        ComposterBlock.CHANCES.put(BGItems.RAFFLESIA_SEED.get(), 0.3F);

        ComposterBlock.CHANCES.put(BGItems.KABLOOM_SEEDS.get(), 0.3F);
        ComposterBlock.CHANCES.put(BGItems.KABLOOM_FRUIT.get(), 0.65F);
        ComposterBlock.CHANCES.put(BGItems.CANDIED_KABLOOM_FRUIT.get(), 0.65F);

        ComposterBlock.CHANCES.put(BGItems.SLIME_FERN.get(), 0.65F);

        ComposterBlock.CHANCES.put(BGItems.PINK_BALL_CACTUS_FLOWER.get(), 0.3F);
        ComposterBlock.CHANCES.put(BGItems.RED_BALL_CACTUS_FLOWER.get(), 0.3F);
        ComposterBlock.CHANCES.put(BGItems.ORANGE_BALL_CACTUS_FLOWER.get(), 0.3F);
        ComposterBlock.CHANCES.put(BGItems.YELLOW_BALL_CACTUS_FLOWER.get(), 0.3F);

        ComposterBlock.CHANCES.put(BGItems.PINK_BALL_CACTUS.get(), 0.65F);
        ComposterBlock.CHANCES.put(BGItems.RED_BALL_CACTUS.get(), 0.65F);
        ComposterBlock.CHANCES.put(BGItems.ORANGE_BALL_CACTUS.get(), 0.65F);
        ComposterBlock.CHANCES.put(BGItems.YELLOW_BALL_CACTUS.get(), 0.65F);
    }
}