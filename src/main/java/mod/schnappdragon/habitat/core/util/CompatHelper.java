package mod.schnappdragon.habitat.core.util;

import mod.schnappdragon.habitat.core.Habitat;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public class CompatHelper {
    private static boolean modsLoaded(String... modids) {
        if (FMLLoader.isProduction()) {
            ModList modList = ModList.get();
            for (String modid : modids) {
                if (!modList.isLoaded(modid))
                    return false;
            }
        }
        return true;
    }

    public static void registerCompat(Runnable register, String... modids) {
        if (modsLoaded(modids))
            register.run();
    }

    public static <I extends IForgeRegistryEntry<? super I>> RegistryObject<I> registerCompat(Supplier<RegistryObject<I>> register, String... modids) {
        return modsLoaded(modids) ? register.get() : null;
    }
}