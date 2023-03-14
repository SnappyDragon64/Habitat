package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record FairyRingConfiguration(int radius, BlockStateProvider mushroomProvider, Holder<PlacedFeature> feature) implements FeatureConfiguration {
    public static final Codec<FairyRingConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("radius").orElse(8).forGetter(FairyRingConfiguration::radius),BlockStateProvider.CODEC.fieldOf("mushroom_provider").forGetter(FairyRingConfiguration::mushroomProvider), PlacedFeature.CODEC.fieldOf("feature").forGetter(FairyRingConfiguration::feature)).apply(instance, FairyRingConfiguration::new));
}