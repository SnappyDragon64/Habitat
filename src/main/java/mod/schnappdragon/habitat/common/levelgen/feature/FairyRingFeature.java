package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.FairyRingConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FairyRingFeature extends Feature<FairyRingConfiguration> {
    public FairyRingFeature(Codec<FairyRingConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<FairyRingConfiguration> context) {
        ChunkGenerator generator = context.chunkGenerator();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        FairyRingConfiguration config = context.config();
        boolean[] flag = { false };
        int rad = config.radius();
        int x = 0;
        int z = rad;
        int p = 1 - rad;

        this.setMushroom(world, pos.offset(rad, 0, 0), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-rad, 0, 0), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(0, 0, rad), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(0, 0, -rad), config, rand, generator, flag);

        while (x < z - 1) {
            x++;

            if (p >= 0) {
                z--;
                p = p + 2 * x - 2 * z + 1;
            } else
                p = p + 2 * x + 1;

            this.setMushrooms(world, pos, x, z, config, rand, generator, flag);
        }

        config.surfaceFeature().get().place(world, generator, rand, pos.below());
        config.mobGroupFeature().get().place(world, generator, rand, pos);

        return true;
    }

    public void setMushrooms(WorldGenLevel world, BlockPos pos, int x, int z, FairyRingConfiguration config, RandomSource rand, ChunkGenerator generator, boolean[] flag) {
        this.setMushroom(world, pos.offset(-x, 0 , z), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-z, 0 , x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-z, 0, -x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-x, 0 , -z), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(x, 0 , -z), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(z, 0 , -x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(z, 0 , x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(x, 0 , z), config, rand, generator, flag);
    }

    public void setMushroom(WorldGenLevel world, BlockPos pos, FairyRingConfiguration config, RandomSource rand, ChunkGenerator generator, boolean[] flag) {
        if (world.isEmptyBlock(pos) && world.getBlockState(pos.below()).is(config.canBePlacedOn())) {
            if (!flag[0] && rand.nextInt(8) == 0) {
                PlacedFeature placedFeature = config.hugeMushroomFeature().get();

                if (placedFeature.place(world, generator, rand, pos)) {
                    flag[0] = true;
                } else
                    this.setBlock(world, pos, config.mushroomProvider().getState(rand, pos));
            } else
                this.setBlock(world, pos, config.mushroomProvider().getState(rand, pos));
        }
    }
}