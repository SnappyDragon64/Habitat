package mod.schnappdragon.habitat.core;

import mod.schnappdragon.habitat.client.renderer.entity.HabitatEntityRenderers;
import mod.schnappdragon.habitat.core.misc.HabitatBrewingMixes;
import mod.schnappdragon.habitat.core.misc.HabitatFireInfo;
import mod.schnappdragon.habitat.core.registry.HabitatFeatures;
import mod.schnappdragon.habitat.core.registry.HabitatConfiguredFeatures;
import mod.schnappdragon.habitat.core.misc.HabitatComposterChances;
import mod.schnappdragon.habitat.core.misc.HabitatDispenserBehaviours;
import mod.schnappdragon.habitat.client.renderer.HabitatRenderLayers;
import mod.schnappdragon.habitat.core.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Habitat.MOD_ID)
public class Habitat {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "habitat";

    public Habitat() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        HabitatBlocks.BLOCKS.register(modEventBus);
        HabitatItems.ITEMS.register(modEventBus);
        HabitatTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        HabitatSoundEvents.SOUND_EVENTS.register(modEventBus);
        HabitatEntityTypes.ENTITY_TYPES.register(modEventBus);
        HabitatEffects.EFFECTS.register(modEventBus);
        HabitatPotions.POTIONS.register(modEventBus);
        HabitatRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        HabitatFeatures.FEATURES.register(modEventBus);
        HabitatParticleTypes.PARTICLE_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            HabitatBrewingMixes.registerBrewingMixes();
            HabitatConfiguredFeatures.registerConfiguredFeatures();
            HabitatComposterChances.registerComposterChances();
            HabitatDispenserBehaviours.registerDispenserBehaviour();
            HabitatFireInfo.registerFireInfo();
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        HabitatRenderLayers.registerRenderLayers();
        HabitatEntityRenderers.registerRenderers(event.getMinecraftSupplier());
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}