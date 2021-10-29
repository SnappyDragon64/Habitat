package mod.schnappdragon.habitat.core.api.conditions;

import com.google.gson.JsonObject;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.util.CompatHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public final class QuarkFlagRecipeCondition implements ICondition {
    private final ResourceLocation location;
    private final String flag;

    public QuarkFlagRecipeCondition(ResourceLocation location, String flag) {
        this.location = location;
        this.flag = flag;
    }

    @Override
    public ResourceLocation getID() {
        return this.location;
    }

    @Override
    public boolean test() {
        return CompatHelper.checkQuarkFlag(this.flag);
    }

    public static class Serializer implements IConditionSerializer<QuarkFlagRecipeCondition> {
        private final ResourceLocation location;

        public Serializer() {
            this.location = new ResourceLocation(Habitat.MODID, "quark_flag");
        }

        @Override
        public void write(JsonObject json, QuarkFlagRecipeCondition value) {
            json.addProperty("flag", value.flag);
        }

        @Override
        public QuarkFlagRecipeCondition read(JsonObject json) {
            return new QuarkFlagRecipeCondition(this.location, json.getAsJsonPrimitive("flag").getAsString());
        }

        @Override
        public ResourceLocation getID() {
            return this.location;
        }
    }
}
