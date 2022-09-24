package mod.schnappdragon.habitat.common.levelgen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class FairyRingFeature extends Feature<NoneFeatureConfiguration> {
    public FairyRingFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        ChunkGenerator generator = context.chunkGenerator();
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random rand = context.random();
        boolean[] flag = {false};

        int rad = 5, x = 0, z = rad, p = 1 - rad;

        this.setMushroom(world, pos.offset(rad, 0, 0), rand, generator, flag);
        this.setMushroom(world, pos.offset(-rad, 0, 0), rand, generator, flag);
        this.setMushroom(world, pos.offset(0, 0, rad), rand, generator, flag);
        this.setMushroom(world, pos.offset(0, 0, -rad), rand, generator, flag);

        while (x < z - 1) {
            x++;

            if (p >= 0) {
                z--;
                p = p + 2 * x - 2 * z + 1;
            } else
                p = p + 2 * x + 1;

            this.setMushrooms(world, pos, x, z, rand, generator, flag);
        }

        return true;
    }

    public void setMushrooms(WorldGenLevel world, BlockPos pos, int x, int z, Random rand, ChunkGenerator generator, boolean[] flag) {
        this.setMushroom(world, pos.offset(-x, 0 , z), rand, generator, flag);
        this.setMushroom(world, pos.offset(-z, 0 , x), rand, generator, flag);
        this.setMushroom(world, pos.offset(-z, 0, -x), rand, generator, flag);
        this.setMushroom(world, pos.offset(-x, 0 , -z), rand, generator, flag);
        this.setMushroom(world, pos.offset(x, 0 , -z), rand, generator, flag);
        this.setMushroom(world, pos.offset(z, 0 , -x), rand, generator, flag);
        this.setMushroom(world, pos.offset(z, 0 , x), rand, generator, flag);
        this.setMushroom(world, pos.offset(x, 0 , z), rand, generator, flag);
    }

    public void setMushroom(WorldGenLevel world, BlockPos pos, Random rand, ChunkGenerator generator, boolean[] flag) {
        pos = pos.below();
        BlockState base = world.getBlockState(pos.below());

        if (world.isEmptyBlock(pos) && base.is(BlockTags.DIRT)) {
            if (!flag[0] && rand.nextInt(8) == 0) {
                ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM.get();

                if (configuredfeature.place(world, generator, rand, pos)) {
                    flag[0] = true;
                }
            }

            this.setBlock(world, pos, this.getMushroom(rand));
        }
    }

    private BlockState getMushroom(Random random) {
        return HabitatBlocks.FAIRY_RING_MUSHROOM.get().defaultBlockState().setValue(FairyRingMushroomBlock.MUSHROOMS, 1 + random.nextInt(4));
    }
}