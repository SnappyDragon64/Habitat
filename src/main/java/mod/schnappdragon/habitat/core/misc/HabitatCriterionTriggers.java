package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.common.advancement.HabitatCriterionTrigger;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

public class HabitatCriterionTriggers {
    public static final HabitatCriterionTrigger FEED_PASSERINE = new HabitatCriterionTrigger(get("feed_passerine"));

    public static void registerCriteriaTriggers() {
        CriteriaTriggers.register(FEED_PASSERINE);
    }

    private static ResourceLocation get(String id) {
        return new ResourceLocation(Habitat.MODID, id);
    }
}