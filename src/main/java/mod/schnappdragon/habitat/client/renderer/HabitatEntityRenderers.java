package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.client.renderer.block.HabitatChestBlockEntityRenderer;
import mod.schnappdragon.habitat.client.renderer.entity.HabitatBoatRenderer;
import mod.schnappdragon.habitat.client.renderer.entity.PookaRenderer;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatBlockEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatEntityRenderers {
    @SubscribeEvent
    public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HabitatEntityTypes.KABLOOM_FRUIT.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.BOAT.get(), HabitatBoatRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.POOKA.get(), PookaRenderer::new);

        event.registerBlockEntityRenderer(HabitatBlockEntityTypes.CHEST.get(), HabitatChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(HabitatBlockEntityTypes.TRAPPED_CHEST.get(), HabitatChestBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(HabitatBlockEntityTypes.SIGN.get(), SignRenderer::new);
    }
}