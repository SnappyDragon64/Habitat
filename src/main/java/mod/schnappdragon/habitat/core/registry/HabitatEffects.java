package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.common.potion.HabitatEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Habitat.MODID);

    public static final RegistryObject<MobEffect> BLAST_ENDURANCE = EFFECTS.register("blast_endurance",
            () -> new HabitatEffect(MobEffectCategory.BENEFICIAL, 8440968));

    public static final RegistryObject<MobEffect> PRICKLING = EFFECTS.register("prickling",
            () -> new HabitatEffect(MobEffectCategory.BENEFICIAL, 5794588));
}