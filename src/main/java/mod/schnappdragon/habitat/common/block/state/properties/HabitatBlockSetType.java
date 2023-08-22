package mod.schnappdragon.habitat.common.block.state.properties;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class HabitatBlockSetType {
    public static final BlockSetType FAIRY_RING_MUSHROOM = register("fairy_ring_mushroom");

    private static BlockSetType register(String id) {
        ResourceLocation resourceLocation = new ResourceLocation(Habitat.MODID, id);
        BlockSetType blockSetType = new BlockSetType(resourceLocation.toString());
        return BlockSetType.register(blockSetType);
    }
}
