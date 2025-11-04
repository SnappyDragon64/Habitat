package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.client.renderer.entity.PasserineRenderer;
import mod.schnappdragon.habitat.client.renderer.entity.PookaRenderer;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatRenderers {
    @SubscribeEvent
    public static void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HabitatEntityTypes.KABLOOM_FRUIT.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.POOKA.get(), PookaRenderer::new);
        event.registerEntityRenderer(HabitatEntityTypes.PASSERINE.get(), PasserineRenderer::new);
    }
}