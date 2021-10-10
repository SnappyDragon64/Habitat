package mod.schnappdragon.habitat.core.event.world;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.HabitatConfig;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
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

import java.util.ArrayList;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class HabitatBiomeLoadingEvent {
    private static final ArrayList<String> rafflesiaWhitelist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.rafflesiaWhitelist.get().split(",")));
    private static final ArrayList<String> kabloomBushWhitelist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.kabloomBushWhitelist.get().split(",")));
    private static final ArrayList<String> slimeFernWhitelist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.slimeFernWhitelist.get().split(",")));
    private static final ArrayList<String> ballCactusWhitelist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.ballCactusWhitelist.get().split(",")));
    private static final ArrayList<String> fairyRingWhitelist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.fairyRingWhitelist.get().split(",")));

    private static final ArrayList<String> rafflesiaBlacklist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.rafflesiaBlacklist.get().split(",")));
    private static final ArrayList<String> kabloomBushBlacklist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.kabloomBushBlacklist.get().split(",")));
    private static final ArrayList<String> slimeFernBlacklist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.slimeFernBlacklist.get().split(",")));
    private static final ArrayList<String> ballCactusBlacklist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.ballCactusBlacklist.get().split(",")));
    private static final ArrayList<String> fairyRingBlacklist = new ArrayList<>(Arrays.asList(HabitatConfig.COMMON.fairyRingBlacklist.get().split(",")));

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            String biome = event.getName().toString();
            ModificationHelper helper = new ModificationHelper(event);

            if (helper.checkOverworldCategory(Biome.Category.JUNGLE) && !rafflesiaBlacklist.contains(biome) || rafflesiaWhitelist.contains(biome))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA, GenerationStage.Decoration.VEGETAL_DECORATION);

            if (helper.checkOverworldCategory(Biome.Category.PLAINS) && !kabloomBushBlacklist.contains(biome) || kabloomBushWhitelist.contains(biome))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH, GenerationStage.Decoration.VEGETAL_DECORATION);

            if (helper.checkType(BiomeDictionary.Type.OVERWORLD) && !slimeFernBlacklist.contains(biome) || slimeFernWhitelist.contains(biome))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStage.Decoration.UNDERGROUND_DECORATION);

            if ((helper.checkOverworldCategory(Biome.Category.DESERT) || helper.checkOverworldCategory(Biome.Category.MESA)) && !ballCactusBlacklist.contains(biome) || ballCactusWhitelist.contains(biome))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS, GenerationStage.Decoration.VEGETAL_DECORATION);

            if (helper.checkOverworldCategory(Biome.Category.FOREST) && !fairyRingBlacklist.contains(biome) || fairyRingWhitelist.contains(biome))
                helper.addStructure(HabitatConfiguredStructures.FAIRY_RING);
        }
    }

    private static class ModificationHelper {
        private static BiomeLoadingEvent event;

        private ModificationHelper(BiomeLoadingEvent event) {
            ModificationHelper.event = event;
        }

        private boolean checkOverworldCategory(Biome.Category category) {
            return checkType(BiomeDictionary.Type.OVERWORLD) && event.getCategory() == category;
        }

        private boolean checkType(BiomeDictionary.Type type) {
            return BiomeDictionary.hasType(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName()), type);
        }

        private void addFeature(ConfiguredFeature<?, ?> feature, GenerationStage.Decoration stage) {
            event.getGeneration().withFeature(stage, feature);
        }

        private void addStructure(StructureFeature<?, ?> structure) {
            event.getGeneration().getStructures().add(() -> structure);
        }
    }
}