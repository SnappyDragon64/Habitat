package mod.schnappdragon.bloom_and_gloom.api.capabilities;

import mod.schnappdragon.bloom_and_gloom.api.capabilities.classes.ConsumedFairyRingMushroom;
import mod.schnappdragon.bloom_and_gloom.api.capabilities.interfaces.IConsumedFairyRingMushroom;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityHandler {
    public static void registerCapabilities() {
        register(IConsumedFairyRingMushroom.class, ConsumedFairyRingMushroom.STORAGE, ConsumedFairyRingMushroom.FACTORY);
    }

    private static <T> void register(Class<T> type, Capability.IStorage<T> storage, Callable<? extends T> factory) {
        CapabilityManager.INSTANCE.register(type, storage, factory);
    }
}
