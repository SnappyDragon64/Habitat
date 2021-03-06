package mod.schnappdragon.habitat.client.particle;

import mod.schnappdragon.habitat.core.Habitat;
import mod.schnappdragon.habitat.core.registry.HabitatParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Habitat.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatParticleManager {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(HabitatParticleTypes.DRIPPING_SLIME.get(), SlimeDripParticle.DrippingSlimeFactory::new);
        Minecraft.getInstance().particles.registerFactory(HabitatParticleTypes.FALLING_SLIME.get(), SlimeDripParticle.FallingSlimeFactory::new);
        Minecraft.getInstance().particles.registerFactory(HabitatParticleTypes.LANDING_SLIME.get(), SlimeDripParticle.LandingSlimeFactory::new);
        Minecraft.getInstance().particles.registerFactory(HabitatParticleTypes.FAIRY_RING_SPORE.get(), FairyRingSporeParticle.Factory::new);
    }
}