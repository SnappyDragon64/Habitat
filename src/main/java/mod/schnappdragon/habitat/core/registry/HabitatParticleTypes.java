package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Habitat.MODID);

    public static final RegistryObject<SimpleParticleType> FALLING_SLIME = register("falling_slime", false);
    public static final RegistryObject<SimpleParticleType> LANDING_SLIME = register("landing_slime", false);
    public static final RegistryObject<SimpleParticleType> FAIRY_RING_SPORE = register("fairy_ring_spore", false);

    private static RegistryObject<SimpleParticleType> register(String name, Boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }
}
