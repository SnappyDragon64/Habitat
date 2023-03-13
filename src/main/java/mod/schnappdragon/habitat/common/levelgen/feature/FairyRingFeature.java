package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.levelgen.feature.configuration.FairyRingFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FairyRingFeature extends Feature<FairyRingFeatureConfiguration> {
    public FairyRingFeature(Codec<FairyRingFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<FairyRingFeatureConfiguration> context) {
        ChunkGenerator generator = context.chunkGenerator();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = context.random();
        FairyRingFeatureConfiguration config = context.config();
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

        return true;
    }

    public void setMushrooms(WorldGenLevel world, BlockPos pos, int x, int z, FairyRingFeatureConfiguration config, RandomSource rand, ChunkGenerator generator, boolean[] flag) {
        this.setMushroom(world, pos.offset(-x, 0 , z), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-z, 0 , x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-z, 0, -x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(-x, 0 , -z), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(x, 0 , -z), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(z, 0 , -x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(z, 0 , x), config, rand, generator, flag);
        this.setMushroom(world, pos.offset(x, 0 , z), config, rand, generator, flag);
    }

    public void setMushroom(WorldGenLevel world, BlockPos pos, FairyRingFeatureConfiguration config, RandomSource rand, ChunkGenerator generator, boolean[] flag) {
        pos = pos.below();
        BlockState base = world.getBlockState(pos.below());

        if (world.isEmptyBlock(pos) && base.is(BlockTags.DIRT)) {
            if (!flag[0] && rand.nextInt(8) == 0) {
                PlacedFeature placedFeature = config.feature().get();

                if (placedFeature.place(world, generator, rand, pos)) {
                    flag[0] = true;
                } else
                    this.setBlock(world, pos, config.mushroomProvider().getState(rand, pos));
            } else
                this.setBlock(world, pos, config.mushroomProvider().getState(rand, pos));
        }
    }
}