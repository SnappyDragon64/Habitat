package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.MobGroupConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Optional;

public class MobGroupFeature extends Feature<MobGroupConfiguration> {
    public MobGroupFeature(Codec<MobGroupConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MobGroupConfiguration> context) {
        MobGroupConfiguration config = context.config();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        int count = Mth.nextInt(rand, config.minCount(), config.maxCount());
        int i = config.xzSpread() + 1;

        for (int l = 0; l < count; ++l) {
            Optional<Holder<EntityType<?>>> optionalEntityTypeHolder = config.entityTypes().getRandomElement(rand);

            if (optionalEntityTypeHolder.isPresent()) {
                Mob entity = (Mob) optionalEntityTypeHolder.get().get().create(world.getLevel());
                if (entity != null) {
                    entity.moveTo(pos.getX() + 0.5D + rand.nextInt(i) - rand.nextInt(i), pos.getY(), pos.getZ() + 0.5D + rand.nextInt(i) - rand.nextInt(i));
                    entity.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.SPAWNER, null, null);
                    world.addFreshEntity(entity);
                } else
                    return false;
            } else
                return false;
        }

        return true;
    }
}
