package mod.schnappdragon.bloom_and_gloom.core;

import mod.schnappdragon.bloom_and_gloom.client.renderer.entity.BGEntityRenderers;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGFeatures;
import mod.schnappdragon.bloom_and_gloom.core.registry.BGConfiguredFeatures;
import mod.schnappdragon.bloom_and_gloom.core.misc.BGComposterChances;
import mod.schnappdragon.bloom_and_gloom.core.dispenser.BGDispenserBehaviours;
import mod.schnappdragon.bloom_and_gloom.client.renderer.BGRenderLayers;
import mod.schnappdragon.bloom_and_gloom.core.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BloomAndGloom.MOD_ID)
public class BloomAndGloom {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "bloom_and_gloom";
    public static final boolean DEV = false;

    public BloomAndGloom() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        BGBlocks.BLOCKS.register(modEventBus);
        BGItems.ITEMS.register(modEventBus);
        BGTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        BGSoundEvents.SOUND_EVENTS.register(modEventBus);
        BGEntityTypes.ENTITY_TYPES.register(modEventBus);
        BGEffects.EFFECTS.register(modEventBus);
        BGFeatures.FEATURES.register(modEventBus);
        BGParticleTypes.PARTICLE_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BGConfiguredFeatures.registerConfiguredFeatures();
            BGComposterChances.registerComposterChances();
            BGDispenserBehaviours.registerDispenserBehaviour();
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        BGRenderLayers.registerRenderLayers();
        BGEntityRenderers.registerRenderers(event.getMinecraftSupplier());
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}
