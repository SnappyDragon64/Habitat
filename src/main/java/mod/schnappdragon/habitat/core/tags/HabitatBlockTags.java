package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class HabitatBlockTags {
    public static final TagKey<Block> RAFFLESIA_PLACEABLE_ON = makeTag("rafflesia_placeable_on");
    public static final TagKey<Block> BALL_CACTUS_GROWS_ON = makeTag("ball_cactus_grows_on");
    public static final TagKey<Block> BALL_CACTUS_FLOWER_PLACEABLE_ON = makeTag("ball_cactus_flower_placeable_on");
    public static final TagKey<Block> PASSERINES_PERCHABLE_ON = makeTag("passerines_perchable_on");
    public static final TagKey<Block> PASSERINES_SPAWNABLE_ON = makeTag("passerines_spawnable_on");
    public static final TagKey<Block> EDELWEISS_SHRUB_PLACEABLE_ON = makeTag("edelweiss_shrub_placeable_on");
    public static final TagKey<Block> KABLOOM_PULP_BLOCK_DOES_NOT_STICK_TO = makeTag("kabloom_pulp_block_does_not_stick_to");
    public static final TagKey<Block> BALL_CACTUS_BLOCKS_CAN_SUSTAIN = makeTag("ball_cactus_blocks_can_sustain");

    private static TagKey<Block> makeTag(String id) {
        return BlockTags.create(new ResourceLocation(Habitat.MODID, id));
    }
}
