package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class HabitatItemTags {
    public static final TagKey<Item> POOKA_FOOD = tag("pooka_food");
    public static final TagKey<Item> PASSERINE_FOOD = tag("passerine_food");

    private static TagKey<Item> tag(String id) {
        return ItemTags.create(new ResourceLocation(Habitat.MODID, id));
    }
}
