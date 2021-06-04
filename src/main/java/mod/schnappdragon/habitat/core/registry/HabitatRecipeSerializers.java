package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.item.crafting.FairyRingMushroomSuspiciousStewRecipe;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Habitat.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<FairyRingMushroomSuspiciousStewRecipe>> CRAFTING_SPECIAL_FAIRYRINGMUSHROOMSUSPICIOUSSTEW = RECIPE_SERIALIZERS.register("crafting_special_fairyringmushroomsuspiciousstew", () -> new SpecialRecipeSerializer<>(FairyRingMushroomSuspiciousStewRecipe::new));
}
