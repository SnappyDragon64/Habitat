package mod.schnappdragon.bloom_and_gloom.client.particle;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BGParticleManager {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(BGParticleTypes.DRIPPING_SLIME.get(), SlimeDripParticle.DrippingSlimeFactory::new);
        Minecraft.getInstance().particles.registerFactory(BGParticleTypes.FALLING_SLIME.get(), SlimeDripParticle.FallingSlimeFactory::new);
        Minecraft.getInstance().particles.registerFactory(BGParticleTypes.LANDING_SLIME.get(), SlimeDripParticle.LandingSlimeFactory::new);
    }
}