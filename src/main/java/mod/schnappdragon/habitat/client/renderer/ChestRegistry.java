package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.common.block.misc.ChestVariants;
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
            for (ChestVariants.ChestVariant variant : ChestVariants.getVariants()) {
                event.addSprite(variant.getSingleResource());
                event.addSprite(variant.getRightResource());
                event.addSprite(variant.getLeftResource());
            }
        }
    }
}