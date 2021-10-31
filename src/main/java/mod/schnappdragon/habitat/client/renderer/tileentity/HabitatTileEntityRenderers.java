package mod.schnappdragon.habitat.client.renderer.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class HabitatTileEntityRenderers {
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(HabitatTileEntityTypes.SIGN.get(), SignRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HabitatTileEntityTypes.CHEST.get(), HabitatChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(HabitatTileEntityTypes.TRAPPED_CHEST.get(), HabitatChestTileEntityRenderer::new);
    }
}