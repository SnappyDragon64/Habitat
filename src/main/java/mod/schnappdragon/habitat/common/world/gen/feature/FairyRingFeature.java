package mod.schnappdragon.habitat.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.tags.HabitatBlockTags;
import mod.schnappdragon.habitat.core.util.CompatHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FairyRingFeature extends Feature<NoFeatureConfig> {
    public FairyRingFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int[][] XZ_PAIRS = {
                {1, 5}, {2, 5}, {3, 4}, {4, 4}, {4, 3}, {5, 2}, {5, 1}, {5, 0},
                {0, 5}, {-1, 5}, {-2, 5}, {-3, 4}, {-4, 4}, {-4, 3}, {-5, 2}, {-5, 1},
                {-1, -5}, {-2, -5}, {-3, -4}, {-4, -4}, {-4, -3}, {-5, -2}, {-5, -1}, {-5, 0},
                {0, -5}, {1, -5}, {2, -5}, {3, -4}, {4, -4}, {4, -3}, {5, -2}, {5, -1}
        };
        WeightedBlockStateProvider mushroomProvider = new WeightedBlockStateProvider().addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState(), 1).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 2), 2).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 3), 3).addWeightedBlockstate(HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState().with(FairyRingMushroomBlock.MUSHROOMS, 4), 3);
        boolean bigFlag = false;

        for (int[] XZ : XZ_PAIRS) {
            BlockPos.Mutable blockpos$mutable = pos.add(XZ[0], 0, XZ[1]).toMutable();
            for (int h = 6; h >= -6; --h) {
                BlockPos.Mutable blockpos$mutable1 = blockpos$mutable.add(0, h, 0).toMutable();
                BlockState base = reader.getBlockState(blockpos$mutable1.down());
                if (reader.isAirBlock(blockpos$mutable1) && base.isSolid() && !base.isIn(HabitatBlockTags.FAIRY_RING_GENERATION_BLACKLIST)) {
                    if (!bigFlag && rand.nextInt(10) == 0) {
                        ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM;

                        if (configuredfeature.generate(reader, generator, rand, blockpos$mutable1)) {
                            bigFlag = true;
                            generateTreasureRoom(reader, rand, blockpos$mutable1, mushroomProvider);
                            break;
                        }
                    }
                    reader.setBlockState(blockpos$mutable1, mushroomProvider.getBlockState(rand, blockpos$mutable1), 2);
                    break;
                }
            }
        }
        return true;
    }

    private void generateTreasureRoom(ISeedReader reader, Random rand, BlockPos pos, WeightedBlockStateProvider mushroomProvider) {
        int depth = 6 + rand.nextInt(3);
        BlockPos chestPos = pos.down(depth);
        BlockState chest = CompatHelper.checkQuarkFlag("variant_chests") ? HabitatBlocks.FAIRY_RING_MUSHROOM_CHEST.get().getDefaultState() : Blocks.CHEST.getDefaultState();
        BlockState stem = CompatHelper.checkMods("enhanced_mushrooms") ? HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM.get().getDefaultState(): HabitatBlocks.FAIRY_RING_MUSHROOM_STEM.get().getDefaultState();

        for (int i = 3; i < depth; i++)
            reader.setBlockState(chestPos.up(i), stem, 2);
        reader.setBlockState(chestPos.down(), stem, 2);

        for (Direction dir1 : new Direction[]{Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
            reader.destroyBlock(chestPos.offset(dir1), false);

            for (Direction dir2 : Direction.values()) {
                if (rand.nextInt(3) == 0) {
                    reader.destroyBlock(chestPos.offset(dir1).offset(dir2), false);
                    for (Direction dir3 : Direction.values()) {
                        BlockPos stemPos = chestPos.offset(dir1).offset(dir2).offset(dir3);
                        if (!stemPos.equals(chestPos) && reader.getBlockState(stemPos).isSolid()) {
                            reader.setBlockState(stemPos, stem, 2);
                        }
                    }
                }
                else
                    reader.setBlockState(chestPos.offset(dir1).offset(dir2), stem, 2);
            }
        }

        reader.setBlockState(chestPos, chest, 2);
        TileEntity tileentity = reader.getTileEntity(chestPos);
        if (tileentity instanceof ChestTileEntity)
            ((ChestTileEntity) tileentity).setLootTable(new ResourceLocation(Habitat.MOD_ID, "chests/fairy_ring_treasure"), rand.nextLong());
    }
}