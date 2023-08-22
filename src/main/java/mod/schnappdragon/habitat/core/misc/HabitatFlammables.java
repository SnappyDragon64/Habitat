package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import java.util.function.Supplier;

public class HabitatFlammables {
    public static void registerFlammables() {
        setFlammable(HabitatBlocks.RAFFLESIA, 60, 100);

        setFlammable(HabitatBlocks.KABLOOM_BUSH, 60, 100);
        setFlammable(HabitatBlocks.KABLOOM_FRUIT_PILE, 30, 20);

        setFlammable(HabitatBlocks.SLIME_FERN, 60, 100);

        setFlammable(HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER, 60, 100);
        setFlammable(HabitatBlocks.PINK_BALL_CACTUS_FLOWER, 60, 100);
        setFlammable(HabitatBlocks.RED_BALL_CACTUS_FLOWER, 60, 100);
        setFlammable(HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER, 60, 100);
        setFlammable(HabitatBlocks.GROWING_ORANGE_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.GROWING_PINK_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.GROWING_RED_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.GROWING_YELLOW_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.ORANGE_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.PINK_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.RED_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.YELLOW_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_PINK_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_RED_BALL_CACTUS, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS, 5, 60);

        setFlammable(HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM, 5, 5);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_HYPHAE, 5, 5);
        setFlammable(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_STEM, 5, 5);
        setFlammable(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE, 5, 5);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_PLANKS, 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_SLAB, 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_STAIRS, 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE, 5, 20);
        //setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE_GATE, 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_POST, 5, 20);
        setFlammable(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_POST, 5, 20);

        setFlammable(HabitatBlocks.EDELWEISS, 60, 100);

        setFlammable(HabitatBlocks.BALL_CACTUS_BLOCK, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS_BLOCK, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_PINK_BALL_CACTUS_BLOCK, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_RED_BALL_CACTUS_BLOCK, 5, 60);
        setFlammable(HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS_BLOCK, 5, 60);
        setFlammable(HabitatBlocks.DRIED_BALL_CACTUS_BLOCK, 5, 60);

        setFlammable(HabitatBlocks.PURPLE_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.RED_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.WHITE_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.YELLOW_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.TALL_PURPLE_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.TALL_RED_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.TALL_WHITE_ANTHURIUM, 60, 100);
        setFlammable(HabitatBlocks.TALL_YELLOW_ANTHURIUM, 60, 100);

        setFlammable(HabitatBlocks.BLOOMING_DREADBUD, 60, 100);
        setFlammable(HabitatBlocks.FADING_DREADBUD, 60, 100);
        setFlammable(HabitatBlocks.UNNERVING_DREADBUD, 60, 100);
        setFlammable(HabitatBlocks.HAUNTING_DREADBUD, 60, 100);
    }

    private static void setFlammable(Supplier<Block> block, int flameOdds, int burnOdds) {
        ((FireBlock) Blocks.FIRE).setFlammable(block.get(), flameOdds, burnOdds);
    }
}