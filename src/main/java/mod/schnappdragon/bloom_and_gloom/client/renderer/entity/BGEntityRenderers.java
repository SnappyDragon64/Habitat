package mod.schnappdragon.bloom_and_gloom.client.renderer.entity;

import mod.schnappdragon.bloom_and_gloom.core.registry.BGEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class BGEntityRenderers {

    public static void registerRenderers(Supplier<Minecraft> minecraft) {
        RenderingRegistry.registerEntityRenderingHandler(BGEntityTypes.KABLOOM_FRUIT.get(),
                rendererManager -> new SpriteRenderer<>(rendererManager, minecraft.get().getItemRenderer()));
    }
}