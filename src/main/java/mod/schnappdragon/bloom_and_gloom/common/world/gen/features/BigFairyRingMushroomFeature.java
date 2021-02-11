package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class BigFairyRingMushroomFeature extends AbstractBigMushroomFeature {
    public BigFairyRingMushroomFeature(Codec<BigMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected void func_225564_a_(IWorld world, Random rand, BlockPos pos, int p_225564_4_, BlockPos.Mutable blockpos$mutable, BigMushroomFeatureConfig config) {
        for(int i = p_225564_4_ - 5; i <= p_225564_4_; ++i) {
            int j = i < p_225564_4_ ? config.foliageRadius : config.foliageRadius - 1;
            int k = config.foliageRadius - 2;

            for(int l = -j; l <= j; ++l) {
                for(int i1 = -j; i1 <= j; ++i1) {
                    boolean flag = l == -j;
                    boolean flag1 = l == j;
                    boolean flag2 = i1 == -j;
                    boolean flag3 = i1 == j;
                    boolean flag4 = flag || flag1;
                    boolean flag5 = flag2 || flag3;
                    if (i >= p_225564_4_ || flag4 != flag5) {
                        blockpos$mutable.setAndOffset(pos, l, i, i1);
                        if (world.getBlockState(blockpos$mutable).canBeReplacedByLeaves(world, blockpos$mutable)) {
                            this.setBlockState(world, blockpos$mutable, config.capProvider.getBlockState(rand, pos).with(HugeMushroomBlock.UP, i >= p_225564_4_ - 1).with(HugeMushroomBlock.WEST, l < -k).with(HugeMushroomBlock.EAST, l > k).with(HugeMushroomBlock.NORTH, i1 < -k).with(HugeMushroomBlock.SOUTH, i1 > k));
                        }
                    }
                }
            }
        }
    }

    protected int func_225563_a_(int p_225563_1_, int p_225563_2_, int p_225563_3_, int p_225563_4_) {
        int i = 0;
        if (p_225563_4_ < p_225563_2_ && p_225563_4_ >= p_225563_2_ - 8) {
            i = p_225563_3_;
        } else if (p_225563_4_ == p_225563_2_) {
            i = p_225563_3_;
        }

        return i;
    }
}