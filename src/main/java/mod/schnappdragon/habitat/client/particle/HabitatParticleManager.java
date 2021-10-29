package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatParticleManager {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        registerFactory(HabitatParticleTypes.FALLING_SLIME.get(), FallingSlimeParticle.FallingSlimeFactory::new);
        registerFactory(HabitatParticleTypes.LANDING_SLIME.get(), LandingSlimeParticle.LandingSlimeFactory::new);
        registerFactory(HabitatParticleTypes.FAIRY_RING_SPORE.get(), FairyRingSporeParticle.Factory::new);
    }

    private static <T extends IParticleData> void registerFactory(ParticleType<T> particle, ParticleManager.IParticleMetaFactory<T> factory) {
        Minecraft.getInstance().particles.registerFactory(particle, factory);
    }
}