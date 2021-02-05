package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGGeneration {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeRegistryKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());

        if (BiomeDictionary.hasType(biomeRegistryKey, BiomeDictionary.Type.OVERWORLD)) {
            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, BGConfiguredFeatures.PATCH_SLIME_FERN);
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BGConfiguredFeatures.FAIRY_RING);

            if (event.getCategory() == Biome.Category.JUNGLE && !event.getName().getPath().contains("bamboo"))
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BGConfiguredFeatures.PATCH_RAFFLESIA);

            if (event.getCategory() == Biome.Category.PLAINS)
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BGConfiguredFeatures.PATCH_KABLOOM_BUSH);
        }
    }
}