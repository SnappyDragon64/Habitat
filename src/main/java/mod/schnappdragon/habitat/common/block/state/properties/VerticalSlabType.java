package mod.schnappdragon.habitat.common.block.state.properties;

import net.minecraft.util.IStringSerializable;

public enum VerticalSlabType implements IStringSerializable {
    HALF("half"),
    DOUBLE("double");

    private final String name;

    VerticalSlabType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getString() {
        return this.name;
    }
}