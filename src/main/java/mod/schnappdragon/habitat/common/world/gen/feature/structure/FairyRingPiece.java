package mod.schnappdragon.habitat.common.world.gen.feature.structure;

import java.util.Random;

import mod.schnappdragon.habitat.common.block.FairyRingMushroomBlock;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import mod.schnappdragon.habitat.core.registry.HabitatStructurePieceTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class FairyRingPiece extends ScatteredStructurePiece {
    private static final int[][] XZ_PAIRS = {
            {2, 5}, {2, 6}, {2, 7}, {2, 8},
            {11, 5}, {11, 6}, {11, 7}, {11, 8},
            {5, 2}, {6, 2}, {7, 2}, {8, 2},
            {5, 11}, {6, 11}, {7, 11}, {8, 11},
            {3, 3}, {3, 4}, {3, 9}, {3, 10},
            {10, 3}, {10, 4}, {10, 9}, {10,10},
            {4, 3}, {4, 10}, {9, 3}, {9, 10}
    };

    public FairyRingPiece(Random random, int x, int z) {
        super(HabitatStructurePieceTypes.FR, random, x, 64, z, 14, 4, 4);
    }

    public FairyRingPiece(TemplateManager templateManager, CompoundNBT nbt) {
        super(HabitatStructurePieceTypes.FR, nbt);
    }

    public boolean func_230383_a_(ISeedReader reader, StructureManager structureManager, ChunkGenerator generator, Random random, MutableBoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        if (!this.isInsideBounds(reader, boundingBox, 0))
            return false;

        for (int[] XZ : XZ_PAIRS) {
            BlockState state = getBlockState(random);
            for (int j = 4; j > -4; j--) {
                BlockPos blockpos = new BlockPos(this.getXWithOffset(XZ[0], XZ[1]), this.getYWithOffset(j), this.getZWithOffset(XZ[0], XZ[1]));
                if (reader.isAirBlock(blockpos) && reader.getBlockState(blockpos.down()).isSolid()) {
                    this.setBlockState(reader, Blocks.YELLOW_WOOL.getDefaultState(), XZ[0], j, XZ[1], boundingBox);
                    reader.setBlockState(blockpos, Blocks.GREEN_WOOL.getDefaultState(), 2);
                }
            }
            this.setBlockState(reader, Blocks.RED_WOOL.getDefaultState(), XZ[0], 0, XZ[1], boundingBox);
        }
        return true;
    }

    public BlockState getBlockState(Random rand) {
        BlockState mushroom = HabitatBlocks.FAIRY_RING_MUSHROOM.get().getDefaultState();
        int n = rand.nextInt(9);

        if (n < 3)
            return mushroom.with(FairyRingMushroomBlock.MUSHROOMS, 4);
        else if (n < 6)
            return mushroom.with(FairyRingMushroomBlock.MUSHROOMS, 3);
        else if (n < 8)
            return mushroom.with(FairyRingMushroomBlock.MUSHROOMS, 2);
        else
            return mushroom;
    }
}