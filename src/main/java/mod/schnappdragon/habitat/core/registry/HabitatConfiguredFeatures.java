package mod.schnappdragon.habitat.core.registry;

import com.google.common.collect.ImmutableSet;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.common.block.RafflesiaBlock;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;

public class HabitatConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_RAFFLESIA = Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HabitatBlocks.RAFFLESIA.get().getDefaultState().with(RafflesiaBlock.AGE, 2)), SimpleBlockPlacer.PLACER).xSpread(6).ySpread(1).zSpread(6).tries(2).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(4);

    public static final ConfiguredFeature<?, ?> PATCH_KABLOOM_BUSH = Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HabitatBlocks.KABLOOM_BUSH.get().getDefaultState().with(KabloomBushBlock.AGE, 7)), SimpleBlockPlacer.PLACER).xSpread(4).ySpread(1).zSpread(4).tries(20).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(200);

    public static final ConfiguredFeature<?, ?> PATCH_SLIME_FERN = HabitatFeatures.SLIME_FERN_FEATURE.get().withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(HabitatBlocks.SLIME_FERN.get().getDefaultState()), SimpleBlockPlacer.PLACER).xSpread(4).ySpread(5).zSpread(4).tries(50).build()).range(40).chance(3);

    public static final ConfiguredFeature<?, ?> PATCH_BALL_CACTUS = Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().addWeightedBlockstate(HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS.get().getDefaultState(), 3).addWeightedBlockstate(HabitatBlocks.FLOWERING_PINK_BALL_CACTUS.get().getDefaultState(), 3).addWeightedBlockstate(HabitatBlocks.FLOWERING_RED_BALL_CACTUS.get().getDefaultState(), 2).addWeightedBlockstate(HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS.get().getDefaultState(), 1), SimpleBlockPlacer.PLACER).xSpread(5).ySpread(1).zSpread(5).tries(5).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(16);

    public static final ConfiguredFeature<?, ?> FAIRY_RING = HabitatFeatures.FAIRY_RING_FEATURE.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(3);
    public static final ConfiguredFeature<?, ?> FAIRY_RING_PLAINS = HabitatFeatures.FAIRY_RING_FEATURE.get().withConfiguration(new NoFeatureConfig()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(300);
    public static final ConfiguredFeature<?, ?> HUGE_FAIRY_RING_MUSHROOM_DARK_FORESTS = HabitatFeatures.HUGE_FAIRY_RING_MUSHROOM_FEATURE.get().withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get().getDefaultState().with(HugeMushroomBlock.DOWN, false)), new SimpleBlockStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get().getDefaultState().with(HugeMushroomBlock.UP, false).with(HugeMushroomBlock.DOWN, false)), 2)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(20);
    public static final ConfiguredFeature<?, ?> HUGE_FAIRY_RING_MUSHROOM_MUSHROOM_FIELDS = HabitatFeatures.HUGE_FAIRY_RING_MUSHROOM_FEATURE.get().withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get().getDefaultState().with(HugeMushroomBlock.DOWN, false)), new SimpleBlockStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get().getDefaultState().with(HugeMushroomBlock.UP, false).with(HugeMushroomBlock.DOWN, false)), 2)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).chance(80);
    public static final ConfiguredFeature<?, ?> GROWN_HUGE_FAIRY_RING_MUSHROOM = HabitatFeatures.HUGE_FAIRY_RING_MUSHROOM_FEATURE.get().withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get().getDefaultState().with(HugeMushroomBlock.DOWN, false)), new SimpleBlockStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get().getDefaultState().with(HugeMushroomBlock.UP, false).with(HugeMushroomBlock.DOWN, false)), 2));

    public static void registerConfiguredFeatures() {
        register("rafflesia_patch", PATCH_RAFFLESIA);
        register("kabloom_bush_patch", PATCH_KABLOOM_BUSH);
        register("slime_fern_patch", PATCH_SLIME_FERN);
        register("ball_cactus_patch", PATCH_BALL_CACTUS);
        register("fairy_ring", FAIRY_RING);
        register("fairy_ring_plains", FAIRY_RING_PLAINS);
        register("huge_fairy_ring_mushroom_dark_forests", HUGE_FAIRY_RING_MUSHROOM_DARK_FORESTS);
        register("huge_fairy_ring_mushroom_mushroom_fields", HUGE_FAIRY_RING_MUSHROOM_MUSHROOM_FIELDS);
        register("grown_huge_fairy_ring_mushroom", GROWN_HUGE_FAIRY_RING_MUSHROOM);
    }

    private static void register(String identifier, ConfiguredFeature<?, ?> configuredFeature) {
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Habitat.MOD_ID + ":" + identifier, configuredFeature);
    }
}