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
                    this.setBlockState(reader, blockpos$mutable, mushroomProvider.getBlockState(rand, blockpos$mutable));
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

        reader.destroyBlock(chestPos.up(), false);
        for (int i = 2; i < depth; i++)
            this.setBlockState(reader, chestPos.up(i), stem);
        this.setBlockState(reader, chestPos.down(), stem);

        for (int i = -1; i <= 2; i++) {
            BlockPos.Mutable stemPos = chestPos.up(i).toMutable();
            for (Direction dir : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
                this.setBlockState(reader, stemPos.offset(dir), stem);
        }

        for (int i = -1; i <= 1; i += 2) {
            for (int k = -1; k <= 1; k += 2) {
                for (int j = 0; j <= 1; j++)
                    this.setBlockState(reader, chestPos.add(i, j, k), stem);

                for (int j = -1; j <= 2; j += 3) {
                    if (rand.nextBoolean())
                        this.setBlockState(reader, chestPos.add(i, j, k), stem);
                }
            }
        }

        this.setBlockState(reader, chestPos, chest);
        TileEntity tileentity = reader.getTileEntity(chestPos);
        if (tileentity instanceof ChestTileEntity)
            ((ChestTileEntity) tileentity).setLootTable(new ResourceLocation(Habitat.MOD_ID, "chests/fairy_ring_treasure"), rand.nextLong());
    }
}