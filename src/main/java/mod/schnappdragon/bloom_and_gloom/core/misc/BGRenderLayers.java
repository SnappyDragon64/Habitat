package mod.schnappdragon.bloom_and_gloom.core.misc;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class BGRenderLayers {

    public static void registerRenderLayers() {
        RenderTypeLookup.setRenderLayer(BGBlocks.RAFFLESIA_BLOCK.get(), RenderType.getCutout());
    }
}
