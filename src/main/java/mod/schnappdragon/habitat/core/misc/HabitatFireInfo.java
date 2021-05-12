package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class HabitatFireInfo {
    public static void registerFireInfo() {
        setFireInfo(HabitatBlocks.RAFFLESIA.get(), 60, 100);
        setFireInfo(HabitatBlocks.KABLOOM_BUSH.get(), 60, 100);

        setFireInfo(HabitatBlocks.SLIME_FERN.get(), 60, 100);
        setFireInfo(HabitatBlocks.WALL_SLIME_FERN.get(), 60, 100);

        setFireInfo(HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFireInfo(HabitatBlocks.PINK_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFireInfo(HabitatBlocks.RED_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFireInfo(HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER.get(), 60, 100);
    }

    private static void setFireInfo(Block block, int encouragement, int flammability) {
        ((FireBlock) Blocks.FIRE).setFireInfo(block, encouragement, flammability);
    }
}