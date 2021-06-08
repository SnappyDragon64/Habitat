package mod.schnappdragon.habitat.core.event.world;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class BiomeModification {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            RegistryKey<Biome> biomeRegistryKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
            ModificationHelper helper = new ModificationHelper(event);

            if (BiomeDictionary.hasType(biomeRegistryKey, BiomeDictionary.Type.OVERWORLD)) {
                helper.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStage.Decoration.UNDERGROUND_DECORATION);

                // Jungles excluding Bamboo Jungles
                if (event.getCategory() == Biome.Category.JUNGLE && !event.getName().getPath().contains("bamboo"))
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA, GenerationStage.Decoration.VEGETAL_DECORATION);

                // Plains
                if (event.getCategory() == Biome.Category.PLAINS)
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH, GenerationStage.Decoration.VEGETAL_DECORATION);

                // Dark Forests
                if (event.getName().getPath().contains("dark_forest"))
                    helper.addFeature(HabitatConfiguredFeatures.FAIRY_RING, GenerationStage.Decoration.SURFACE_STRUCTURES);

                // Desert and Badlands
                if (event.getCategory() == Biome.Category.DESERT || event.getCategory() == Biome.Category.MESA)
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS, GenerationStage.Decoration.VEGETAL_DECORATION);
            }
        }
    }

    private static class ModificationHelper {
        private static BiomeLoadingEvent event;

        private ModificationHelper(BiomeLoadingEvent event) {
            ModificationHelper.event = event;
        }

        private void addFeature(ConfiguredFeature<?, ?> feature, GenerationStage.Decoration stage) {
            event.getGeneration().withFeature(stage, feature);
        }

        private void addStructure(StructureFeature<?, ?> structure) {
            event.getGeneration().getStructures().add(() -> structure);
        }
    }
}