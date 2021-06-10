package mod.schnappdragon.habitat.core.event.world;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Habitat.MOD_ID)
public class HabitatStructureDimensionalSpacing {
    private static Method GETCODEC_METHOD;

    @SubscribeEvent
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                if (GETCODEC_METHOD == null)
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");

                ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
                if (cgRL != null && cgRL.getNamespace().equals("terraforged"))
                    return;
            } catch (Exception e) {
                Habitat.getLOGGER().error("Was unable to check if " + serverWorld.getDimensionKey().getLocation() + " is using Terraforged's ChunkGenerator.");
            }

            if (serverWorld.getChunkProvider().generator instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
                return;
            }

            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = addToMap(new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_));
        }
    }

    private Map<Structure<?>, StructureSeparationSettings> addToMap(Map<Structure<?>, StructureSeparationSettings> map) {
        put(map, HabitatStructures.FAIRY_RING_STRUCTURE.get());

        return map;
    }

    private void put(Map<Structure<?>, StructureSeparationSettings> map, Structure<?> structure) {
        map.putIfAbsent(structure, DimensionStructuresSettings.field_236191_b_.get(structure));
    }
}