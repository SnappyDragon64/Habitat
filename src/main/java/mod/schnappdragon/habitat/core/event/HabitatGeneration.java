package mod.schnappdragon.habitat.core.event;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class HabitatGeneration {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            RegistryKey<Biome> biomeRegistryKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
            FeatureHelper helper = new FeatureHelper(event);

            if (BiomeDictionary.hasType(biomeRegistryKey, BiomeDictionary.Type.OVERWORLD)) {
                helper.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStage.Decoration.UNDERGROUND_DECORATION);

                // Jungles excluding Bamboo Jungles
                if (event.getCategory() == Biome.Category.JUNGLE && !event.getName().getPath().contains("bamboo"))
                    helper.addVegetalDecorationFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA);

                // Plains
                if (event.getCategory() == Biome.Category.PLAINS) {
                    helper.addVegetalDecorationFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH);
                    helper.addVegetalDecorationFeature(HabitatConfiguredFeatures.FAIRY_RING);
                }

                // Dark Forests
                if (event.getName().getPath().contains("dark_forest")) {
                    helper.addVegetalDecorationFeature(HabitatConfiguredFeatures.FAIRY_RING);
                    helper.addVegetalDecorationFeature(HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM);
                }

                // Desert and Badlands
                if (event.getCategory() == Biome.Category.DESERT || event.getCategory() == Biome.Category.MESA)
                    helper.addVegetalDecorationFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS);
            }
        }
    }

    private static class FeatureHelper {
        private static BiomeLoadingEvent event;

        private FeatureHelper(BiomeLoadingEvent event) {
            FeatureHelper.event = event;
        }

        private void addFeature(ConfiguredFeature<?, ?> feature, GenerationStage.Decoration stage) {
            event.getGeneration().withFeature(stage, feature);
        }

        private void addVegetalDecorationFeature(ConfiguredFeature<?, ?> feature) {
            event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, feature);
        }
    }
}