package mod.schnappdragon.habitat.core.registry;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.world.gen.feature.template.FairyRingRandomizeProcessor;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.StructureProcessor;

public class HabitatStructureProcessors {
    public static IStructureProcessorType<FairyRingRandomizeProcessor> FAIRY_RING_RANDOMIZE;

    public static void registerStructureProcessors() {
        FAIRY_RING_RANDOMIZE = register("fairy_ring_randomize", FairyRingRandomizeProcessor.CODEC);
    }

    private static <P extends StructureProcessor> IStructureProcessorType<P> register(String id, Codec<P> codec) {
        return Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(Habitat.MOD_ID, id), () -> codec);
    }
}