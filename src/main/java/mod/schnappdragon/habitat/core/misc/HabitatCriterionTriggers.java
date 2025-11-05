package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;

public class HabitatCriterionTriggers {

    public static void registerCriteriaTriggers() {
    }

    private static ResourceLocation get(String id) {
        return new ResourceLocation(Habitat.MODID, id);
    }
}