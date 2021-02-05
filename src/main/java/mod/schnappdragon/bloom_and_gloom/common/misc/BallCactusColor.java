package mod.schnappdragon.bloom_and_gloom.common.misc;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public enum BallCactusColor {
    PINK("pink"),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow");

    private final String color;

    private BallCactusColor(String colorIn) {
        this.color = colorIn;
    }

    public Block getBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(BloomAndGloom.MOD_ID, this.color + "_ball_cactus"));
    }

    public Block getFloweringBallCactus() {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(BloomAndGloom.MOD_ID, "flowering_" + this.color + "_ball_cactus"));
    }
}
