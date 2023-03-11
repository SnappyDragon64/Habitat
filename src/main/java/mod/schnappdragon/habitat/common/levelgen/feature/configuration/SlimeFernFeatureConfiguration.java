package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SlimeFernFeatureConfiguration(BlockStateProvider baseStateProvider, BlockStateProvider ceilingStateProvider, BlockStateProvider wallStateProvider, int tries, int xzSpread, int ySpread) implements FeatureConfiguration {
    public static final Codec<SlimeFernFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("base_state_provider").forGetter(SlimeFernFeatureConfiguration::baseStateProvider), BlockStateProvider.CODEC.fieldOf("ceiling_state_provider").forGetter(SlimeFernFeatureConfiguration::ceilingStateProvider), BlockStateProvider.CODEC.fieldOf("walL_state_provider").forGetter(SlimeFernFeatureConfiguration::wallStateProvider), Codec.INT.fieldOf("tries").forGetter(SlimeFernFeatureConfiguration::tries), Codec.INT.fieldOf("xz_spread").forGetter(SlimeFernFeatureConfiguration::xzSpread), Codec.INT.fieldOf("y_spread").forGetter(SlimeFernFeatureConfiguration::ySpread)).apply(instance, SlimeFernFeatureConfiguration::new));
}