package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<SoundEvent> RAFFLESIA_SPEW = SOUND_EVENTS.register("rafflesia_spew", () -> getSoundEvent("rafflesia_spew"));
    public static final RegistryObject<SoundEvent> RAFFLESIA_POP = SOUND_EVENTS.register("rafflesia_pop", () -> getSoundEvent("rafflesia_pop"));
    public static final RegistryObject<SoundEvent> RAFFLESIA_SLURP = SOUND_EVENTS.register("rafflesia_slurp", () -> getSoundEvent("rafflesia_slurp"));

    private static SoundEvent getSoundEvent(String soundPath)
    {
        return new SoundEvent(new ResourceLocation(BloomAndGloom.MOD_ID, soundPath));
    }
}