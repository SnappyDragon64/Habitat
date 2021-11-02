package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

public class HabitatWoodType {
    public static final WoodType FAIRY_RING_MUSHROOM = register("fairy_ring_mushroom");

    private static WoodType register(String name) {
        return WoodType.register(WoodType.create(new ResourceLocation(Habitat.MODID, name).toString()));
    }
}