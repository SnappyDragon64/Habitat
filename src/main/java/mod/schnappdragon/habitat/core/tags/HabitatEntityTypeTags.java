package mod.schnappdragon.habitat.core.tags;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class HabitatEntityTypeTags {
    public static final TagKey<EntityType<?>> POLLINATORS = makeTag("pollinators");

    private static TagKey<EntityType<?>> makeTag(String id) {
        return EntityTypeTags.create(new ResourceLocation(Habitat.MODID, id).toString());
    }
}