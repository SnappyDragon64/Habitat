package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.loot.conditions.IsModLoaded;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class HabitatLootConditionTypes {
    public static LootConditionType IS_MOD_LOADED;

    public static void registerLootConditionTypes() {
        IS_MOD_LOADED = register("is_mod_loaded", new IsModLoaded.Serializer());
    }

    private static LootConditionType register(final String name, final ILootSerializer<? extends ILootCondition> serializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(Habitat.MODID, name), new LootConditionType(serializer));
    }
}
