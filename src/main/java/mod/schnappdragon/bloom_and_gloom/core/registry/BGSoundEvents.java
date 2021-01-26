package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BloomAndGloom.MOD_ID);

    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_SPEW = register("block.rafflesia.spew");
    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_POP = register("block.rafflesia.pop");
    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_SLURP = register("block.rafflesia.slurp");

    public static final RegistryObject<SoundEvent> BLOCK_KABLOOM_BUSH_RUSTLE = register("block.kabloom_bush.rustle");
    public static final RegistryObject<SoundEvent> BLOCK_KABLOOM_BUSH_SHEAR = register("block.kabloom_bush.shear");

    public static final RegistryObject<SoundEvent> BLOCK_SLIME_FERN_DROP = register("block.slime_fern.drip");

    public static final RegistryObject<SoundEvent> ENTITY_KABLOOM_FRUIT_EXPLODE = register("entity.kabloom_fruit.explode");

    private static RegistryObject<SoundEvent> register(String name)
    {
        return SOUND_EVENTS.register(name, () -> new SoundEvent((new ResourceLocation(BloomAndGloom.MOD_ID, name))));
    }
}
