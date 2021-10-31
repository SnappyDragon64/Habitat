package mod.schnappdragon.habitat.client.renderer.blockentity;

import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class HabitatBlockEntityRenderers {
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(HabitatBlockEntityTypes.SIGN.get(), SignRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HabitatBlockEntityTypes.CHEST.get(), HabitatChestBlockEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HabitatBlockEntityTypes.TRAPPED_CHEST.get(), HabitatChestBlockEntityRenderer::new);
    }
}