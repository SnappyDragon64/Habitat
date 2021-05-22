package mod.schnappdragon.habitat.client.renderer.tileentity;

import mod.schnappdragon.habitat.core.registry.HabitatTileEntityTypes;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class HabitatTileEntityRenderers {
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(HabitatTileEntityTypes.SIGN.get(), SignTileEntityRenderer::new);
    }
}