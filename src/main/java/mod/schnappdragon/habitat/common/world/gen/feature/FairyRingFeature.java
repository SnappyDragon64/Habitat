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

import java.util.Arrays;
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
        int[] bigXZ = XZ_PAIRS[rand.nextInt(32)];

        for (int[] XZ : XZ_PAIRS) {
            for (int d = -1; d >= -6; d--) {
                BlockPos.Mutable blockpos$mutable = pos.add(XZ[0], d, XZ[1]).toMutable();
                BlockState base = reader.getBlockState(blockpos$mutable.down());
                if (reader.isAirBlock(blockpos$mutable) && base.isSolid() && !base.isIn(HabitatBlockTags.FAIRY_RING_GENERATION_BLACKLIST)) {
                    if (Arrays.equals(XZ, bigXZ)) {
                        ConfiguredFeature<?, ?> configuredfeature = HabitatConfiguredFeatures.HUGE_FAIRY_RING_MUSHROOM;

                        if (configuredfeature.generate(reader, generator, rand, blockpos$mutable)) {
                            generateTreasureRoom(reader, rand, blockpos$mutable, mushroomProvider);
                            break;
                        }
                    }
                    reader.setBlockState(blockpos$mutable, mushroomProvider.getBlockState(rand, blockpos$mutable), 2);
                    break;
                }
            }
        }
        return true;
    }

    private void generateTreasureRoom(ISeedReader reader, Random rand, BlockPos pos, WeightedBlockStateProvider mushroomProvider) {
        int depth = 5 + rand.nextInt(3);
        BlockPos.Mutable chestPos = pos.down(depth).toMutable();
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
                        BlockPos.Mutable stemPos = chestPos.offset(dir1).offset(dir2).offset(dir3).toMutable();
                        if (reader.getBlockState(stemPos).isSolid()) {
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