package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.levelgen.feature.*;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.FairyRingConfiguration;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.HugeBallCactusConfiguration;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.MobGroupConfiguration;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.SlimeFernConfiguration;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HabitatFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Habitat.MODID);

    public final static RegistryObject<Feature<SlimeFernConfiguration>> SLIME_FERN_FEATURE = FEATURES.register("slime_fern",
            () -> new SlimeFernFeature(SlimeFernConfiguration.CODEC));

    public final static RegistryObject<Feature<FairyRingConfiguration>> FAIRY_RING_FEATURE = FEATURES.register("fairy_ring",
            () -> new FairyRingFeature(FairyRingConfiguration.CODEC));

    public final static RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_FAIRY_RING_MUSHROOM_FEATURE = FEATURES.register("huge_fairy_ring_mushroom",
            () -> new HugeFairyRingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

    public final static RegistryObject<Feature<HugeBallCactusConfiguration>> HUGE_BALL_CACTUS_FEATURE = FEATURES.register("huge_ball_cactus",
            () -> new HugeBallCactusFeature(HugeBallCactusConfiguration.CODEC));

    public final static RegistryObject<Feature<MobGroupConfiguration>> MOB_GROUP_FEATURE = FEATURES.register("mob_group",
            () -> new MobGroupFeature(MobGroupConfiguration.CODEC));
}
