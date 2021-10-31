package mod.schnappdragon.habitat.common.block.misc;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;

public class HabitatWoodTypes {
    public static final WoodType FAIRY_RING_MUSHROOM = WoodType.create(new ResourceLocation(Habitat.MODID, "fairy_ring_mushroom").toString());

    public static void setupAtlas() {
        Sheets.addWoodType(FAIRY_RING_MUSHROOM);
    }
}