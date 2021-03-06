package mod.schnappdragon.habitat.core.util;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class CompatHelper {
    private static boolean modLoadedOrDev(String modid) {
        return ModList.get().isLoaded(modid) || Habitat.DEV;
    }

    public static void registerCompat(String modid, Runnable register) {
        if (modLoadedOrDev(modid))
            register.run();
    }

    public static <I extends IForgeRegistryEntry<? super I>> RegistryObject<I> registerCompat(String modid, Supplier<RegistryObject<I>> register) {
        return modLoadedOrDev(modid) ? register.get() : null;
    }
}