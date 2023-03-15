package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record HugeBallCactusConfiguration(BlockStateProvider cactusProvider, BlockStateProvider floweringCactusProvider, float floweringCactusChance, HolderSet<Block> canBePlacedOn) implements FeatureConfiguration {
    public static final Codec<HugeBallCactusConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("cactus_provider").forGetter(HugeBallCactusConfiguration::cactusProvider), BlockStateProvider.CODEC.fieldOf("flowering_cactus_provider").forGetter(HugeBallCactusConfiguration::floweringCactusProvider), Codec.FLOAT.fieldOf("flowering_cactus_chance").orElse(0.4F).forGetter(HugeBallCactusConfiguration::floweringCactusChance), RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("can_be_placed_on").forGetter(HugeBallCactusConfiguration::canBePlacedOn)).apply(instance, HugeBallCactusConfiguration::new));
}