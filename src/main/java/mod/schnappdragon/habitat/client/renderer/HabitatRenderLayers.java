package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.core.registry.HabitatBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class HabitatRenderLayers {
    public static void registerRenderLayers() {
        setRenderLayer(HabitatBlocks.RAFFLESIA.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_RAFFLESIA.get(), RenderType.cutout());

        setRenderLayer(HabitatBlocks.KABLOOM_BUSH.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_KABLOOM_BUSH.get(), RenderType.cutout());

        setRenderLayer(HabitatBlocks.SLIME_FERN.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.WALL_SLIME_FERN.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_SLIME_FERN.get(), RenderType.cutout());

        setRenderLayer(HabitatBlocks.ORANGE_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.PINK_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.RED_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.YELLOW_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FLOWERING_ORANGE_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FLOWERING_PINK_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FLOWERING_RED_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FLOWERING_YELLOW_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_ORANGE_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_PINK_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_RED_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_YELLOW_BALL_CACTUS_FLOWER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_ORANGE_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_PINK_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_RED_BALL_CACTUS.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_YELLOW_BALL_CACTUS.get(), RenderType.cutout());

        setRenderLayer(HabitatBlocks.FAIRY_RING_MUSHROOM.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.POTTED_FAIRY_RING_MUSHROOM.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FAIRY_SPORE_LANTERN.get(), RenderType.cutout());

        setRenderLayer(HabitatBlocks.FAIRY_RING_MUSHROOM_TRAPDOOR.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FAIRY_RING_MUSHROOM_DOOR.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FAIRY_RING_MUSHROOM_LADDER.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.FAIRY_RING_MUSHROOM_POST.get(), RenderType.cutout());
        setRenderLayer(HabitatBlocks.STRIPPED_FAIRY_RING_MUSHROOM_POST.get(), RenderType.cutout());
    }

    private static void setRenderLayer(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }
}