package mod.schnappdragon.habitat.core.registry;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.levelgen.placement.SlimeChunkFilter;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class HabitatPlacementModifierTypes {
    public static PlacementModifierType<SlimeChunkFilter> SLIME_CHUNK_FILTER;

    public static void registerPlacementModifierTypes() {
        SLIME_CHUNK_FILTER = register("slime_chunk_filter", SlimeChunkFilter.CODEC);
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String id, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, new ResourceLocation(Habitat.MODID, id), () -> codec);
    }
}