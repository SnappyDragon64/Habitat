package mod.schnappdragon.habitat.core.misc;

import mod.schnappdragon.habitat.core.registry.HabitatEntityTypes;
import mod.schnappdragon.habitat.core.registry.HabitatSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;

public class HabitatParrotImitationSounds {
    public static void registerParrotImitationSounds() {
        addImitationSound(HabitatEntityTypes.POOKA.get(), HabitatSoundEvents.PARROT_IMITATE_POOKA.get());
    }

    private static void addImitationSound(EntityType<?> type, SoundEvent sound) {
        Parrot.MOB_SOUND_MAP.put(type, sound);
    }
}
