package mod.schnappdragon.habitat.core.util;

import com.google.gson.JsonObject;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class CompatHelper {
    public static boolean checkMods(String... modids) {
        if (!Habitat.DEV) {
            ModList modList = ModList.get();
            for (String modid : modids) {
                if (!modList.isLoaded(modid))
                    return false;
            }
        }
        return true;
    }

    public static boolean checkQuarkFlag(String flag) {
        if (ModList.get().isLoaded("quark")) {
            JsonObject dummyObject = new JsonObject();
            dummyObject.addProperty("type", "quark:flag");
            dummyObject.addProperty("flag", flag);
            return CraftingHelper.getCondition(dummyObject).test();
        }
        return Habitat.DEV;
    }

    public static CreativeModeTab compatItemGroup(CreativeModeTab group, String... modids) {
        return checkMods(modids) ? group : null;
    }
}