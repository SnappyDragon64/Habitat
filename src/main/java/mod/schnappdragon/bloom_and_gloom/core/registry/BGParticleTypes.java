package mod.schnappdragon.bloom_and_gloom.core.registry;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BGParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BloomAndGloom.MOD_ID);

    public static final RegistryObject<BasicParticleType> DRIPPING_SLIME = PARTICLE_TYPES.register("dripping_slime", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> FALLING_SLIME = PARTICLE_TYPES.register("falling_slime", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> LANDING_SLIME = PARTICLE_TYPES.register("landing_slime", () -> new BasicParticleType(false));
}
