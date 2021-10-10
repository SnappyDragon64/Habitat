package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;

public class HabitatBlockTags {
    public static final INamedTag<Block> RAFFLESIA_PLANTABLE_ON = makeTag("rafflesia_plantable_on");
    public static final INamedTag<Block> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    public static final INamedTag<Block> GROWING_BALL_CACTI = makeTag("growing_ball_cacti");
    public static final INamedTag<Block> BALL_CACTI = makeTag("ball_cacti");
    public static final INamedTag<Block> FLOWERING_BALL_CACTI = makeTag("flowering_ball_cacti");
    public static final INamedTag<Block> BALL_CACTUS_PLANTABLE_ON = makeTag("ball_cactus_plantable_on");
    public static final INamedTag<Block> BALL_CACTUS_FLOWER_PLACEABLE_ON = makeTag("ball_cactus_flower_placeable_on");
    public static final INamedTag<Block> BEEHIVES = makeTag("beehives");
    public static final INamedTag<Block> FAIRY_RING_GENERATION_BLACKLIST = makeTag("fairy_ring_generation_blacklist");
    public static final INamedTag<Block> FAIRY_RING_MUSHROOM_STEMS = makeTag("fairy_ring_mushroom_stems");

    private static INamedTag<Block> makeTag(String id) {
        return BlockTags.makeWrapperTag(Habitat.MOD_ID + ":" + id);
    }
}
