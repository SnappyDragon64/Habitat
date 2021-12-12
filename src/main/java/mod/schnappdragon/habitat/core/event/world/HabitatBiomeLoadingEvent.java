package mod.schnappdragon.habitat.core.event.world;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatPlacedFeatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
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

            // Grove
            // 1.18 Biomes have not been tagged in the Biome Dictionary
            if (biome.check(Biomes.GROVE))
                biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 12, 4, 4);

            if (biome.check(BiomeDictionary.Type.OVERWORLD)) {
                // All Biomes
                biome.addFeature(HabitatPlacedFeatures.PATCH_SLIME_FERN, GenerationStep.Decoration.UNDERGROUND_DECORATION);

                // Deserts and Badlands
                if (biome.check(Biomes.DESERT, Biomes.BADLANDS, Biomes.ERODED_BADLANDS))
                    biome.addFeature(HabitatPlacedFeatures.PATCH_BALL_CACTUS, GenerationStep.Decoration.VEGETAL_DECORATION);

                // Forests
                if (biome.check(Biome.BiomeCategory.FOREST)) {
                    // Flower Forests
                    if (biome.check(Biomes.FLOWER_FOREST))
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 24, 4, 4);
                    // Other Non-Spooky and Non-Dead Forests
                    else if (!biome.check(BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.DEAD))
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 12, 4, 4);
                }

                // Jungles
                if (biome.check(Biome.BiomeCategory.JUNGLE)) {
                    // Jungle
                    if (biome.check(Biomes.JUNGLE)) {
                        biome.addFeature(HabitatPlacedFeatures.PATCH_RAFFLESIA, GenerationStep.Decoration.VEGETAL_DECORATION);
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 24, 4, 4);
                    }
                    // Bamboo Jungle
                    else if (biome.check(Biomes.BAMBOO_JUNGLE))
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 24, 4, 4);
                        // Sparse Jungle
                    else if (biome.check(Biomes.SPARSE_JUNGLE)) {
                        biome.addFeature(HabitatPlacedFeatures.PATCH_RAFFLESIA_SPARSE, GenerationStep.Decoration.VEGETAL_DECORATION);
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 12, 4, 4);
                    }
                    // Non-Vanilla Jungles
                    else
                        biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 12, 4, 4);
                }

                // Plains
                if (biome.check(Biome.BiomeCategory.PLAINS))
                    biome.addFeature(HabitatPlacedFeatures.PATCH_KABLOOM_BUSH, GenerationStep.Decoration.VEGETAL_DECORATION);

                // Savannas
                if (biome.check(Biome.BiomeCategory.SAVANNA))
                    biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 4, 4, 4);

                // Taigas
                if (biome.check(Biome.BiomeCategory.TAIGA))
                    biome.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 12, 4, 4);
            }
        }
    }

    private static class BiomeHelper {
        private final BiomeLoadingEvent event;
        private final BiomeGenerationSettingsBuilder generation;
        private final MobSpawnSettingsBuilder spawns;
        private final ResourceKey<Biome> biome;

        @SuppressWarnings("ConstantConditions")
        private BiomeHelper(BiomeLoadingEvent event) {
            this.event = event;
            this.generation = event.getGeneration();
            this.spawns = event.getSpawns();
            this.biome = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        }

        private boolean check(Biome.BiomeCategory... categories) {
            for (Biome.BiomeCategory category : categories) {
                if (this.event.getCategory() == category)
                    return true;
            }

            return false;
        }

        private boolean check(ResourceKey<?>... biomes) {
            for (ResourceKey<?> biome : biomes) {
                if (biome.location().equals(this.event.getName()))
                    return true;
            }

            return false;
        }

        private boolean check(BiomeDictionary.Type... types) {
            for (BiomeDictionary.Type type : types) {
                if (BiomeDictionary.hasType(this.biome, type))
                    return true;
            }

            return false;
        }

        private void addFeature(PlacedFeature feature, GenerationStep.Decoration stage) {
            this.generation.addFeature(stage, feature);
        }

        private void addCreatureSpawn(EntityType<?> type, int weight, int min, int max) {
            this.spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(type, weight, min, max));
        }
    }
}