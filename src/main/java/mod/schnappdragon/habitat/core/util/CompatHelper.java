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
}