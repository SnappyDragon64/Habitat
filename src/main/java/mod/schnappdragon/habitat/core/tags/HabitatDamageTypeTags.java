package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class HabitatDamageTypeTags {
    public static final TagKey<DamageType> AVOIDS_PRICKLING = tag("avoids_prickling");
    public static final TagKey<DamageType> PRICKLING_IMMUNE_TO = tag("prickling_immune_to");

    private static TagKey<DamageType> tag(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Habitat.MODID, id));
    }
}
