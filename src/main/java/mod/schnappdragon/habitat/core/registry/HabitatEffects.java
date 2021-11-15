package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.effect.HabitatEffect;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Habitat.MODID);

    public static final RegistryObject<MobEffect> BLAST_ENDURANCE = EFFECTS.register("blast_endurance",
            () -> new HabitatEffect(MobEffectCategory.BENEFICIAL, 8440968));

    public static final RegistryObject<MobEffect> PRICKLING = EFFECTS.register("prickling",
            () -> new HabitatEffect(MobEffectCategory.BENEFICIAL, 5794588));
}