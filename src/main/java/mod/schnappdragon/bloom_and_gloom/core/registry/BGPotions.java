package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BGPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, BloomAndGloom.MOD_ID);

    public static final RegistryObject<Potion> BLAST_ENDURANCE = register("blast_endurance", BGEffects.BLAST_ENDURANCE, 3600);
    public static final RegistryObject<Potion> LONG_BLAST_ENDURANCE = register("long_blast_endurance", BGEffects.BLAST_ENDURANCE, 9600);
    public static final RegistryObject<Potion> STRONG_BLAST_ENDURANCE = register("strong_blast_endurance", BGEffects.BLAST_ENDURANCE, 1800, 1);

    public static final RegistryObject<Potion> PRICKLING = register("prickling", BGEffects.PRICKLING, 3600);
    public static final RegistryObject<Potion> LONG_PRICKLING = register("long_prickling", BGEffects.PRICKLING, 9600);
    public static final RegistryObject<Potion> STRONG_PRICKLING = register("strong_prickling", BGEffects.PRICKLING, 1800, 1);

    private static RegistryObject<Potion> register(String id, Supplier<Effect> effect, int duration) {
        return POTIONS.register(id, () -> new Potion(new EffectInstance(effect.get(), duration)));
    }

    private static RegistryObject<Potion> register(String id, Supplier<Effect> effect, int duration, int amplifier) {
        return POTIONS.register(id, () -> new Potion(new EffectInstance(effect.get(), duration, amplifier)));
    }
}