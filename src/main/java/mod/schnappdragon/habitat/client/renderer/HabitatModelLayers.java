package mod.schnappdragon.habitat.client.renderer;

import mod.schnappdragon.habitat.client.model.PasserineModel;
import mod.schnappdragon.habitat.client.model.PookaModel;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Habitat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HabitatModelLayers {
    public static ModelLayerLocation POOKA = new ModelLayerLocation(new ResourceLocation(Habitat.MODID, "pooka"), "main");
    public static ModelLayerLocation PASSERINE = new ModelLayerLocation(new ResourceLocation(Habitat.MODID, "passerine"), "main");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(POOKA, PookaModel::createBodyLayer);
        event.registerLayerDefinition(PASSERINE, PasserineModel::createBodyLayer);
    }
}