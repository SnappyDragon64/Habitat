package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class HabitatFlammables {
    public static void registerFlammables() {
        setFlammable(HabitatBlocks.RAFFLESIA.get(), 60, 100);

        setFlammable(HabitatBlocks.KABLOOM_BUSH.get(), 60, 100);
        setFlammable(HabitatBlocks.KABLOOM_FRUIT_PILE.get(), 30, 20);

        setFlammable(HabitatBlocks.SLIME_FERN.get(), 60, 100);
        setFlammable(HabitatBlocks.WALL_SLIME_FERN.get(), 60, 100);

        setFlammable(HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFlammable(HabitatBlocks.PINK_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFlammable(HabitatBlocks.RED_BALL_CACTUS_FLOWER.get(), 60, 100);
        setFlammable(HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER.get(), 60, 100);

        setFlammable(HabitatBlocks.ENHANCED_FAIRY_RING_MUSHROOM_STEM.get(), 5, 5);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_HYPHAE.get(), 5, 5);
        setFlammable(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_STEM.get(), 5, 5);
        setFlammable(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_HYPHAE.get(), 5, 5);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_PLANKS.get(), 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_SLAB.get(), 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_STAIRS.get(), 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE.get(), 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_FENCE_GATE.get(), 5, 20);
        setFlammable(HabitatBlocks.FAIRY_RING_MUSHROOM_POST.get(), 5, 20);
        setFlammable(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_POST.get(), 5, 20);

        setFlammable(HabitatBlocks.EDELWEISS.get(), 60, 100);
    }

    private static void setFlammable(Block block, int flameOdds, int burnOdds) {
        ((FireBlock) Blocks.FIRE).setFlammable(block, flameOdds, burnOdds);
    }
}