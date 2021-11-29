package mod.schnappdragon.habitat.core.registry;

import com.google.common.collect.ImmutableSet;
import mod.schnappdragon.habitat.common.block.KabloomBushBlock;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.HabitatConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class HabitatConfiguredFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_RAFFLESIA = Feature.RANDOM_PATCH.configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(HabitatBlocks.RAFFLESIA.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE).xspread(6).yspread(1).zspread(6).tries(2).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).decorated(Features.Decorators.HEIGHTMAP_SQUARE).rarity(3);

    public static final ConfiguredFeature<?, ?> PATCH_KABLOOM_BUSH = Feature.RANDOM_PATCH.configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(HabitatBlocks.KABLOOM_BUSH.get().defaultBlockState().setValue(KabloomBushBlock.AGE, 7)), SimpleBlockPlacer.INSTANCE).xspread(4).yspread(1).zspread(4).tries(20).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).build()).decorated(Features.Decorators.HEIGHTMAP_SQUARE).rarity(160);

    public static final ConfiguredFeature<?, ?> PATCH_SLIME_FERN = HabitatFeatures.SLIME_FERN_FEATURE.get().configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(HabitatBlocks.SLIME_FERN.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE).xspread(4).yspread(5).zspread(4).tries(50).build()).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(40)).rarity(2);

    public static final ConfiguredFeature<?, ?> PATCH_BALL_CACTUS = Feature.RANDOM_PATCH.configured(new RandomPatchConfiguration.GrassConfigurationBuilder(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS.get().defaultBlockState(), 3).add(HabitatBlocks.FLOWERING_PINK_BALL_CACTUS.get().defaultBlockState(), 3).add(HabitatBlocks.FLOWERING_RED_BALL_CACTUS.get().defaultBlockState(), 2).add(HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS.get().defaultBlockState(), 1)), SimpleBlockPlacer.INSTANCE).xspread(5).yspread(1).zspread(5).tries(5).build()).decorated(Features.Decorators.HEIGHTMAP_SQUARE).rarity(16);

    public static final ConfiguredFeature<?, ?> FAIRY_RING = HabitatFeatures.FAIRY_RING_FEATURE.get().configured(new NoneFeatureConfiguration());
    public static final ConfiguredFeature<?, ?> HUGE_FAIRY_RING_MUSHROOM = HabitatFeatures.HUGE_FAIRY_RING_MUSHROOM_FEATURE.get().configured(new HugeMushroomFeatureConfiguration(new SimpleStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)), new SimpleStateProvider(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get().defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false)), 2));

    public static void registerConfiguredFeatures() {
        register("rafflesia_patch", PATCH_RAFFLESIA);
        register("kabloom_bush_patch", PATCH_KABLOOM_BUSH);
        register("slime_fern_patch", PATCH_SLIME_FERN);
        register("ball_cactus_patch", PATCH_BALL_CACTUS);
        register("fairy_ring", FAIRY_RING);
        register("huge_fairy_ring_mushroom", HUGE_FAIRY_RING_MUSHROOM);
    }

    private static void register(String id, ConfiguredFeature<?, ?> configuredFeature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Habitat.MODID, id), configuredFeature);
    }
}