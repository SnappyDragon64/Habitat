package mod.schnappdragon.habitat.common.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatLootConditionTypes;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraftforge.fml.ModList;

public class IsModLoaded implements ILootCondition {
    private final String modid;

    public IsModLoaded(String modid) {
        this.modid = modid;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return HabitatLootConditionTypes.IS_MOD_LOADED;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return ModList.get().isLoaded(modid)/* || Habitat.DEV*/;
    }

    public static ILootCondition.IBuilder builder(String modid) {
        return () -> new IsModLoaded(modid);
    }

    public static class Serializer implements ILootSerializer<IsModLoaded> {
        @Override
        public void serialize(JsonObject object, IsModLoaded instance, JsonSerializationContext context) {
            object.addProperty("modid", instance.modid);
        }

        @Override
        public IsModLoaded deserialize(JsonObject object, JsonDeserializationContext context) {
            return new IsModLoaded(JSONUtils.getString(object, "modid"));
        }
    }
}
