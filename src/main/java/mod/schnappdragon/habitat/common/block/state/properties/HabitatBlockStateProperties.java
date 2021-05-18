package mod.schnappdragon.habitat.common.block.state.properties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;

public class HabitatBlockStateProperties {
    public static final BooleanProperty ON_COOLDOWN = BooleanProperty.create("on_cooldown");
    public static final BooleanProperty HAS_STEW = BooleanProperty.create("has_stew");
    public static final BooleanProperty ON_CEILING = BooleanProperty.create("on_ceiling");
    public static final IntegerProperty MUSHROOMS_1_4 = IntegerProperty.create("mushrooms", 1, 4);
    public static final BooleanProperty DUSTED = BooleanProperty.create("dusted");
}