package mod.schnappdragon.habitat.client.renderer.entity;

import mod.schnappdragon.habitat.client.renderer.entity.models.PookaModel;
import mod.schnappdragon.habitat.common.entity.monster.PookaEntity;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class PookaRenderer extends MobRenderer<PookaEntity, PookaModel<PookaEntity>> {
    private static final ResourceLocation POOKA_TEXTURES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/pooka.png");
    private static final ResourceLocation PACIFIED_POOKA_TEXTURES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/pooka_pacified.png");

    public PookaRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PookaModel<>(), 0.3F);
    }

    protected int getBlockLight(PookaEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(PookaEntity entity) {
        return entity.isPacified() ? PACIFIED_POOKA_TEXTURES : POOKA_TEXTURES;
    }
}