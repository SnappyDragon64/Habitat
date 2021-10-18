package mod.schnappdragon.habitat.client.renderer.entity.models;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.schnappdragon.habitat.common.entity.monster.PookaEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class PookaModel<T extends PookaEntity> extends EntityModel<T> {
    private final ModelRenderer pookaLeftFoot;
    private final ModelRenderer pookaRightFoot;
    private final ModelRenderer pookaLeftThigh;
    private final ModelRenderer pookaRightThigh;
    private final ModelRenderer pookaBody;
    private final ModelRenderer pookaSmallMushroomX;
    private final ModelRenderer pookaSmallMushroomZ;
    private final ModelRenderer pookaBigMushroomX;
    private final ModelRenderer pookaBigMushroomZ;
    private final ModelRenderer pookaLeftArm;
    private final ModelRenderer pookaRightArm;
    private final ModelRenderer pookaHead;
    private final ModelRenderer pookaRightEar;
    private final ModelRenderer pookaLeftEar;
    private final ModelRenderer pookaRightEarBase;
    private final ModelRenderer pookaLeftEarBase;
    private final ModelRenderer pookaTail;
    private final ModelRenderer pookaNose;
    private float jumpRotation;

    public PookaModel() {
        this.pookaLeftFoot = new ModelRenderer(this, 26, 24);
        this.pookaLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.pookaLeftFoot.setRotationPoint(3.0F, 17.5F, 3.7F);
        this.pookaLeftFoot.mirror = true;
        this.setRotationOffset(this.pookaLeftFoot, 0.0F, 0.0F, 0.0F);
        this.pookaRightFoot = new ModelRenderer(this, 8, 24);
        this.pookaRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F);
        this.pookaRightFoot.setRotationPoint(-3.0F, 17.5F, 3.7F);
        this.pookaRightFoot.mirror = true;
        this.setRotationOffset(this.pookaRightFoot, 0.0F, 0.0F, 0.0F);
        this.pookaLeftThigh = new ModelRenderer(this, 30, 15);
        this.pookaLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.pookaLeftThigh.setRotationPoint(3.0F, 17.5F, 3.7F);
        this.pookaLeftThigh.mirror = true;
        this.setRotationOffset(this.pookaLeftThigh, -0.34906584F, 0.0F, 0.0F);
        this.pookaRightThigh = new ModelRenderer(this, 16, 15);
        this.pookaRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F);
        this.pookaRightThigh.setRotationPoint(-3.0F, 17.5F, 3.7F);
        this.pookaRightThigh.mirror = true;
        this.setRotationOffset(this.pookaRightThigh, -0.34906584F, 0.0F, 0.0F);
        this.pookaBody = new ModelRenderer(this, 0, 0);
        this.pookaBody.addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F);
        this.pookaBody.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.pookaBody.mirror = true;
        this.setRotationOffset(this.pookaBody, -0.34906584F, 0.0F, 0.0F);
        this.pookaBigMushroomX = new ModelRenderer(this, 0, 27);
        this.pookaBigMushroomX.addBox(0.0F, -7.0F, -3.0F, 3.0F, 5.0F, 0.0F);
        this.pookaBigMushroomX.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.pookaBigMushroomX.mirror = true;
        this.setRotationOffset(this.pookaBigMushroomX, -0.34906584F, 0.0F, 0.0F);
        this.pookaBigMushroomZ = new ModelRenderer(this, 0, 24);
        this.pookaBigMushroomZ.addBox(1.5F, -7.0F, -4.5F, 0.0F, 5.0F, 3.0F);
        this.pookaBigMushroomZ.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.pookaBigMushroomZ.mirror = true;
        this.setRotationOffset(this.pookaBigMushroomZ, -0.34906584F, 0.0F, 0.0F);
        this.pookaSmallMushroomX = new ModelRenderer(this, 22, 27);
        this.pookaSmallMushroomX.addBox(-3.0F, -6.0F, -7.0F, 3.0F, 4.0F, 0.0F);
        this.pookaSmallMushroomX.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.pookaSmallMushroomX.mirror = true;
        this.setRotationOffset(this.pookaSmallMushroomX, -0.34906584F, 0.0F, 0.0F);
        this.pookaSmallMushroomZ = new ModelRenderer(this, 22, 24);
        this.pookaSmallMushroomZ.addBox(-1.5F, -6.0F, -8.5F, 0.0F, 4.0F, 3.0F);
        this.pookaSmallMushroomZ.setRotationPoint(0.0F, 19.0F, 8.0F);
        this.pookaSmallMushroomZ.mirror = true;
        this.setRotationOffset(this.pookaSmallMushroomZ, -0.34906584F, 0.0F, 0.0F);
        this.pookaLeftArm = new ModelRenderer(this, 8, 15);
        this.pookaLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.pookaLeftArm.setRotationPoint(3.0F, 17.0F, -1.0F);
        this.pookaLeftArm.mirror = true;
        this.setRotationOffset(this.pookaLeftArm, -0.17453292F, 0.0F, 0.0F);
        this.pookaRightArm = new ModelRenderer(this, 0, 15);
        this.pookaRightArm.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F);
        this.pookaRightArm.setRotationPoint(-3.0F, 17.0F, -1.0F);
        this.pookaRightArm.mirror = true;
        this.setRotationOffset(this.pookaRightArm, -0.17453292F, 0.0F, 0.0F);
        this.pookaHead = new ModelRenderer(this, 32, 0);
        this.pookaHead.addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F);
        this.pookaHead.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.pookaHead.mirror = true;
        this.setRotationOffset(this.pookaHead, 0.0F, 0.0F, 0.0F);
        this.pookaRightEar = new ModelRenderer(this, 0, 0);
        this.pookaRightEar.addBox(-3.5F, -10.0F, -1.0F, 3.0F, 5.0F, 1.0F);
        this.pookaRightEar.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.pookaRightEar.mirror = true;
        this.setRotationOffset(this.pookaRightEar, 0.0F, -0.2617994F, 0.0F);
        this.pookaLeftEar = new ModelRenderer(this, 22, 0);
        this.pookaLeftEar.addBox(0.5F, -10.0F, -1.0F, 3.0F, 5.0F, 1.0F);
        this.pookaLeftEar.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.pookaLeftEar.mirror = true;
        this.pookaRightEarBase = new ModelRenderer(this, 32, 0);
        this.pookaRightEarBase.addBox(-2.5F, -5.0F, -0.5F, 1.0F, 1.0F, 0.0F);
        this.pookaRightEarBase.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.pookaRightEarBase.mirror = true;
        this.setRotationOffset(this.pookaRightEarBase, 0.0F, -0.2617994F, 0.0F);
        this.pookaLeftEarBase = new ModelRenderer(this, 32, 0);
        this.pookaLeftEarBase.addBox(1.5F, -5.0F, -0.5F, 1.0F, 1.0F, 0.0F);
        this.pookaLeftEarBase.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.pookaLeftEarBase.mirror = true;
        this.setRotationOffset(this.pookaLeftEarBase, 0.0F, 0.2617994F, 0.0F);
        this.pookaTail = new ModelRenderer(this, 52, 0);
        this.pookaTail.addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F);
        this.pookaTail.setRotationPoint(0.0F, 20.0F, 7.0F);
        this.pookaTail.mirror = true;
        this.setRotationOffset(this.pookaTail, -0.3490659F, 0.0F, 0.0F);
        this.pookaNose = new ModelRenderer(this, 32, 9);
        this.pookaNose.addBox(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F);
        this.pookaNose.setRotationPoint(0.0F, 16.0F, -1.0F);
        this.pookaNose.mirror = true;
        this.setRotationOffset(this.pookaNose, 0.0F, 0.0F, 0.0F);
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            matrixStackIn.push();
            matrixStackIn.scale(0.56666666F, 0.56666666F, 0.56666666F);
            matrixStackIn.translate(0.0D, 1.375D, 0.125D);
            ImmutableList.of(this.pookaHead, this.pookaLeftEar, this.pookaRightEar, this.pookaLeftEarBase, this.pookaRightEarBase, this.pookaNose).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.scale(0.4F, 0.4F, 0.4F);
            matrixStackIn.translate(0.0D, 2.25D, 0.0D);
            ImmutableList.of(this.pookaLeftFoot, this.pookaRightFoot, this.pookaLeftThigh, this.pookaRightThigh, this.pookaBody, this.pookaLeftArm, this.pookaRightArm, this.pookaTail).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
        } else {
            matrixStackIn.push();
            matrixStackIn.scale(0.6F, 0.6F, 0.6F);
            matrixStackIn.translate(0.0D, 1.0D, 0.0D);
            ImmutableList.of(this.pookaLeftFoot, this.pookaRightFoot, this.pookaLeftThigh, this.pookaRightThigh, this.pookaBody, this.pookaSmallMushroomX, this.pookaSmallMushroomZ, this.pookaBigMushroomX, this.pookaBigMushroomZ, this.pookaLeftArm, this.pookaRightArm, this.pookaHead, this.pookaRightEar, this.pookaLeftEar, this.pookaRightEarBase, this.pookaLeftEarBase, this.pookaTail, this.pookaNose).forEach((modelRenderer) -> {
                modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            matrixStackIn.pop();
        }
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float) entityIn.ticksExisted;
        this.pookaNose.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.pookaHead.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.pookaRightEar.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.pookaLeftEar.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.pookaRightEarBase.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.pookaLeftEarBase.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.pookaNose.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.pookaHead.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.pookaRightEar.rotateAngleY = this.pookaNose.rotateAngleY - 0.2617994F;
        this.pookaLeftEar.rotateAngleY = this.pookaNose.rotateAngleY + 0.2617994F;
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(f) * (float) Math.PI);
        this.pookaLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.pookaRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.pookaLeftFoot.rotateAngleX = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.pookaRightFoot.rotateAngleX = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.pookaLeftArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        this.pookaRightArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = MathHelper.sin(entityIn.getJumpCompletion(partialTick) * (float)Math.PI);
    }
}