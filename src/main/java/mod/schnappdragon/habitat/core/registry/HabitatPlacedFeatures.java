package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class HabitatPlacedFeatures {
    public static final PlacedFeature PLACED_FAIRY_RING = HabitatConfiguredFeatures.FAIRY_RING.placed();

    public static void registerPlacedFeatures() {
        //register("rafflesia_patch", PLACED_PATCH_RAFFLESIA);
        //register("kabloom_bush_patch", PLACED_PATCH_KABLOOM_BUSH);
        //register("slime_fern_patch", PLACED_PATCH_SLIME_FERN);
        //register("ball_cactus_patch", PLACED_PATCH_BALL_CACTUS);
        register("fairy_ring", PLACED_FAIRY_RING);
    }

    private static void register(String id, PlacedFeature placedFeature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(Habitat.MODID, id), placedFeature);
    }
}
