package mod.schnappdragon.habitat.client.renderer.entity;

import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Supplier;

public class HabitatEntityRenderers {
    public static void registerRenderers(Supplier<Minecraft> minecraft) {
        RenderingRegistry.registerEntityRenderingHandler(HabitatEntityTypes.KABLOOM_FRUIT.get(), rendererManager -> new ThrownItemRenderer<>(rendererManager, minecraft.get().getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(HabitatEntityTypes.BOAT.get(), HabitatBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(HabitatEntityTypes.POOKA.get(), PookaRenderer::new);
    }
}