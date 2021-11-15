package mod.schnappdragon.habitat.core.event.world;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatStructures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Habitat.MODID)
public class HabitatStructureDimensionalSpacing {
    private static Method GETCODEC_METHOD;

    @SubscribeEvent
    @SuppressWarnings("unchecked")
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) event.getWorld();

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");

                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged"))
                    return;
            } catch (Exception e) {
                Habitat.getLOGGER().error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            if (serverWorld.getChunkSource().generator instanceof FlatLevelSource && serverWorld.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            serverWorld.getChunkSource().generator.getSettings().structureConfig = addToMap(new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig));
        }
    }

    private Map<StructureFeature<?>, StructureFeatureConfiguration> addToMap(Map<StructureFeature<?>, StructureFeatureConfiguration> map) {
        put(map, HabitatStructures.FAIRY_RING_STRUCTURE.get());

        return map;
    }

    private void put(Map<StructureFeature<?>, StructureFeatureConfiguration> map, StructureFeature<?> structure) {
        map.putIfAbsent(structure, StructureSettings.DEFAULTS.get(structure));
    }
}