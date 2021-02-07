package mod.schnappdragon.bloom_and_gloom.common.state.properties;

import net.minecraft.state.BooleanProperty;

public class BGBlockStateProperties {
    public static final BooleanProperty ON_COOLDOWN = BooleanProperty.create("on_cooldown");
    public static final BooleanProperty HAS_STEW = BooleanProperty.create("has_stew");
    public static final BooleanProperty POLLINATED = BooleanProperty.create("pollinated");
    public static final BooleanProperty ON_CEILING = BooleanProperty.create("on_ceiling");
    public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
}