package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Habitat.MODID);

    public static final RegistryObject<SoundEvent> RAFFLESIA_SPEW = register("block.rafflesia.spew");
    public static final RegistryObject<SoundEvent> RAFFLESIA_POP = register("block.rafflesia.pop");
    public static final RegistryObject<SoundEvent> RAFFLESIA_SLURP = register("block.rafflesia.slurp");
    public static final RegistryObject<SoundEvent> RAFFLESIA_FILL_BOWL = register("block.rafflesia.fill_bowl");

    public static final RegistryObject<SoundEvent> KABLOOM_BUSH_RUSTLE = register("block.kabloom_bush.rustle");
    public static final RegistryObject<SoundEvent> KABLOOM_BUSH_SHEAR = register("block.kabloom_bush.shear");

    public static final RegistryObject<SoundEvent> SLIME_FERN_DROP = register("block.slime_fern.drip");

    public static final RegistryObject<SoundEvent> FLOWERING_BALL_CACTUS_SHEAR = register("block.flowering_ball_cactus.shear");

    public static final RegistryObject<SoundEvent> FAIRY_RING_MUSHROOM_SHEAR = register("block.fairy_ring_mushroom.shear");
    public static final RegistryObject<SoundEvent> FAIRY_RING_MUSHROOM_DUST = register("block.fairy_ring_mushroom.dust");

    public static final RegistryObject<SoundEvent> KABLOOM_FRUIT_THROW = register("entity.kabloom_fruit.throw");
    public static final RegistryObject<SoundEvent> KABLOOM_FRUIT_EXPLODE = register("entity.kabloom_fruit.explode");

    public static final RegistryObject<SoundEvent> POOKA_AMBIENT = register("entity.pooka.ambient");
    public static final RegistryObject<SoundEvent> POOKA_ATTACK = register("entity.pooka.attack");
    public static final RegistryObject<SoundEvent> POOKA_DEATH = register("entity.pooka.death");
    public static final RegistryObject<SoundEvent> POOKA_EAT = register("entity.pooka.eat");
    public static final RegistryObject<SoundEvent> POOKA_HURT = register("entity.pooka.hurt");
    public static final RegistryObject<SoundEvent> POOKA_JUMP = register("entity.pooka.jump");
    public static final RegistryObject<SoundEvent> POOKA_PACIFY = register("entity.pooka.pacify");
    public static final RegistryObject<SoundEvent> POOKA_SHEAR = register("entity.pooka.shear");
    public static final RegistryObject<SoundEvent> RABBIT_CONVERTED_TO_POOKA = register("entity.rabbit.converted_to_pooka");
    public static final RegistryObject<SoundEvent> PARROT_IMITATE_POOKA = register("entity.parrot.imitate.pooka");

    public static final RegistryObject<SoundEvent> PASSERINE_AMBIENT = register("entity.passerine.ambient");
    public static final RegistryObject<SoundEvent> PASSERINE_DEATH = register("entity.passerine.death");
    public static final RegistryObject<SoundEvent> PASSERINE_FLAP = register("entity.passerine.flap");
    public static final RegistryObject<SoundEvent> PASSERINE_HURT = register("entity.passerine.hurt");
    public static final RegistryObject<SoundEvent> PASSERINE_STEP = register("entity.passerine.step");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent((new ResourceLocation(Habitat.MODID, name))));
    }
}