package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.level.block.Block;

public class HabitatBlockTags {
    public static final Named<Block> RAFFLESIA_PLANTABLE_ON = makeTag("rafflesia_plantable_on");
    public static final Named<Block> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    public static final Named<Block> GROWING_BALL_CACTI = makeTag("growing_ball_cacti");
    public static final Named<Block> BALL_CACTI = makeTag("ball_cacti");
    public static final Named<Block> FLOWERING_BALL_CACTI = makeTag("flowering_ball_cacti");
    public static final Named<Block> BALL_CACTUS_GROWS_ON = makeTag("ball_cactus_grows_on");
    public static final Named<Block> BALL_CACTUS_FLOWER_PLACEABLE = makeTag("ball_cactus_flower_placeable");
    public static final Named<Block> FAIRY_RING_MUSHROOM_STEMS = makeTag("fairy_ring_mushroom_stems");
    public static final Named<Block> PASSERINES_PERCHABLE_ON = makeTag("passerines_perchable_on");
    public static final Named<Block> PASSERINES_SPAWNABLE_ON = makeTag("passerines_spawnable_on");

    private static Named<Block> makeTag(String id) {
        return BlockTags.bind(Habitat.MODID + ":" + id);
    }
}
