package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.advancement.HabitatCriterionTrigger;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;

public class HabitatCriterionTriggers {
    public static final HabitatCriterionTrigger PACIFY_POOKA = new HabitatCriterionTrigger(get("pacify_pooka"));

    public static void registerCriteriaTriggers() {
        CriteriaTriggers.register(PACIFY_POOKA);
    }

    private static ResourceLocation get(String id) {
        return new ResourceLocation(Habitat.MOD_ID, id);
    }
}