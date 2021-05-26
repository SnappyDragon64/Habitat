package mod.schnappdragon.habitat.common.block.misc;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class ChestVariants {
    private static final Set<ChestVariant> VARIANTS = new ObjectArraySet<>();
    public static final ChestVariant FAIY_RING_MUSHROOM_NORMAL = register(new ChestVariant("fairy_ring_mushroom"));
    public static final ChestVariant FAIY_RING_MUSHROOM_TRAPPED = register(new ChestVariant("fairy_ring_mushroom", true));

    public static ChestVariant register(ChestVariant variant) {
        VARIANTS.add(variant);
        return variant;
    }

    public static Set<ChestVariant> getVariants() {
        return VARIANTS;
    }

    public static class ChestVariant {
        private final String name;
        private final boolean trapped;

        public ChestVariant(String name, boolean trapped) {
            this.name = name;
            this.trapped = trapped;
        }

        public ChestVariant(String name) {
            this(name, false);
        }

        public String getName() {
            return name;
        }

        public boolean isTrapped() {
            return trapped;
        }

        public ResourceLocation getSingleMaterial() {
            return getResourceLocation(getPath(name, trapped ? "trapped" : "normal"));
        }

        public ResourceLocation getRightMaterial() {
            return getResourceLocation(getPath(name, trapped ? "trapped" : "normal") + "_right");
        }

        public ResourceLocation getLeftMaterial() {
            return getResourceLocation(getPath(name, trapped ? "trapped" : "normal") + "_left");
        }

        private static ResourceLocation getResourceLocation(String path) {
            return new ResourceLocation(Habitat.MOD_ID, path);
        }

        private static String getPath(String name, String type) {
            return "entity/chest/" + name + "_" + type;
        }
    }
}