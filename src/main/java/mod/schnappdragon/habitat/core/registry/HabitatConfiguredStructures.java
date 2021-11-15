package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

public class HabitatConfiguredStructures {
    public static final ConfiguredStructureFeature<?, ?> FAIRY_RING = HabitatStructures.FAIRY_RING_STRUCTURE.get().configured(FeatureConfiguration.NONE);

    public static void registerConfiguredStructures() {
        register("fairy_ring", FAIRY_RING, HabitatStructures.FAIRY_RING_STRUCTURE.get());
    }

    private static void register(String id, ConfiguredStructureFeature<?, ?> structureFeature, StructureFeature<?> structure) {
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Habitat.MODID, id), structureFeature);
        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(structure, structureFeature);
    }
}