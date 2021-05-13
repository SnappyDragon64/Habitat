package mod.schnappdragon.habitat.core.util;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class CompatHelper {
    public static boolean checkCompat(String... modids) {
        if (FMLLoader.isProduction()) {
            ModList modList = ModList.get();
            for (String modid : modids) {
                if (!modList.isLoaded(modid))
                    return false;
            }
        }
        return true;
    }

    public static ItemGroup compatItemGroup(ItemGroup groupIn, String... modids) {
        return checkCompat(modids) ? groupIn : null;
    }
}