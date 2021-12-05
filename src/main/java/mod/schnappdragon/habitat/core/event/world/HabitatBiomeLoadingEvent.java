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
            BiomeHelper biome = new BiomeHelper(event);

            if (biome.checkType(BiomeDictionary.Type.OVERWORLD)) {
                // All Biomes
                biome.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStep.Decoration.UNDERGROUND_DECORATION);

                // Deserts and Badlands
                if (biome.checkKey(Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_BADLANDS_PLATEAU, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.WOODED_BADLANDS_PLATEAU))
                    biome.addFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS, GenerationStep.Decoration.VEGETAL_DECORATION);

                // Forest Biomes
                if (biome.checkCategory(Biome.BiomeCategory.FOREST)) {
                    // Dark Forests
                    if (biome.checkKey(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS))
                        biome.addStructure(HabitatConfiguredStructures.FAIRY_RING);
                    // Flower Forests
                    else if (biome.checkKey(Biomes.FLOWER_FOREST))
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 20, 3, 4);
                    // Other Non-Spooky and Non-Dead Forests
                    else if (!biome.checkType(BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.DEAD))
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 6, 3, 4);
                }

                // Vanilla Jungle Biomes
                if (biome.checkKey(Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE)) {
                    // Excluding Bamboo Jungles
                    if (!biome.checkKey(Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS))
                        biome.addFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA, GenerationStep.Decoration.VEGETAL_DECORATION);

                    biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 20, 3, 4);
                }
                // Modded Jungle Biomes
                else if (biome.checkCategory(Biome.BiomeCategory.JUNGLE))
                    biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 3, 3, 4);

                // Plains
                if (biome.checkCategory(Biome.BiomeCategory.PLAINS))
                    biome.addFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH, GenerationStep.Decoration.VEGETAL_DECORATION);

                // Savannas
                if (biome.checkCategory(Biome.BiomeCategory.SAVANNA))
                    biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 3, 3, 4);

                // Taigas
                if (biome.checkCategory(Biome.BiomeCategory.TAIGA))
                    biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 6, 3, 4);
            }
        }
    }

    private static class BiomeHelper {
        private final BiomeLoadingEvent event;
        private final BiomeGenerationSettingsBuilder generation;
        private final MobSpawnInfoBuilder spawns;
        private final ResourceKey<Biome> biome;

        private BiomeHelper(BiomeLoadingEvent event) {
            this.event = event;
            this.generation = event.getGeneration();
            this.spawns = event.getSpawns();
            this.biome = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        }

        private boolean checkCategory(Biome.BiomeCategory... categories) {
            for (Biome.BiomeCategory category : categories) {
                if (this.event.getCategory() == category)
                    return true;
            }

            return false;
        }

        private boolean checkKey(ResourceKey<?>... biomes) {
            for (ResourceKey<?> biome : biomes) {
                if (biome.location().equals(this.event.getName()))
                    return true;
            }

            return false;
        }

        private boolean checkType(BiomeDictionary.Type... types) {
            for (BiomeDictionary.Type type : types) {
                if (BiomeDictionary.hasType(this.biome, type))
                    return true;
            }

            return false;
        }

        private void addFeature(ConfiguredFeature<?, ?> feature, GenerationStep.Decoration stage) {
            this.generation.addFeature(stage, feature);
        }

        private void addStructure(ConfiguredStructureFeature<?, ?> structure) {
            this.generation.getStructures().add(() -> structure);
        }

        private void addCreatureSpawn(EntityType<?> type, int weight, int min, int max) {
            this.spawns.getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(type, weight, min, max));
        }
    }
}