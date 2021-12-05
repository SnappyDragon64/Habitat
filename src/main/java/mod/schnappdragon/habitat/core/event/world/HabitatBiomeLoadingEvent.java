package mod.schnappdragon.habitat.core.event.world;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredStructures;
import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
            ResourceKey<Biome> biome = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OVERWORLD)) {
                ModificationHelper helper = new ModificationHelper(event);

                helper.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStep.Decoration.UNDERGROUND_DECORATION);

                if (helper.isVanilla()) {

                }



                //OLD
                if (helper.checkCategory(Biome.BiomeCategory.JUNGLE) && !helper.checkKeys(Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS))
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA, GenerationStep.Decoration.VEGETAL_DECORATION);

                if (helper.checkCategory(Biome.BiomeCategory.PLAINS))
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH, GenerationStep.Decoration.VEGETAL_DECORATION);

                if (helper.checkCategory(Biome.BiomeCategory.DESERT) || helper.checkCategory(Biome.BiomeCategory.MESA))
                    helper.addFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS, GenerationStep.Decoration.VEGETAL_DECORATION);

                if (helper.checkKeys(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS))
                    helper.addStructure(HabitatConfiguredStructures.FAIRY_RING);

                if (helper.checkKeys(Biomes.FLOWER_FOREST, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE))
                    helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 20, 3, 4);
                else if (helper.checkCategories(Biome.BiomeCategory.FOREST, Biome.BiomeCategory.TAIGA, Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.JUNGLE))
                    helper.addCreatureSpawn(HabitatEntityTypes.PASSERINE.get(), 5, 3, 4);
            }
        }
    }

    private static class ModificationHelper {
        private static BiomeLoadingEvent event;
        private static BiomeGenerationSettingsBuilder generation;
        private static MobSpawnInfoBuilder spawns;

        private ModificationHelper(BiomeLoadingEvent event) {
            ModificationHelper.event = event;
            generation = event.getGeneration();
            spawns = event.getSpawns();
        }

        private boolean isVanilla() {
            return ResourceLocation.DEFAULT_NAMESPACE.equals(event.getName().getNamespace());
        }

        private boolean checkCategory(Biome.BiomeCategory category) {
            return event.getCategory() == category;
        }

        private boolean checkCategories(Biome.BiomeCategory... categories) {
            for (Biome.BiomeCategory category : categories) {
                if (this.checkCategory(category)) {
                    return true;
                }
            }

            return false;
        }

        private boolean checkKeys(ResourceKey<?>... biomes) {
            for (ResourceKey<?> biome : biomes) {
                if (biome.location().equals(event.getName()))
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