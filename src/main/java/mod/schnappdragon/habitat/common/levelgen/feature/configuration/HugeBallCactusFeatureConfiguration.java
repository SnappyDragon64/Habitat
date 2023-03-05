package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public record HugeBallCactusFeatureConfiguration(SimpleStateProvider cactusProvider, WeightedStateProvider floweringCactusProvider, float floweringCactusChance) implements FeatureConfiguration {
    public static final Codec<HugeBallCactusFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(SimpleStateProvider.CODEC.fieldOf("cactus_provider").forGetter(HugeBallCactusFeatureConfiguration::cactusProvider), WeightedStateProvider.CODEC.fieldOf("flowering_cactus_provider").forGetter(HugeBallCactusFeatureConfiguration::floweringCactusProvider), Codec.FLOAT.fieldOf("flowering_cactus_chance").forGetter(HugeBallCactusFeatureConfiguration::floweringCactusChance)).apply(instance, HugeBallCactusFeatureConfiguration::new));
}