package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.item.crafting.FairySuspiciousStewRecipe;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGRecipeSerializers {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<FairySuspiciousStewRecipe>> CRAFTING_SPECIAL_FAIRYSUSPICIOUSSTEW = RECIPE_SERIALIZERS.register("crafting_special_fairysuspiciousstew", () -> new SpecialRecipeSerializer<>(FairySuspiciousStewRecipe::new));
}
