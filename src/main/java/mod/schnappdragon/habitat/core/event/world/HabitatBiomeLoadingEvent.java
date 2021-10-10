package mod.schnappdragon.habitat.core.event.world;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.HabitatConfig;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class HabitatBiomeLoadingEvent {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void modifyBiomes(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            ModificationHelper helper = new ModificationHelper(event);

            if (helper.check(helper.checkOverworldCategory(Biome.Category.JUNGLE), HabitatConfig.COMMON.rafflesiaWhitelist, HabitatConfig.COMMON.rafflesiaBlacklist))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_RAFFLESIA, GenerationStage.Decoration.VEGETAL_DECORATION);

            if (helper.check(helper.checkOverworldCategory(Biome.Category.PLAINS), HabitatConfig.COMMON.kabloomBushWhitelist, HabitatConfig.COMMON.kabloomBushBlacklist))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_KABLOOM_BUSH, GenerationStage.Decoration.VEGETAL_DECORATION);

            if (helper.check(helper.checkType(BiomeDictionary.Type.OVERWORLD), HabitatConfig.COMMON.slimeFernWhitelist, HabitatConfig.COMMON.slimeFernBlacklist))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_SLIME_FERN, GenerationStage.Decoration.UNDERGROUND_DECORATION);

            if (helper.check(helper.checkOverworldCategory(Biome.Category.DESERT) || helper.checkOverworldCategory(Biome.Category.MESA), HabitatConfig.COMMON.ballCactusWhitelist, HabitatConfig.COMMON.ballCactusBlacklist))
                helper.addFeature(HabitatConfiguredFeatures.PATCH_BALL_CACTUS, GenerationStage.Decoration.VEGETAL_DECORATION);

            if (helper.check(helper.checkOverworldCategory(Biome.Category.FOREST), HabitatConfig.COMMON.fairyRingWhitelist, HabitatConfig.COMMON.fairyRingBlacklist))
                helper.addStructure(HabitatConfiguredStructures.FAIRY_RING);
        }
    }

    private static class ModificationHelper {
        private static BiomeLoadingEvent event;

        private ModificationHelper(BiomeLoadingEvent event) {
            ModificationHelper.event = event;
        }

        private boolean check(boolean condition, ForgeConfigSpec.ConfigValue<String> whitelistConfig, ForgeConfigSpec.ConfigValue<String> blacklistConfig) {
            String biome = event.getName().toString();
            List<String> whitelist = Arrays.asList(whitelistConfig.get().split(","));
            List<String> blacklist = Arrays.asList(blacklistConfig.get().split(","));
            return condition && !blacklist.contains(biome) || whitelist.contains(biome);
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