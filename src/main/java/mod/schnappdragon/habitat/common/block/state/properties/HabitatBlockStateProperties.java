package mod.schnappdragon.habitat.common.block.state.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class HabitatBlockStateProperties {
    public static final BooleanProperty ON_COOLDOWN = BooleanProperty.create("on_cooldown");
    public static final BooleanProperty HAS_STEW = BooleanProperty.create("has_stew");
    public static final BooleanProperty SLIMY = BooleanProperty.create("slimy");
    public static final IntegerProperty MUSHROOMS_1_4 = IntegerProperty.create("mushrooms", 1, 4);
    public static final EnumProperty<VerticalSlabType> VERTICAL_SLAB_TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty CHAINED_UP = BooleanProperty.create("chained_up");
    public static final BooleanProperty CHAINED_DOWN = BooleanProperty.create("chained_down");
    public static final BooleanProperty CHAINED_NORTH = BooleanProperty.create("chained_north");
    public static final BooleanProperty CHAINED_EAST = BooleanProperty.create("chained_east");
    public static final BooleanProperty CHAINED_SOUTH = BooleanProperty.create("chained_south");
    public static final BooleanProperty CHAINED_WEST = BooleanProperty.create("chained_west");
}