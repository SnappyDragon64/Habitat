package mod.schnappdragon.habitat.common.block.misc;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.util.ResourceLocation;

public enum ChestVariant {
    FAIY_RING_MUSHROOM_NORMAL("fairy_ring_mushroom"),
    FAIY_RING_MUSHROOM_TRAPPED("fairy_ring_mushroom", true);

    private final String name;
    private final boolean trapped;

    ChestVariant(String name, boolean trapped) {
        this.name = name;
        this.trapped = trapped;
    }

    ChestVariant(String name) {
        this(name, false);
    }

    public ResourceLocation getSingleResource() {
        return new ResourceLocation(Habitat.MOD_ID, "entity/chest/" + this.name + "_" + (this.trapped ? "trapped" : "normal"));
    }

    public ResourceLocation getRightResource() {
        return new ResourceLocation(Habitat.MOD_ID, "entity/chest/" + this.name + "_" + (this.trapped ? "trapped" : "normal") + "_right");
    }

    public ResourceLocation getLeftResource() {
        return new ResourceLocation(Habitat.MOD_ID, "entity/chest/" + this.name + "_" + (this.trapped ? "trapped" : "normal") + "_left");
    }
}