package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.loot.conditions.IsModLoaded;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

public class HabitatLootConditionTypes {
    public static LootItemConditionType IS_MOD_LOADED;

    public static void registerLootConditionTypes() {
        IS_MOD_LOADED = register("is_mod_loaded", new IsModLoaded.Serializer());
    }

    private static LootItemConditionType register(final String name, final Serializer<? extends LootItemCondition> serializer) {
        return Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(Habitat.MODID, name), new LootItemConditionType(serializer));
    }
}
