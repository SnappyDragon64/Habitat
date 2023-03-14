package mod.schnappdragon.habitat.core.tags;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatMobEffectTags {
    public static final TagKey<MobEffect> FAIRY_RING_MUSHROOM_EFFECTS = makeTag("fairy_ring_mushroom_effects");

    private static TagKey<MobEffect> makeTag(String id) {
        return ForgeRegistries.MOB_EFFECTS.tags().createTagKey(new ResourceLocation(Habitat.MODID, id));
    }
}
