package mod.schnappdragon.habitat.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.habitat.client.model.PookaModel;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class PookaEyesLayer<T extends Pooka, M extends PookaModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation HOSTILE_EYES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/hostile_eyes.png");
    private static final ResourceLocation PACIFIED_EYES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/pacified_eyes.png");
    private static final ResourceLocation PASSIVE_EYES = new ResourceLocation(Habitat.MODID, "textures/entity/pooka/passive_eyes.png");

    public PookaEyesLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, T pooka, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!pooka.isInvisible()) {
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pooka)));
            this.getParentModel().renderToBuffer(matrixStack, vertexconsumer, packedLight, LivingEntityRenderer.getOverlayCoords(pooka, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getTextureLocation(Pooka pooka) {
        return switch (pooka.getState()) {
            case HOSTILE -> HOSTILE_EYES;
            case PACIFIED -> PACIFIED_EYES;
            case PASSIVE -> PASSIVE_EYES;
        };
    }
}