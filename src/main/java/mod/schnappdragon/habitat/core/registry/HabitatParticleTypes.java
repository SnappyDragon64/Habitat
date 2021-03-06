package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HabitatParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Habitat.MOD_ID);

    public static final RegistryObject<BasicParticleType> DRIPPING_SLIME = register("dripping_slime", false);
    public static final RegistryObject<BasicParticleType> FALLING_SLIME = register("falling_slime",false);
    public static final RegistryObject<BasicParticleType> LANDING_SLIME = register("landing_slime",false);
    public static final RegistryObject<BasicParticleType> FAIRY_RING_SPORE = register("fairy_ring_spore",false);

    private static RegistryObject<BasicParticleType> register(String name, Boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new BasicParticleType(alwaysShow));
    }
}
