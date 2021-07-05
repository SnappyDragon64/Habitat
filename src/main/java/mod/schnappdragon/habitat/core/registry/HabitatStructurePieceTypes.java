package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.world.gen.feature.structure.FairyRingPiece;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class HabitatStructurePieceTypes {
    public static IStructurePieceType FR;

    public static void registerStructurePieceTypes() {
        FR = register(FairyRingPiece::new, "fr");
    }

    private static IStructurePieceType register(IStructurePieceType type, String id) {
        return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Habitat.MOD_ID, id), type);
    }
}
