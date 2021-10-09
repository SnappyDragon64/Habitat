package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

public class HabitatConfiguredStructures {
    public static final StructureFeature<?, ?> FAIRY_RING = HabitatStructures.FAIRY_RING_STRUCTURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        register("fairy_ring", FAIRY_RING, HabitatStructures.FAIRY_RING_STRUCTURE.get());
    }

    private static void register(String id, StructureFeature<?, ?> structureFeature, Structure<?> structure) {
        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Habitat.MOD_ID, id), structureFeature);
        FlatGenerationSettings.STRUCTURES.put(structure, structureFeature);
    }
}