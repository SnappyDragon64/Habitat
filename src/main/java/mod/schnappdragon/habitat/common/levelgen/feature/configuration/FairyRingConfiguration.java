package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record FairyRingConfiguration(int radius, BlockStateProvider mushroomProvider, Holder<PlacedFeature> hugeMushroomFeature, Holder<PlacedFeature> mobGroupFeature, HolderSet<Block> canBePlacedOn) implements FeatureConfiguration {
    public static final Codec<FairyRingConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("radius").orElse(8).forGetter(FairyRingConfiguration::radius),BlockStateProvider.CODEC.fieldOf("mushroom_provider").forGetter(FairyRingConfiguration::mushroomProvider), PlacedFeature.CODEC.fieldOf("huge_mushroom_feature").forGetter(FairyRingConfiguration::hugeMushroomFeature), PlacedFeature.CODEC.fieldOf("mob_group_feature").forGetter(FairyRingConfiguration::mobGroupFeature), RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).fieldOf("can_be_placed_on").forGetter(FairyRingConfiguration::canBePlacedOn)).apply(instance, FairyRingConfiguration::new));
}