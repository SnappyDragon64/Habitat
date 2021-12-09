package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class HabitatConfiguredStructures {
    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_FAIRY_RING = HabitatStructures.FAIRY_RING.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        register("fairy_ring", CONFIGURED_FAIRY_RING);
    }

    private static void register(String id, ConfiguredStructureFeature<?, ?> structure) {
        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Habitat.MODID, id), structure);
    }
}