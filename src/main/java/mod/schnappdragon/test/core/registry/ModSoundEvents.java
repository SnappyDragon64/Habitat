package mod.schnappdragon.test.core.registry;

import mod.schnappdragon.test.core.Test;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Test.MOD_ID);

    public static final RegistryObject<SoundEvent> RAFFLESIA_CLOUD = SOUND_EVENTS.register("rafflesia_cloud", () -> getSoundEvent("rafflesia_cloud"));
    public static final RegistryObject<SoundEvent> RAFFLESIA_POP = SOUND_EVENTS.register("rafflesia_pop", () -> getSoundEvent("rafflesia_pop"));
    public static final RegistryObject<SoundEvent> RAFFLESIA_STEW = SOUND_EVENTS.register("rafflesia_stew", () -> getSoundEvent("rafflesia_stew"));

    private static SoundEvent getSoundEvent(String soundPath)
    {
        return new SoundEvent(new ResourceLocation(Test.MOD_ID, soundPath));
    }
}
