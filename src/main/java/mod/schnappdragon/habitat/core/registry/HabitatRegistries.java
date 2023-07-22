package mod.schnappdragon.habitat.core.registry;

import mod.schnappdragon.habitat.common.entity.animal.PasserineVariant;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;

@Mod.EventBusSubscriber(modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatRegistries {
    @SubscribeEvent()
    public static void createDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Keys.PASSERINE_VARIANTS, PasserineVariants.CODEC, PasserineVariants.CODEC);
    }

    public static class Keys {
        public static final ResourceKey<Registry<PasserineVariant>> PASSERINE_VARIANTS = ResourceKey.createRegistryKey(new ResourceLocation(Habitat.MODID, "passerine_variants"));
    }
}
