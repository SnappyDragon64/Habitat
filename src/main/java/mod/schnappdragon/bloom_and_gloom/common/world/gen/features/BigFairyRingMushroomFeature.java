package mod.schnappdragon.bloom_and_gloom.common.world.gen.features;

import com.mojang.serialization.Codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import mod.schnappdragon.bloom_and_gloom.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.AbstractBigMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class BigFairyRingMushroomFeature extends AbstractBigMushroomFeature {
    public BigFairyRingMushroomFeature(Codec<BigMushroomFeatureConfig> codec) {
        super(codec);
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
        WeightedBlockStateProvider blockStateProvider = new WeightedBlockStateProvider().addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState(), 1).addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 2), 2).addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 3), 3).addWeightedBlockstate(BGBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 4), 3);
        HashMap<Direction, Integer> dirLengthMap = new HashMap<>();
        HashMap<Integer, Direction> indexDirMap = new HashMap<>();
        ArrayList<Integer> cornerLength = new ArrayList<>();
        int index1 = 0;

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            indexDirMap.put(index1, dir);
            dirLengthMap.put(dir, rand.nextBoolean() ? rand.nextInt(i0) - 8 : 0);
            BloomAndGloom.getLOGGER().info("1 " + dirLengthMap.get(dir) + "\n");
            index1++;
        }
        for (int i = 0; i < 4; ++i)
            cornerLength.add((int) (((float) dirLengthMap.get(indexDirMap.get(i)) + dirLengthMap.get(indexDirMap.get(i < 3 ? i + 1 : 0))) / 2) + rand.nextInt(2) - 1);

        for (int i = 0; i < i0; ++i) {
            blockpos$mutable.setPos(pos).move(Direction.UP, i);
            if (world.getBlockState(blockpos$mutable).canBeReplacedByLogs(world, blockpos$mutable)) {
                this.setBlockState(world, blockpos$mutable, config.stemProvider.getBlockState(rand, pos));
            }

            int index2 = 0;
            for (Integer l : dirLengthMap.values()) {
                BloomAndGloom.getLOGGER().info("2 " + l + "\n");
                if (i <= l + 1) {
                    Direction dir = indexDirMap.get(index2);
                    if (i < 1 + 1)
                        tryPlacing(world, config.stemProvider.getBlockState(rand, pos).getBlock().getDefaultState(), blockpos$mutable, dir, false, false);
                    else if (rand.nextBoolean())
                        tryPlacing(world, blockStateProvider.getBlockState(rand, pos), blockpos$mutable, dir, false, false);

                    int cornerLen = cornerLength.get(index2);
                    if (i < cornerLen + 1) {
                        if (i < cornerLen)
                            tryPlacing(world, config.stemProvider.getBlockState(rand, pos).getBlock().getDefaultState(), blockpos$mutable, dir, true, false);
                        else if (rand.nextBoolean())
                            tryPlacing(world, blockStateProvider.getBlockState(rand, pos), blockpos$mutable, dir, true, false);
                    }
                }
                index2++;
            }

            if (i > i0 - 6) {
                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    if (rand.nextInt(5) == 0) {
                        tryPlacing(world, BGBlocks.FAIRYLIGHT.get().getDefaultState(), blockpos$mutable, dir, false, true);
                        break;
                    } else if (rand.nextInt(5) == 0) {
                        tryPlacing(world, BGBlocks.FAIRYLIGHT.get().getDefaultState(), blockpos$mutable, dir, true, true);
                        break;
                    }
                }
            }
        }
    }

    protected void tryPlacing(IWorld world, BlockState state, BlockPos.Mutable blockpos$mutable, Direction dir, boolean rotateY, boolean isLight) {
        BlockPos pos = blockpos$mutable.offset(dir);
        if (rotateY)
            pos = pos.offset(dir.rotateY());

        if (world.getBlockState(pos).getMaterial().isReplaceable() && (isLight && world.getBlockState(pos.down()).getBlock() != BGBlocks.FAIRYLIGHT.get() || world.getBlockState(pos.down()).isSolid())) {
            this.setBlockState(world, pos, state);
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