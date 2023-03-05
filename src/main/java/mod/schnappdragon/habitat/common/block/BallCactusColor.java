package mod.schnappdragon.habitat.common.block;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public enum BallCactusColor {
    PINK("pink"),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow");

    private final String color;

    BallCactusColor(String colorIn) {
        this.color = colorIn;
    }

    public Item getFlower() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(Habitat.MODID, this.color + "_ball_cactus_flower"));
    }

    public Block getGrowingBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Habitat.MODID, "growing_" + this.color + "_ball_cactus"));
    }

    public Block getBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Habitat.MODID, this.color + "_ball_cactus"));
    }

    public Block getFloweringBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Habitat.MODID, "flowering_" + this.color + "_ball_cactus"));
    }

    public ResourceLocation getHugeBallCactus() {
        return new ResourceLocation(Habitat.MODID, "huge_" + this.color + "_ball_cactus");
    }
}