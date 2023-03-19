package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;

import java.util.function.Supplier;

public class HabitatFlowerPots {
    public static void addPlantsToFlowerPot() {
        addPlantToFlowerPot("rafflesia", HabitatBlocks.POTTED_RAFFLESIA);
        addPlantToFlowerPot("kabloom_bush", HabitatBlocks.POTTED_KABLOOM_BUSH);
        addPlantToFlowerPot("slime_fern", HabitatBlocks.POTTED_KABLOOM_BUSH);
        addPlantToFlowerPot("orange_ball_cactus_flower", HabitatBlocks.POTTED_ORANGE_BALL_CACTUS_FLOWER);
        addPlantToFlowerPot("pink_ball_cactus_flower", HabitatBlocks.POTTED_PINK_BALL_CACTUS_FLOWER);
        addPlantToFlowerPot("red_ball_cactus_flower", HabitatBlocks.POTTED_RED_BALL_CACTUS_FLOWER);
        addPlantToFlowerPot("yellow_ball_cactus_flower", HabitatBlocks.POTTED_YELLOW_BALL_CACTUS_FLOWER);
        addPlantToFlowerPot("orange_ball_cactus", HabitatBlocks.POTTED_ORANGE_BALL_CACTUS);
        addPlantToFlowerPot("pink_ball_cactus", HabitatBlocks.POTTED_PINK_BALL_CACTUS);
        addPlantToFlowerPot("red_ball_cactus", HabitatBlocks.POTTED_RED_BALL_CACTUS);
        addPlantToFlowerPot("yellow_ball_cactus", HabitatBlocks.POTTED_YELLOW_BALL_CACTUS);
        addPlantToFlowerPot("fairy_ring_mushroom", HabitatBlocks.POTTED_FAIRY_RING_MUSHROOM);
        addPlantToFlowerPot("edelweiss", HabitatBlocks.POTTED_EDELWEISS);
    }

    private static void addPlantToFlowerPot(String id, Supplier<Block> flowerPot) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(Habitat.MODID, id), flowerPot);
    }
}
