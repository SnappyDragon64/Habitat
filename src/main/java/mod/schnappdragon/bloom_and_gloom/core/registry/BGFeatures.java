package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.common.world.gen.features.SlimeFernFeature;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BloomAndGloom.MOD_ID);

    public final static RegistryObject<Feature<BlockClusterFeatureConfig>> SLIME_CHUNK_RANDOM_PATCH_FEATURE = FEATURES.register("slime_chunk_random_patch_feature", () -> new SlimeFernFeature(BlockClusterFeatureConfig.field_236587_a_));
}
