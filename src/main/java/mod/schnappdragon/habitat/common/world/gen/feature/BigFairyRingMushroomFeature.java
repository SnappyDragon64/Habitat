package mod.schnappdragon.habitat.common.world.gen.feature;

import com.mojang.serialization.Codec;

import java.util.Random;

import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.util.CompatHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class BigFairyRingMushroomFeature extends AbstractBigMushroomFeature {
    public BigFairyRingMushroomFeature(Codec<BigMushroomFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BigMushroomFeatureConfig config) {
        int i = this.func_227211_a_(rand);
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        if (!this.func_227209_a_(reader, pos, i, blockpos$mutable, config)) {
            return false;
        } else {
            this.func_225564_a_(reader, rand, pos, i, blockpos$mutable, config);
            this.func_227210_a_(reader, rand, pos, config, i, blockpos$mutable);
            return true;
        }
    }

    @Override
    protected int func_227211_a_(Random rand) {
        int i = rand.nextInt(3) + 8;
        if (rand.nextInt(12) == 0) {
            i *= 2;
        }

        return i;
    }

    @Override
    protected void func_227210_a_(IWorld world, Random rand, BlockPos pos, BigMushroomFeatureConfig config, int i0, BlockPos.Mutable blockpos$mutable) {
        WeightedBlockStateProvider mushroomProvider = new WeightedBlockStateProvider().addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState(), 1).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 2), 2).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 3), 3).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 4), 3);

        BlockState stem = config.stemProvider.getBlockState(rand, pos);
        boolean enhancedFlag = stem.isIn(HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get()) && CompatHelper.checkMods("enhanced_mushrooms");
        if (enhancedFlag)
            stem = HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM.get().getDefaultState();

        for (int i = 0; i < i0; ++i) {
            blockpos$mutable.setPos(pos).move(Direction.UP, i);
            if (world.getBlockState(blockpos$mutable).canBeReplacedByLogs(world, blockpos$mutable)) {
                this.setBlockState(world, blockpos$mutable, stem);
            }

            boolean breakFlag = false;
            if (i > i0 - 7) {
                for (int x = -1; x <= 1; ++x) {
                    for (int z = -1; z <= 1; ++z) {
                        BlockPos.Mutable inPos = new BlockPos.Mutable().setAndOffset(blockpos$mutable, x, 0, z);
                        if (world.getBlockState(inPos).canBeReplacedByLeaves(world, inPos)) {
                            if (i > i0 - 6 && (x != 0 || z != 0) && rand.nextInt(12) == 0 && !world.getBlockState(inPos.down()).isIn(HabitatBlocks.FAIRYLIGHT.get())) {
                                this.setBlockState(world, inPos, HabitatBlocks.FAIRYLIGHT.get().getDefaultState());
                                breakFlag = true;
                                break;
                            }
                        }
                    }
                    if (breakFlag)
                        break;
                }
            }
        }

        if (!enhancedFlag)
            stem = stem.with(HugeMushroomBlock.UP, true);

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                if (x != 0 || z != 0) {
                    int len = rand.nextInt(3) < 2 ? MathHelper.ceil((float) rand.nextInt(i0 - 6) / 2) : 0;
                    blockpos$mutable.setAndOffset(pos, x, -1, z);

                    for (int i = 0; i < len + 1; ++i) {
                        blockpos$mutable.move(Direction.UP, 1);
                        if (world.getBlockState(blockpos$mutable).canBeReplacedByLogs(world, blockpos$mutable) && world.getBlockState(blockpos$mutable.down()).isOpaqueCube(world, blockpos$mutable.down())) {
                            if (i < len)
                                this.setBlockState(world, blockpos$mutable, stem);
                            else if (rand.nextInt(3) == 0)
                                this.setBlockState(world, blockpos$mutable, mushroomProvider.getBlockState(rand, pos));
                        }
                    }
                }
            }
        }
    }

    protected void func_225564_a_(IWorld world, Random rand, BlockPos pos, int i0, BlockPos.Mutable blockpos$mutable, BigMushroomFeatureConfig config) {
        for (int i = i0 - 6; i <= i0; ++i) {
            int j = i < i0 ? config.foliageRadius : config.foliageRadius - 1;
            int k = config.foliageRadius - 2;

            for (int l = -j; l <= j; ++l) {
                for (int i1 = -j; i1 <= j; ++i1) {
                    boolean flag = l == -j;
                    boolean flag1 = l == j;
                    boolean flag2 = i1 == -j;
                    boolean flag3 = i1 == j;
                    boolean flag4 = flag || flag1;
                    boolean flag5 = flag2 || flag3;
                    if (i >= i0 || flag4 != flag5) {
                        blockpos$mutable.setAndOffset(pos, l, i, i1);
                        if (world.getBlockState(blockpos$mutable).canBeReplacedByLeaves(world, blockpos$mutable)) {
                            this.setBlockState(world, blockpos$mutable, config.capProvider.getBlockState(rand, pos).with(HugeMushroomBlock.UP, i >= i0 - 1).with(HugeMushroomBlock.WEST, l < -k).with(HugeMushroomBlock.EAST, l > k).with(HugeMushroomBlock.NORTH, i1 < -k).with(HugeMushroomBlock.SOUTH, i1 > k));
                        }
                    }
                }
            }
        }
    }

    protected int func_225563_a_(int i1, int i2, int i3, int i4) {
        return i4 < i2 && i4 >= i2 - 3 || i4 == i2 ? i3 : 0;
    }
}