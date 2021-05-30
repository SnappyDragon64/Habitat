package mod.schnappdragon.habitat.core.api.conditions;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class RecipeConditions {
    public static void registerSerializers() {
        register(new QuarkFlagRecipeCondition.Serializer());
    }

    private static void register(IConditionSerializer<?> serializer) {
        CraftingHelper.register(serializer);
    }
}