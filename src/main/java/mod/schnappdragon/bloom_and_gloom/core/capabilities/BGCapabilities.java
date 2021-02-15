package mod.schnappdragon.bloom_and_gloom.core.capabilities;

import mod.schnappdragon.bloom_and_gloom.core.capabilities.classes.ConsumedFairyRingMushroom;
import mod.schnappdragon.bloom_and_gloom.core.capabilities.interfaces.IConsumedFairyRingMushroom;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class BGCapabilities {
    public static void registerCapabilities() {
        register(IConsumedFairyRingMushroom.class, ConsumedFairyRingMushroom.STORAGE, ConsumedFairyRingMushroom.FACTORY);
    }

    private static <T> void register(Class<T> type, Capability.IStorage<T> storage, Callable<? extends T> factory) {
        CapabilityManager.INSTANCE.register(type, storage, factory);
    }
}
