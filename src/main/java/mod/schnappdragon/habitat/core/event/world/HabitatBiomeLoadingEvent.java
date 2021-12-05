package mod.schnappdragon.habitat.core.event.world;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredStructures;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatBiomeLoadingEvent {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            ModificationHelper helper = new ModificationHelper(event);

            if (helper.checkType(BiomeDictionary.Type.OVERWORLD)) {
                // All Biomes
                helper.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStep.Decoration.UNDERGROUND_DECORATION);

                // Deserts and Badlands
                if (helper.checkKey(Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU))
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS, GenerationStep.Decoration.VEGETAL_DECORATION);

                // Forest Biomes
                if (helper.checkCategory(Biome.BiomeCategory.FOREST)) {
                    // Dark Forests
                    if (helper.checkKey(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS))
                        helper.addStructure(HabitatConfiguredStructures.FAIRY_RING);
                    // Flower Forests
                    else if (helper.checkKey(Biomes.FLOWER_FOREST))
                        helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 20, 3, 4);
                    // Other Non-Spooky and Non-Dead Forests
                    else if (!helper.checkType(BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.DEAD))
                        helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 6, 3, 4);
                }

                // Vanilla Jungle Biomes
                if (helper.checkKey(Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE)) {
                    // Excluding Bamboo Jungles
                    if (!helper.checkKey(Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS))
                        helper.addFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA, GenerationStep.Decoration.VEGETAL_DECORATION);

                    helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 20, 3, 4);
                }
                // Modded Jungle Biomes
                else if (helper.checkCategory(Biome.BiomeCategory.JUNGLE))
                    helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 3, 3, 4);

                // Plains
                if (helper.checkCategory(Biome.BiomeCategory.PLAINS))
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH, GenerationStep.Decoration.VEGETAL_DECORATION);

                // Savannas
                if (helper.checkCategory(Biome.BiomeCategory.SAVANNA))
                    helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 3, 3, 4);

                // Taigas
                if (helper.checkCategory(Biome.BiomeCategory.TAIGA))
                    helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 6, 3, 4);
            }
        }
    }

    private static class ModificationHelper {
        private static BiomeLoadingEvent event;
        private static BiomeGenerationSettingsBuilder generation;
        private static MobSpawnInfoBuilder spawns;
        ResourceKey<Biome> biome;

        private ModificationHelper(BiomeLoadingEvent event) {
            ModificationHelper.event = event;
            generation = event.getGeneration();
            spawns = event.getSpawns();
            biome = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        }

        private boolean checkCategory(Biome.BiomeCategory... categories) {
            for (Biome.BiomeCategory category : categories) {
                if (event.getCategory() == category)
                    return true;
            }

            return false;
        }

        private boolean checkKey(ResourceKey<?>... biomes) {
            for (ResourceKey<?> biome : biomes) {
                if (biome.location().equals(event.getName()))
                    return true;
            }

            return false;
        }

        private boolean checkType(BiomeDictionary.Type... types) {
            for (BiomeDictionary.Type type : types) {
                if (BiomeDictionary.hasType(biome, type))
                    return true;
            }

            return false;
        }

        private void addFeature(ConfiguredFeature<?, ?> feature, GenerationStep.Decoration stage) {
            generation.addFeature(stage, feature);
        }

        private void addStructure(ConfiguredStructureFeature<?, ?> structure) {
            generation.getStructures().add(() -> structure);
        }

        private void addCreatureSpawn(EntityType<?> type, int weight, int min, int max) {
            spawns.getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(type, weight, min, max));
        }
    }
}