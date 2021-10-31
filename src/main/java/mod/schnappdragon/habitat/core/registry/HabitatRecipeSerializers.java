package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.item.crafting.FairyRingMushroomSuspiciousStewRecipe;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Habitat.MODID);

    public static final RegistryObject<RecipeSerializer<FairyRingMushroomSuspiciousStewRecipe>> CRAFTING_SPECIAL_FAIRYRINGMUSHROOMSUSPICIOUSSTEW = RECIPE_SERIALIZERS.register("crafting_special_fairyringmushroomsuspiciousstew", () -> new SimpleRecipeSerializer<>(FairyRingMushroomSuspiciousStewRecipe::new));
}
