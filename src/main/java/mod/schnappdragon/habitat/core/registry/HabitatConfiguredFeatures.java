package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class HabitatConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Habitat.MODID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> PATCH_RAFFLESIA = CONFIGURED_FEATURES.register("rafflesia_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(2, 6, 1, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(HabitatBlocks.RAFFLESIA.get().defaultBlockState())), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.GRASS_BLOCK))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PATCH_KABLOOM_BUSH = CONFIGURED_FEATURES.register("kabloom_bush_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(HabitatBlocks.KABLOOM_BUSH.get().defaultBlockState().setValue(KabloomBushBlock.AGE, 7))), List.of(Blocks.GRASS_BLOCK))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PATCH_SLIME_FERN = CONFIGURED_FEATURES.register("slime_fern_patch", () -> new ConfiguredFeature<>(HabitatFeatures.SLIME_FERN_FEATURE.get(), FeatureConfiguration.NONE));

    public static final RegistryObject<ConfiguredFeature<?, ?>> PATCH_BALL_CACTUS = CONFIGURED_FEATURES.register("ball_cactus_patch", () -> new ConfiguredFeature<>(Feature.RANDOM_PATCH, new RandomPatchConfiguration(4, 5, 1, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS.get().defaultBlockState(), 3).add(HabitatBlocks.FLOWERING_PINK_BALL_CACTUS.get().defaultBlockState(), 3).add(HabitatBlocks.FLOWERING_RED_BALL_CACTUS.get().defaultBlockState(), 2).add(HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS.get().defaultBlockState(), 1))), BlockPredicate.allOf(BlockPredicate.replaceable(), BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), List.of(Blocks.SAND, Blocks.RED_SAND)))))));

    public static final RegistryObject<ConfiguredFeature<?, ?>> FAIRY_RING = CONFIGURED_FEATURES.register("fairy_ring", () -> new ConfiguredFeature<>(HabitatFeatures.FAIRY_RING_FEATURE.get(), FeatureConfiguration.NONE));
    public static final RegistryObject<ConfiguredFeature<?, ?>> HUGE_FAIRY_RING_MUSHROOM = CONFIGURED_FEATURES.register("huge_fairy_ring_mushroom", () -> new ConfiguredFeature<>(HabitatFeatures.HUGE_FAIRY_RING_MUSHROOM_FEATURE.get(), new HugeMushroomFeatureConfiguration(BlockStateProvider.simple(HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)), BlockStateProvider.simple(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 2)));
}