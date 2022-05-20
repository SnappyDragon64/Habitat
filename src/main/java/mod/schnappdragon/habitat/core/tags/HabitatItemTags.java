package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class HabitatItemTags {
    public static final TagKey<Item> BALL_CACTUS_FLOWERS = makeTag("ball_cactus_flowers");
    public static final TagKey<Item> BALL_CACTI = makeTag("ball_cacti");
    public static final TagKey<Item> FAIRY_RING_MUSHROOM_STEMS = makeTag("fairy_ring_mushroom_stems");
    public static final TagKey<Item> POOKA_FOOD = makeTag("pooka_food");
    public static final TagKey<Item> PASSERINE_FOOD = makeTag("passerine_food");

    private static TagKey<Item> makeTag(String id) {
        return ItemTags.create(new ResourceLocation(Habitat.MODID, id));
    }
}
