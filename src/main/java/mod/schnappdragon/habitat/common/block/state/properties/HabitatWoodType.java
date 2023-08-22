package mod.schnappdragon.habitat.common.block.state.properties;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class HabitatWoodType {
    public static final WoodType FAIRY_RING_MUSHROOM = register("fairy_ring_mushroom", HabitatBlockSetType.FAIRY_RING_MUSHROOM);

    private static WoodType register(String id, BlockSetType blockSetType) {
        ResourceLocation resourceLocation = new ResourceLocation(Habitat.MODID, id);
        WoodType woodType = new WoodType(resourceLocation.toString(), blockSetType);
        return WoodType.register(woodType);
    }
}
