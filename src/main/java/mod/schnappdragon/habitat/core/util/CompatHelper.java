package mod.schnappdragon.habitat.core.util;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class CompatHelper {
    private static boolean checkCompat(String... modids) {
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