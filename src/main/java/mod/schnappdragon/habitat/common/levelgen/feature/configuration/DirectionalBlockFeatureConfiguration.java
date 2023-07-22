package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record DirectionalBlockFeatureConfiguration(BlockStateProvider toPlace, HolderSet<Block> canBePlacedOn) implements FeatureConfiguration {
    public static final Codec<DirectionalBlockFeatureConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(BlockStateProvider.CODEC.fieldOf("to_place").forGetter(DirectionalBlockFeatureConfiguration::toPlace), RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(DirectionalBlockFeatureConfiguration::canBePlacedOn)).apply(instance, DirectionalBlockFeatureConfiguration::new));
}