package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HugeBallCactusConfiguration(BlockStateProvider cactusProvider, BlockStateProvider floweringCactusProvider, float floweringCactusChance) implements FeatureConfiguration {
    public static final Codec<HugeBallCactusConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("cactus_provider").forGetter(HugeBallCactusConfiguration::cactusProvider), BlockStateProvider.CODEC.fieldOf("flowering_cactus_provider").forGetter(HugeBallCactusConfiguration::floweringCactusProvider), Codec.FLOAT.fieldOf("flowering_cactus_chance").orElse(0.4F).forGetter(HugeBallCactusConfiguration::floweringCactusChance)).apply(instance, HugeBallCactusConfiguration::new));
}