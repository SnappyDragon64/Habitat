package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;

public enum ChestVariant {
    FAIY_RING_MUSHROOM_NORMAL("fairy_ring_mushroom"),
    FAIY_RING_MUSHROOM_TRAPPED("fairy_ring_mushroom", true);

    private final String location;

    ChestVariant(String name, boolean trapped) {
        this.location = name + "_" + (trapped ? "trapped" : "normal");
    }

    ChestVariant(String name) {
        this(name, false);
    }

    public ResourceLocation getSingle() {
        return new ResourceLocation(Habitat.MODID, "entity/chest/" + this.location);
    }

    public ResourceLocation getRight() {
        return new ResourceLocation(Habitat.MODID, "entity/chest/" + this.location + "_right");
    }

    public ResourceLocation getLeft() {
        return new ResourceLocation(Habitat.MODID, "entity/chest/" + this.location + "_left");
    }
}