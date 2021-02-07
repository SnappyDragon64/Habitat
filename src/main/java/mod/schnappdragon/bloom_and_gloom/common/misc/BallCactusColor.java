package mod.schnappdragon.bloom_and_gloom.common.misc;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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

    public Block getBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(BloomAndGloom.MOD_ID, this.color + "_ball_cactus"));
    }

    public Block getGrowingBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(BloomAndGloom.MOD_ID, "growing_" + this.color + "_ball_cactus"));
    }

    public Item getFlower() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(BloomAndGloom.MOD_ID, this.color + "_ball_cactus_flower"));
    }
}
