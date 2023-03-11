package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class HabitatBlockTags {
    public static final TagKey<Block> RAFFLESIA_PLANTABLE_ON = makeTag("rafflesia_plantable_on");
    // public static final TagKey<Block> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    // public static final TagKey<Block> GROWING_BALL_CACTI = makeTag("growing_ball_cacti");
    // public static final TagKey<Block> BALL_CACTI = makeTag("ball_cacti");
    // public static final TagKey<Block> FLOWERING_BALL_CACTI = makeTag("flowering_ball_cacti");
    public static final TagKey<Block> BALL_CACTUS_GROWS_ON = makeTag("ball_cactus_grows_on");
    public static final TagKey<Block> BALL_CACTUS_FLOWER_PLACEABLE = makeTag("ball_cactus_flower_placeable");
    // public static final TagKey<Block> FAIRY_RING_MUSHROOM_STEMS = makeTag("fairy_ring_mushroom_stems");
    public static final TagKey<Block> PASSERINES_PERCHABLE_ON = makeTag("passerines_perchable_on");
    public static final TagKey<Block> PASSERINES_SPAWNABLE_ON = makeTag("passerines_spawnable_on");
    public static final TagKey<Block> EDELWEISS_SHRUB_PLANTABLE_ON = makeTag("edelweiss_shrub_plantable_on");
    public static final TagKey<Block> EDELWEISS_PLANTABLE_ON = makeTag("edelweiss_plantable_on");
    // public static final TagKey<Block> BALL_CACTUS_BLOCKS = makeTag("ball_cactus_blocks");
    // public static final TagKey<Block> FLOWERING_BALL_CACTUS_BLOCKS = makeTag("flowering_ball_cactus_blocks");
    public static final TagKey<Block> POOKA_SPAWNABLE_ON = makeTag("pooka_spawnable_on");

    private static TagKey<Block> makeTag(String id) {
        return BlockTags.create(new ResourceLocation(Habitat.MODID, id));
    }
}
