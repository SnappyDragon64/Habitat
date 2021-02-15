package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.item.crafting.SuspiciousStewWithFairyRingMushroomRecipe;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<SuspiciousStewWithFairyRingMushroomRecipe>> CRAFTING_SPECIAL_SUSPICIOUSSTEWWITHFAIRYRINGMUSHROOM = RECIPE_SERIALIZERS.register("crafting_special_suspiciousstewwithfairyringmushroom", () -> new SpecialRecipeSerializer<>(SuspiciousStewWithFairyRingMushroomRecipe::new));
}
