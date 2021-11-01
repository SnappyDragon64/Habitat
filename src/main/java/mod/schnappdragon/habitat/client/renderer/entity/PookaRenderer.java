package mod.schnappdragon.habitat.client.renderer.entity;

import mod.schnappdragon.habitat.client.model.PookaModel;
import mod.schnappdragon.habitat.client.renderer.HabitatModelLayers;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class PookaRenderer extends MobRenderer<Pooka, PookaModel<Pooka>> {
    private static final ResourceLocation POOKA_TEXTURES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/pooka.png");
    private static final ResourceLocation PACIFIED_POOKA_TEXTURES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/pooka_pacified.png");

    public PookaRenderer(EntityRendererProvider.Context context) {
        super(context, new PookaModel<>(context.bakeLayer(HabitatModelLayers.POOKA)), 0.3F);
    }

    protected int getBlockLightLevel(Pooka entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(Pooka entity) {
        return entity.isPacified() ? PACIFIED_POOKA_TEXTURES : POOKA_TEXTURES;
    }
}