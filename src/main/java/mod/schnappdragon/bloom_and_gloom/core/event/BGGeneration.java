package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGConfiguredFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGGeneration {
    @SubscribeEvent
    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.JUNGLE)
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BGConfiguredFeatures.PATCH_RAFFLESIA);
        else if (event.getCategory() == Biome.Category.PLAINS)
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BGConfiguredFeatures.PATCH_KABLOOM_BUSH);

        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND && event.getCategory() != Biome.Category.NONE)
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BGConfiguredFeatures.PATCH_SLIME_FERN);
    }
}