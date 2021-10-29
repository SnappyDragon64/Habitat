package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.common.potion.HabitatEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Habitat.MODID);

    public static final RegistryObject<Effect> BLAST_ENDURANCE = EFFECTS.register("blast_endurance",
            () -> new HabitatEffect(EffectType.BENEFICIAL, 8440968));

    public static final RegistryObject<Effect> PRICKLING = EFFECTS.register("prickling",
            () -> new HabitatEffect(EffectType.BENEFICIAL, 5794588));
}