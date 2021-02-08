package mod.schnappdragon.bloom_and_gloom.core.tags;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;

public class BGItemTags {
    public static final INamedTag<Item> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    public static final INamedTag<Item> BALL_CACTI = makeTag("ball_cacti");

    private static INamedTag<Item> makeTag(String id) {
        return ItemTags.makeWrapperTag(BloomAndGloom.MOD_ID + ":" + id);
    }
}
