package mod.schnappdragon.bloom_and_gloom.core.event;

import mod.schnappdragon.bloom_and_gloom.core.BloomAndGloom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BloomAndGloom.MOD_ID)
public class BGGeneration {

    @SubscribeEvent
    public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.JUNGLE)
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> mod.schnappdragon.bloom_and_gloom.core.registry.BGFeatures.RAFFLESIA_PATCH);
    }
}