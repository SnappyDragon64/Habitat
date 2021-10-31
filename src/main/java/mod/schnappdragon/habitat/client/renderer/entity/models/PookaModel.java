package mod.schnappdragon.habitat.client.renderer.entity.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class PookaModel<T extends Pooka> extends EntityModel<T> {
    private final ModelPart pookaLeftFoot;
    private final ModelPart pookaRightFoot;
    private final ModelPart pookaLeftThigh;
    private final ModelPart pookaRightThigh;
    private final ModelPart pookaBody;
    private final ModelPart pookaSmallMushroomX;
    private final ModelPart pookaSmallMushroomZ;
    private final ModelPart pookaBigMushroomX;
    private final ModelPart pookaBigMushroomZ;
    private final ModelPart pookaLeftArm;
    private final ModelPart pookaRightArm;
    private final ModelPart pookaHead;
    private final ModelPart pookaRightEar;
    private final ModelPart pookaLeftEar;
    private final ModelPart pookaRightEarBase;
    private final ModelPart pookaLeftEarBase;
    private final ModelPart pookaTail;
    private final ModelPart pookaNose;
    private float jumpRotation;

    public PookaModel() {
        this.pookaLeftFoot = new ModelPart(this, 26, 24);
        this.pookaLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.pookaLeftFoot.setPos(3.0F, 17.5F, 3.7F);
        this.pookaLeftFoot.mirror = true;
        this.setRotationOffset(this.pookaLeftFoot, 0.0F, 0.0F, 0.0F);
        this.pookaRightFoot = new ModelPart(this, 8, 24);
        this.pookaRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.pookaRightFoot.setPos(-3.0F, 17.5F, 3.7F);
        this.pookaRightFoot.mirror = true;
        this.setRotationOffset(this.pookaRightFoot, 0.0F, 0.0F, 0.0F);
        this.pookaLeftThigh = new ModelPart(this, 30, 15);
        this.pookaLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.pookaLeftThigh.setPos(3.0F, 17.5F, 3.7F);
        this.pookaLeftThigh.mirror = true;
        this.setRotationOffset(this.pookaLeftThigh, -0.34906584F, 0.0F, 0.0F);
        this.pookaRightThigh = new ModelPart(this, 16, 15);
        this.pookaRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.pookaRightThigh.setPos(-3.0F, 17.5F, 3.7F);
        this.pookaRightThigh.mirror = true;
        this.setRotationOffset(this.pookaRightThigh, -0.34906584F, 0.0F, 0.0F);
        this.pookaBody = new ModelPart(this, 0, 0);
        this.pookaBody.addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F);
        this.pookaBody.setPos(0.0F, 19.0F, 8.0F);
        this.pookaBody.mirror = true;
        this.setRotationOffset(this.pookaBody, -0.34906584F, 0.0F, 0.0F);
        this.pookaBigMushroomX = new ModelPart(this, 0, 27);
        this.pookaBigMushroomX.addBox(0.0F, -7.0F, -3.0F, 3.0F, 5.0F, 0.0F);
        this.pookaBigMushroomX.setPos(0.0F, 19.0F, 8.0F);
        this.pookaBigMushroomX.mirror = true;
        this.setRotationOffset(this.pookaBigMushroomX, -0.34906584F, 0.0F, 0.0F);
        this.pookaBigMushroomZ = new ModelPart(this, 0, 24);
        this.pookaBigMushroomZ.addBox(1.5F, -7.0F, -4.5F, 0.0F, 5.0F, 3.0F);
        this.pookaBigMushroomZ.setPos(0.0F, 19.0F, 8.0F);
        this.pookaBigMushroomZ.mirror = true;
        this.setRotationOffset(this.pookaBigMushroomZ, -0.34906584F, 0.0F, 0.0F);
        this.pookaSmallMushroomX = new ModelPart(this, 22, 27);
        this.pookaSmallMushroomX.addBox(-3.0F, -6.0F, -7.0F, 3.0F, 4.0F, 0.0F);
        this.pookaSmallMushroomX.setPos(0.0F, 19.0F, 8.0F);
        this.pookaSmallMushroomX.mirror = true;
        this.setRotationOffset(this.pookaSmallMushroomX, -0.34906584F, 0.0F, 0.0F);
        this.pookaSmallMushroomZ = new ModelPart(this, 22, 24);
        this.pookaSmallMushroomZ.addBox(-1.5F, -6.0F, -8.5F, 0.0F, 4.0F, 3.0F);
        this.pookaSmallMushroomZ.setPos(0.0F, 19.0F, 8.0F);
        this.pookaSmallMushroomZ.mirror = true;
        this.setRotationOffset(this.pookaSmallMushroomZ, -0.34906584F, 0.0F, 0.0F);
        this.pookaLeftArm = new ModelPart(this, 8, 15);
        this.pookaLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.pookaLeftArm.setPos(3.0F, 17.0F, -1.0F);
        this.pookaLeftArm.mirror = true;
        this.setRotationOffset(this.pookaLeftArm, -0.17453292F, 0.0F, 0.0F);
        this.pookaRightArm = new ModelPart(this, 0, 15);
        this.pookaRightArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.pookaRightArm.setPos(-3.0F, 17.0F, -1.0F);
        this.pookaRightArm.mirror = true;
        this.setRotationOffset(this.pookaRightArm, -0.17453292F, 0.0F, 0.0F);
        this.pookaHead = new ModelPart(this, 32, 0);
        this.pookaHead.addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F);
        this.pookaHead.setPos(0.0F, 16.0F, -1.0F);
        this.pookaHead.mirror = true;
        this.setRotationOffset(this.pookaHead, 0.0F, 0.0F, 0.0F);
        this.pookaRightEar = new ModelPart(this, 0, 0);
        this.pookaRightEar.addBox(-3.5F, -10.0F, -1.0F, 3.0F, 5.0F, 1.0F);
        this.pookaRightEar.setPos(0.0F, 16.0F, -1.0F);
        this.pookaRightEar.mirror = true;
        this.setRotationOffset(this.pookaRightEar, 0.0F, -0.2617994F, 0.0F);
        this.pookaLeftEar = new ModelPart(this, 22, 0);
        this.pookaLeftEar.addBox(0.5F, -10.0F, -1.0F, 3.0F, 5.0F, 1.0F);
        this.pookaLeftEar.setPos(0.0F, 16.0F, -1.0F);
        this.pookaLeftEar.mirror = true;
        this.pookaRightEarBase = new ModelPart(this, 1, 6);
        this.pookaRightEarBase.addBox(-3.5F, -5.0F, -0.5F, 3.0F, 1.0F, 0.0F);
        this.pookaRightEarBase.setPos(0.0F, 16.0F, -1.0F);
        this.pookaRightEarBase.mirror = true;
        this.setRotationOffset(this.pookaRightEarBase, 0.0F, -0.2617994F, 0.0F);
        this.pookaLeftEarBase = new ModelPart(this, 1, 6);
        this.pookaLeftEarBase.addBox(0.5F, -5.0F, -0.5F, 3.0F, 1.0F, 0.0F);
        this.pookaLeftEarBase.setPos(0.0F, 16.0F, -1.0F);
        this.pookaLeftEarBase.mirror = true;
        this.setRotationOffset(this.pookaLeftEarBase, 0.0F, 0.2617994F, 0.0F);
        this.pookaTail = new ModelPart(this, 52, 0);
        this.pookaTail.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F);
        this.pookaTail.setPos(0.0F, 20.0F, 7.0F);
        this.pookaTail.mirror = true;
        this.setRotationOffset(this.pookaTail, -0.3490659F, 0.0F, 0.0F);
        this.pookaNose = new ModelPart(this, 32, 9);
        this.pookaNose.addBox(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F);
        this.pookaNose.setPos(0.0F, 16.0F, -1.0F);
        this.pookaNose.mirror = true;
        this.setRotationOffset(this.pookaNose, 0.0F, 0.0F, 0.0F);
    }

    private void setRotationOffset(ModelPart renderer, float x, float y, float z) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = z;
    }

    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.young) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.56666666F, 0.56666666F, 0.56666666F);
            matrixStackIn.translate(0.0D, 1.375D, 0.125D);
            ImmutableList.of(this.pookaHead, this.pookaLeftEar, this.pookaRightEar, this.pookaLeftEarBase, this.pookaRightEarBase, this.pookaNose).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.4F, 0.4F, 0.4F);
            matrixStackIn.translate(0.0D, 2.25D, 0.0D);
            ImmutableList.of(this.pookaLeftFoot, this.pookaRightFoot, this.pookaLeftThigh, this.pookaRightThigh, this.pookaBody, this.pookaLeftArm, this.pookaRightArm, this.pookaTail).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
        } else {
            matrixStackIn.pushPose();
            matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            matrixStackIn.translate(0.0D, 1.0D, 0.0D);
            ImmutableList.of(this.pookaLeftFoot, this.pookaRightFoot, this.pookaLeftThigh, this.pookaRightThigh, this.pookaBody, this.pookaSmallMushroomX, this.pookaSmallMushroomZ, this.pookaBigMushroomX, this.pookaBigMushroomZ, this.pookaLeftArm, this.pookaRightArm, this.pookaHead, this.pookaRightEar, this.pookaLeftEar, this.pookaRightEarBase, this.pookaLeftEarBase, this.pookaTail, this.pookaNose).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.popPose();
        }
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float) entityIn.tickCount;
        this.pookaNose.xRot = headPitch * ((float) Math.PI / 180F);
        this.pookaHead.xRot = headPitch * ((float) Math.PI / 180F);
        this.pookaRightEar.xRot = headPitch * ((float) Math.PI / 180F);
        this.pookaLeftEar.xRot = headPitch * ((float) Math.PI / 180F);
        this.pookaRightEarBase.xRot = this.pookaRightEar.xRot;
        this.pookaLeftEarBase.xRot = this.pookaLeftEar.xRot;
        this.pookaNose.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.pookaHead.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.pookaRightEar.yRot = this.pookaNose.yRot - 0.2617994F;
        this.pookaLeftEar.yRot = this.pookaNose.yRot + 0.2617994F;
        this.pookaRightEarBase.yRot = this.pookaRightEar.yRot;
        this.pookaLeftEarBase.yRot = this.pookaLeftEar.yRot;
        this.jumpRotation = Mth.sin(entityIn.getJumpCompletion(f) * (float) Math.PI);
        this.pookaLeftThigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.pookaRightThigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.pookaLeftFoot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.pookaRightFoot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.pookaLeftArm.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        this.pookaRightArm.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
    }

    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = Mth.sin(entityIn.getJumpCompletion(partialTick) * (float)Math.PI);
    }
}