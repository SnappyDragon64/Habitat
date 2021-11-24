package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;

public class HabitatItemTags {
    public static final Named<Item> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    public static final Named<Item> BALL_CACTI = makeTag("ball_cacti");
    public static final Named<Item> FAIRY_RING_MUSHROOM_STEMS = makeTag("fairy_ring_mushroom_stems");
    public static final Named<Item> POOKA_FOOD = makeTag("pooka_food");
    public static final Named<Item> PASSERINE_FOOD = makeTag("passerine_food");

    private static Named<Item> makeTag(String id) {
        return ItemTags.bind(Habitat.MODID + ":" + id);
    }
}
