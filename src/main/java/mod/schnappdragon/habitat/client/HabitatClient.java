package mod.schnappdragon.habitat.client;

import mod.schnappdragon.habitat.core.registry.HabitatItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class HabitatClient {
    public static void setup() {
        ItemProperties.register(HabitatItems.BLOWBALL.get(), new ResourceLocation("blowing"),
                (stack, world, livingEntity, integer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}
