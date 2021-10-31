package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.levelgen.feature.HugeFairyRingMushroomFeature;
import mod.schnappdragon.habitat.common.levelgen.feature.FairyRingFeature;
import mod.schnappdragon.habitat.common.levelgen.feature.SlimeFernFeature;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Habitat.MODID);

    public final static RegistryObject<Feature<RandomPatchConfiguration>> SLIME_FERN_FEATURE = FEATURES.register("slime_fern",
            () -> new SlimeFernFeature(RandomPatchConfiguration.CODEC));

    public final static RegistryObject<Feature<NoneFeatureConfiguration>> FAIRY_RING_FEATURE = FEATURES.register("fairy_ring",
            () -> new FairyRingFeature(NoneFeatureConfiguration.CODEC));

    public final static RegistryObject<Feature<HugeMushroomFeatureConfiguration>> HUGE_FAIRY_RING_MUSHROOM_FEATURE = FEATURES.register("huge_fairy_ring_mushroom",
            () -> new HugeFairyRingMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
}
