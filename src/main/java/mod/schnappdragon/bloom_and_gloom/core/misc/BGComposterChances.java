package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.block.ComposterBlock;

public class BGComposterChances {

    public static void registerComposterChances() {
        ComposterBlock.CHANCES.put(BGItems.RAFFLESIA_SEED.get(), 0.3F);

        ComposterBlock.CHANCES.put(BGItems.KABLOOM_SEEDS.get(), 0.3F);
        ComposterBlock.CHANCES.put(BGItems.KABLOOM_SEEDS.get(), 0.65F);

        ComposterBlock.CHANCES.put(BGItems.SLIME_MOSS.get(), 0.85F);
    }
}