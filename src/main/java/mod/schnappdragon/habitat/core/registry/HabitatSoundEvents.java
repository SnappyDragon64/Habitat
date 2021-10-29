package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Habitat.MODID);

    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_SPEW = register("block.rafflesia.spew");
    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_POP = register("block.rafflesia.pop");
    public static final RegistryObject<SoundEvent> BLOCK_RAFFLESIA_SLURP = register("block.rafflesia.slurp");

    public static final RegistryObject<SoundEvent> BLOCK_KABLOOM_BUSH_RUSTLE = register("block.kabloom_bush.rustle");
    public static final RegistryObject<SoundEvent> BLOCK_KABLOOM_BUSH_SHEAR = register("block.kabloom_bush.shear");

    public static final RegistryObject<SoundEvent> BLOCK_SLIME_FERN_DROP = register("block.slime_fern.drip");

    public static final RegistryObject<SoundEvent> BLOCK_FLOWERING_BALL_CACTUS_SHEAR = register("block.flowering_ball_cactus.shear");

    public static final RegistryObject<SoundEvent> BLOCK_FAIRY_RING_MUSHROOM_SHEAR = register("block.fairy_ring_mushroom.shear");
    public static final RegistryObject<SoundEvent> BLOCK_FAIRY_RING_MUSHROOM_DUST = register("block.fairy_ring_mushroom.dust");

    public static final RegistryObject<SoundEvent> ENTITY_KABLOOM_FRUIT_THROW = register("entity.kabloom_fruit.throw");
    public static final RegistryObject<SoundEvent> ENTITY_KABLOOM_FRUIT_EXPLODE = register("entity.kabloom_fruit.explode");

    public static final RegistryObject<SoundEvent> ENTITY_POOKA_AMBIENT = register("entity.pooka.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_POOKA_ATTACK = register("entity.pooka.attack");
    public static final RegistryObject<SoundEvent> ENTITY_POOKA_DEATH = register("entity.pooka.death");
    public static final RegistryObject<SoundEvent> ENTITY_POOKA_HURT = register("entity.pooka.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_POOKA_JUMP = register("entity.pooka.jump");
    public static final RegistryObject<SoundEvent> ENTITY_POOKA_SHEAR = register("entity.pooka.shear");
    public static final RegistryObject<SoundEvent> ENTITY_POOKA_PACIFY = register("entity.pooka.pacify");

    public static final RegistryObject<SoundEvent> ENTITY_RABBIT_CONVERTED_TO_POOKA = register("entity.rabbit.converted_to_pooka");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent((new ResourceLocation(Habitat.MODID, name))));
    }
}