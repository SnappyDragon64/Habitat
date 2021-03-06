package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;

public class HabitatItemTags {
    public static final INamedTag<Item> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    public static final INamedTag<Item> BALL_CACTI = makeTag("ball_cacti");

    private static INamedTag<Item> makeTag(String id) {
        return ItemTags.makeWrapperTag(Habitat.MOD_ID + ":" + id);
    }
}
