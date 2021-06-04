package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.common.block.misc.ChestVariant;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChestRegistry {
    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
            for (ChestVariant variant : ChestVariant.values()) {
                event.addSprite(variant.getSingle());
                event.addSprite(variant.getRight());
                event.addSprite(variant.getLeft());
            }
        }
    }
}