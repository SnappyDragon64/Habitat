package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HugeBallCactusFeatureConfiguration(BlockStateProvider cactusProvider, BlockStateProvider floweringCactusProvider, float floweringCactusChance) implements FeatureConfiguration {
    public static final Codec<HugeBallCactusFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("cactus_provider").forGetter(HugeBallCactusFeatureConfiguration::cactusProvider), BlockStateProvider.CODEC.fieldOf("flowering_cactus_provider").forGetter(HugeBallCactusFeatureConfiguration::floweringCactusProvider), Codec.FLOAT.fieldOf("flowering_cactus_chance").forGetter(HugeBallCactusFeatureConfiguration::floweringCactusChance)).apply(instance, HugeBallCactusFeatureConfiguration::new));
}