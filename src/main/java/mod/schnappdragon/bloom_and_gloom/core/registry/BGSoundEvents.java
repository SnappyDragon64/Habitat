package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_SPEW = SOUND_EVENTS.register("block_rafflesia_spew", () -> getSoundEvent("block_rafflesia_spew"));
    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_POP = SOUND_EVENTS.register("block_rafflesia_pop", () -> getSoundEvent("block_rafflesia_pop"));
    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_SLURP = SOUND_EVENTS.register("block_rafflesia_slurp", () -> getSoundEvent("block_rafflesia_slurp"));

    public static final RegistryObject<SoundEvent> BLOCK_KABLOOM_BUSH_RUSTLE = SOUND_EVENTS.register("block_kabloom_bush_rustle", () -> getSoundEvent("block_kabloom_bush_rustle"));
    public static final RegistryObject<SoundEvent> BLOCK_KABLOOM_BUSH_SHEAR = SOUND_EVENTS.register("block_kabloom_bush_shear", () -> getSoundEvent("block_kabloom_bush_shear"));

    public static final RegistryObject<SoundEvent> ENTITY_KABLOOM_FRUIT_EXPLODE = SOUND_EVENTS.register("entity_kabloom_fruit_explode", () -> getSoundEvent("entity_kabloom_fruit_explode"));

    private static SoundEvent getSoundEvent(String soundPath)
    {
        return new SoundEvent(new ResourceLocation(BloomAndGloom.MOD_ID, soundPath));
    }
}
