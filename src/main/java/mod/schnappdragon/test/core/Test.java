package mod.schnappdragon.test.core;

import mod.schnappdragon.test.core.registry.*;
import mod.schnappdragon.test.core.util.SetupUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Test.MOD_ID)
public class Test {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "test";

    public Test() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModSoundEvents.SOUND_EVENTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            SetupUtil.registerComposterChances();
            event.enqueueWork(ModFeatures::new);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            SetupUtil.registerRenderLayers();
        });
    }
}
