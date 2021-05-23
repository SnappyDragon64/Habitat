package mod.schnappdragon.habitat.common.block.misc;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.util.ResourceLocation;

public class HabitatWoodTypes {
    public static final WoodType FAIRY_RING_MUSHROOM = WoodType.create(new ResourceLocation(Habitat.MOD_ID, "fairy_ring_mushroom").toString());

    public static void setupAtlas() {
        Atlases.addWoodType(FAIRY_RING_MUSHROOM);
    }
}