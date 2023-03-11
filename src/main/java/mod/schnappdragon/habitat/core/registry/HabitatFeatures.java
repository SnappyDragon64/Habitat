package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.levelgen.feature.FairyRingFeature;
import mod.schnappdragon.habitat.common.levelgen.feature.HugeBallCactusFeature;
import mod.schnappdragon.habitat.common.levelgen.feature.HugeFairyRingMushroomFeature;
import mod.schnappdragon.habitat.common.levelgen.feature.SlimeFernFeature;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.HugeBallCactusFeatureConfiguration;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.SlimeFernFeatureConfiguration;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HabitatFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Habitat.MODID);

    public final static RegistryObject<Feature<SlimeFernFeatureConfiguration>> SLIME_FERN_FEATURE = FEATURES.register("slime_fern",
            () -> new SlimeFernFeature(SlimeFernFeatureConfiguration.CODEC));

    public final static RegistryObject<Feature<NoneFeatureConfiguration>> FAIRY_RING_FEATURE = FEATURES.register("fairy_ring",
            () -> new FairyRingFeature(NoneFeatureConfiguration.CODEC));

    public final static RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_FAIRY_RING_MUSHROOM_FEATURE = FEATURES.register("huge_fairy_ring_mushroom",
            () -> new HugeFairyRingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

    public final static RegistryObject<Feature<HugeBallCactusFeatureConfiguration>> HUGE_BALL_CACTUS_FEATURE = FEATURES.register("huge_ball_cactus",
            () -> new HugeBallCactusFeature(HugeBallCactusFeatureConfiguration.CODEC));
}
