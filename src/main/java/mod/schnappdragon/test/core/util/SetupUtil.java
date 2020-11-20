package mod.schnappdragon.test.core.util;

import mod.schnappdragon.test.core.registry.ModBlocks;
import mod.schnappdragon.test.core.registry.ModItems;
import net.minecraft.block.ComposterBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class SetupUtil {

    /*
     * Common Setup Helper
     */

    public static void registerComposterChances() {
        ComposterBlock.CHANCES.put(ModItems.RAFFLESIA_SEED_POD.get(), 0.3F);
    }

    /*
     * Client Setup Helper
     */

    public static void registerRenderLayers() {
        RenderTypeLookup.setRenderLayer(ModBlocks.RAFFLESIA_BLOCK.get(), RenderType.getCutout());
    }
}
