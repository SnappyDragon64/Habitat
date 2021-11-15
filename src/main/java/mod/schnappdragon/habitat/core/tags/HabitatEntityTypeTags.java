package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.entity.EntityType;

public class HabitatEntityTypeTags {
    public static final Named<EntityType<?>> POOKA_ATTACK_TARGETS = makeTag("pooka_attack_targets");
    public static final Named<EntityType<?>> PACIFIED_POOKA_SCARED_BY = makeTag("pacified_pooka_scared_by");

    private static Named<EntityType<?>> makeTag(String id) {
        return EntityTypeTags.bind(Habitat.MODID + ":" + id);
    }
}
