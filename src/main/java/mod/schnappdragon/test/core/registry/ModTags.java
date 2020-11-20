package mod.schnappdragon.test.core.registry;

import mod.schnappdragon.test.core.Test;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;

public class ModTags {

    /*
     * Block Tags
     */

    public static final INamedTag<Block> RAFFLESIA_PLANTABLE_ON = BlockTags.makeWrapperTag(Test.MOD_ID + ":rafflesia_plantable_on");
}
