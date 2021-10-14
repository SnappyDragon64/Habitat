package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

public class HabitatFireInfo {
    public static void registerFireInfo() {
        setFireInfo(HabitatBlocks.RAFFLESIA.get(), 60, 100);

        setFireInfo(HabitatBlocks.KABLOOM_BUSH.get(), 60, 100);
        setFireInfo(HabitatBlocks.KABLOOM_FRUIT_PILE.get(), 30, 20);
        setFireInfo(HabitatBlocks.KABLOOM_PULP_TUB.get(), 5, 20);

        setFireInfo(HabitatBlocks.SLIME_FERN.get(), 60, 100);
        setFireInfo(HabitatBlocks.WALL_SLIME_FERN.get(), 60, 100);

        setFireInfo(HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFireInfo(HabitatBlocks.PINK_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFireInfo(HabitatBlocks.RED_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFireInfo(HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER.get(), 60, 100);

        setFireInfo(HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM.get(), 5, 5);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_HYPHAE.get(), 5, 5);
        setFireInfo(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_STEM.get(), 5, 5);
        setFireInfo(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE.get(), 5, 5);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_PLANKS.get(), 5, 20);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_SLAB.get(), 5, 20);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_STAIRS.get(), 5, 20);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE.get(), 5, 20);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE_GATE.get(), 5, 20);
        setFireInfo(HabitatBlocks.FAIRY_RING_MUSHROOM_POST.get(), 5, 20);
        setFireInfo(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_POST.get(), 5, 20);
    }

    private static void setFireInfo(Block block, int encouragement, int flammability) {
        ((FireBlock) Blocks.FIRE).setFireInfo(block, encouragement, flammability);
    }
}