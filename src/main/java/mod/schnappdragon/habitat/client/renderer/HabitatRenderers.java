package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.client.renderer.block.HabitatChestRenderer;
import mod.schnappdragon.habitat.client.renderer.entity.HabitatBoatRenderer;
import mod.schnappdragon.habitat.client.renderer.entity.PasserineRenderer;
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
public class HabitatRenderers {
    @SubscribeEvent
    public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(HabitatBlockEntityTypes.CHEST.get(), HabitatChestRenderer::new);
        event.registerBlockEntityRenderer(HabitatBlockEntityTypes.TRAPPED_CHEST.get(), HabitatChestRenderer::new);
        //event.registerBlockEntityRenderer(HabitatBlockEntityTypes.SIGN.get(), SignRenderer::new);

        event.registerEntityRenderer(HabitatEntityTypes.KABLOOM_FRUIT.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.BOAT.get(), HabitatBoatRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.POOKA.get(), PookaRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.PASSERINE.get(), PasserineRenderer::new);
    }
}