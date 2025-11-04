package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.entity.MobCategory;

public class HabitatMobCategories {
    public static final MobCategory PASSERINE = create("passerine", 15, true, false, 64);

    private static MobCategory create(String name, int maxCap, boolean isFriendly, boolean isPersistent, int despawnDistance) {
        String namespacedId = Habitat.MODID + "_" + name;
        String internalName  = namespacedId.toUpperCase();

        return MobCategory.create(internalName, namespacedId, maxCap, isFriendly, isPersistent, despawnDistance);
    }
}
