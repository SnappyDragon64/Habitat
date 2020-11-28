package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGItems;
import net.minecraft.block.ComposterBlock;

public class BGComposterChances {

    public static void registerComposterChances() {
        ComposterBlock.CHANCES.put(BGItems.RAFFLESIA_SEED.get(), 0.3F);
    }
}
