package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class HabitatPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Habitat.MODID);

    public static final RegistryObject<Potion> BLAST_ENDURANCE = register("blast_endurance", HabitatEffects.BLAST_ENDURANCE, 3600);
    public static final RegistryObject<Potion> LONG_BLAST_ENDURANCE = register("long_blast_endurance", HabitatEffects.BLAST_ENDURANCE, 9600);
    public static final RegistryObject<Potion> STRONG_BLAST_ENDURANCE = register("strong_blast_endurance", HabitatEffects.BLAST_ENDURANCE, 1800, 1);

    public static final RegistryObject<Potion> PRICKLING = register("prickling", HabitatEffects.PRICKLING, 3600);
    public static final RegistryObject<Potion> LONG_PRICKLING = register("long_prickling", HabitatEffects.PRICKLING, 9600);
    public static final RegistryObject<Potion> STRONG_PRICKLING = register("strong_prickling", HabitatEffects.PRICKLING, 1800, 1);

    private static RegistryObject<Potion> register(String id, Supplier<MobEffect> effect, int duration) {
        return POTIONS.register(id, () -> new Potion(new MobEffectInstance(effect.get(), duration)));
    }

    private static RegistryObject<Potion> register(String id, Supplier<MobEffect> effect, int duration, int amplifier) {
        return POTIONS.register(id, () -> new Potion(new MobEffectInstance(effect.get(), duration, amplifier)));
    }
}