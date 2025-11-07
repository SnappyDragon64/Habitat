package mod.schnappdragon.habitat.common.block.state.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class HabitatBlockStateProperties {
    public static final BooleanProperty READY = BooleanProperty.create("ready");
    public static final BooleanProperty HAS_STEW = BooleanProperty.create("has_stew");
    public static final BooleanProperty SLIMY = BooleanProperty.create("slimy");
    public static final IntegerProperty MUSHROOMS_1_4 = IntegerProperty.create("mushrooms", 1, 4);
}