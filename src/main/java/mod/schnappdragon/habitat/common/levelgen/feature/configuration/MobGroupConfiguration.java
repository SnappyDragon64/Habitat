package mod.schnappdragon.habitat.common.levelgen.feature.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MobGroupConfiguration(HolderSet<EntityType<?>> entityTypes, int minCount, int maxCount, int xzSpread) implements FeatureConfiguration {
    public static final Codec<MobGroupConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(RegistryCodecs.homogeneousList(Registry.ENTITY_TYPE_REGISTRY).fieldOf("entity_types").forGetter(MobGroupConfiguration::entityTypes), Codec.INT.fieldOf("min_count").forGetter(MobGroupConfiguration::minCount), Codec.INT.fieldOf("max_count").forGetter(MobGroupConfiguration::maxCount), Codec.INT.fieldOf("xz_spread").forGetter(MobGroupConfiguration::xzSpread)).apply(instance, MobGroupConfiguration::new));
}
