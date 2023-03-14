package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SlimeFernConfiguration(BlockStateProvider baseStateProvider, BlockStateProvider ceilingStateProvider, BlockStateProvider wallStateProvider) implements FeatureConfiguration {
    public static final Codec<SlimeFernConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("base_state_provider").forGetter(SlimeFernConfiguration::baseStateProvider), BlockStateProvider.CODEC.fieldOf("ceiling_state_provider").forGetter(SlimeFernConfiguration::ceilingStateProvider), BlockStateProvider.CODEC.fieldOf("walL_state_provider").forGetter(SlimeFernConfiguration::wallStateProvider)).apply(instance, SlimeFernConfiguration::new));
}