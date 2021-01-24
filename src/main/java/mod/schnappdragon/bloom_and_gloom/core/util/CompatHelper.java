package mod.schnappdragon.bloom_and_gloom.core.util;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class CompatHelper {
    private static boolean modLoadedOrDev(String modid) {
        return ModList.get().isLoaded(modid) || BloomAndGloom.DEV;
    }

    public static void registerCompat(String modid, Runnable register) {
        if (modLoadedOrDev(modid))
            register.run();
    }

    public static <I extends IForgeRegistryEntry<? super I>> RegistryObject<I> registerCompat(String modid, Supplier<RegistryObject<I>> register) {
        return modLoadedOrDev(modid) ? register.get() : null;
    }
}