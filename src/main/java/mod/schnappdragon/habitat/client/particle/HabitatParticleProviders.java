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
        event.registerSpriteSet(HabitatParticleTypes.FALLING_SLIME.get(), SlimeFallAndLandParticle.FallingSlimeProvider::new);
        event.registerSpriteSet(HabitatParticleTypes.LANDING_SLIME.get(), SlimeDripLandParticle.LandingSlimeProvider::new);
        event.registerSpriteSet(HabitatParticleTypes.FAIRY_RING_SPORE.get(), FairyRingSporeParticle.Provider::new);
        event.registerSpriteSet(HabitatParticleTypes.FEATHER.get(), FeatherParticle.Provider::new);
        event.registerSpriteSet(HabitatParticleTypes.NOTE.get(), NoteParticle.Provider::new);
        event.registerSpriteSet(HabitatParticleTypes.BLOWBALL_PUFF.get(), BlowballPuffParticle.Provider::new);
    }
}