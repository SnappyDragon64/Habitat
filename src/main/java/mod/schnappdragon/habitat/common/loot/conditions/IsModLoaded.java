package mod.schnappdragon.habitat.common.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatLootConditionTypes;
import mod.schnappdragon.habitat.core.util.CompatHelper;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fml.ModList;

public class IsModLoaded implements LootItemCondition {
    private final String modid;

    public IsModLoaded(String modid) {
        this.modid = modid;
    }

    @Override
    public LootItemConditionType getType() {
        return HabitatLootConditionTypes.IS_MOD_LOADED;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return CompatHelper.checkMods(modid);
    }

    public static LootItemCondition.Builder builder(String modid) {
        return () -> new IsModLoaded(modid);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<IsModLoaded> {
        @Override
        public void serialize(JsonObject object, IsModLoaded instance, JsonSerializationContext context) {
            object.addProperty("modid", instance.modid);
        }

        @Override
        public IsModLoaded deserialize(JsonObject object, JsonDeserializationContext context) {
            return new IsModLoaded(GsonHelper.getAsString(object, "modid"));
        }
    }
}
