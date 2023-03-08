package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatParticleProviders {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.register(HabitatParticleTypes.FALLING_SLIME.get(), SlimeFallAndLandParticle.FallingSlimeProvider::new);
        event.register(HabitatParticleTypes.LANDING_SLIME.get(), SlimeDripLandParticle.LandingSlimeProvider::new);
        event.register(HabitatParticleTypes.FAIRY_RING_SPORE.get(), FairyRingSporeParticle.Provider::new);
        event.register(HabitatParticleTypes.FEATHER.get(), FeatherParticle.Provider::new);
        event.register(HabitatParticleTypes.NOTE.get(), NoteParticle.Provider::new);
    }
}