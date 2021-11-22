package mod.schnappdragon.habitat.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PookaModel<T extends Pooka> extends EntityModel<T> {
    private final ModelPart leftRearFoot;
    private final ModelPart rightRearFoot;
    private final ModelPart leftHaunch;
    private final ModelPart rightHaunch;
    private final ModelPart body;
    private final ModelPart smallMushroomX;
    private final ModelPart smallMushroomZ;
    private final ModelPart bigMushroomX;
    private final ModelPart bigMushroomZ;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart head;
    private final ModelPart rightEar;
    private final ModelPart rightEarStalk;
    private final ModelPart leftEar;
    private final ModelPart leftEarStalk;
    private final ModelPart tail;
    private final ModelPart nose;
    private float jumpRotation;

    public PookaModel(ModelPart part) {
        this.leftRearFoot = part.getChild("left_hind_foot");
        this.rightRearFoot = part.getChild("right_hind_foot");
        this.leftHaunch = part.getChild("left_haunch");
        this.rightHaunch = part.getChild("right_haunch");
        this.body = part.getChild("body");
        this.smallMushroomX = part.getChild("small_mushroom_x");
        this.smallMushroomZ = part.getChild("small_mushroom_z");
        this.bigMushroomX = part.getChild("big_mushroom_x");
        this.bigMushroomZ = part.getChild("big_mushroom_z");
        this.leftFrontLeg = part.getChild("left_front_leg");
        this.rightFrontLeg = part.getChild("right_front_leg");
        this.head = part.getChild("head");
        this.rightEar = part.getChild("right_ear");
        this.rightEarStalk = part.getChild("right_ear_stalk");
        this.leftEar = part.getChild("left_ear");
        this.leftEarStalk = part.getChild("left_ear_stalk");
        this.tail = part.getChild("tail");
        this.nose = part.getChild("nose");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(26, 24).addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F), PartPose.offset(3.0F, 17.5F, 3.7F));
        partdefinition.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, 5.5F, -3.7F, 2.0F, 1.0F, 7.0F), PartPose.offset(-3.0F, 17.5F, 3.7F));
        partdefinition.addOrReplaceChild("left_haunch", CubeListBuilder.create().texOffs(30, 15).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F), PartPose.offsetAndRotation(3.0F, 17.5F, 3.7F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_haunch", CubeListBuilder.create().texOffs(16, 15).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 5.0F), PartPose.offsetAndRotation(-3.0F, 17.5F, 3.7F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.0F, -10.0F, 6.0F, 5.0F, 10.0F), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("big_mushroom_x", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, -7.0F, -3.0F, 3.0F, 5.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("big_mushroom_z", CubeListBuilder.create().texOffs(0, 24).addBox(1.5F, -7.0F, -4.5F, 0.0F, 5.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("small_mushroom_x", CubeListBuilder.create().texOffs(22, 27).addBox(-3.0F, -6.0F, -7.0F, 3.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("small_mushroom_z", CubeListBuilder.create().texOffs(22, 24).addBox(-1.5F, -6.0F, -8.5F, 0.0F, 4.0F, 3.0F), PartPose.offsetAndRotation(0.0F, 19.0F, 8.0F, -0.34906584F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(8, 15).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(3.0F, 17.0F, -1.0F, -0.17453292F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offsetAndRotation(-3.0F, 17.0F, -1.0F, -0.17453292F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-2.5F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F), PartPose.offset(0.0F, 16.0F, -1.0F));
        partdefinition.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -10.0F, -1.0F, 3.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, 0.0F, -0.2617994F, 0.0F));
        partdefinition.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(22, 0).addBox(0.5F, -10.0F, -1.0F, 3.0F, 5.0F, 1.0F), PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, 0.0F, 0.2617994F, 0.0F));
        partdefinition.addOrReplaceChild("right_ear_stalk", CubeListBuilder.create().texOffs(1, 6).addBox(-3.5F, -5.0F, -0.5F, 3.0F, 1.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, 0.0F, -0.2617994F, 0.0F));
        partdefinition.addOrReplaceChild("left_ear_stalk", CubeListBuilder.create().texOffs(1, 6).addBox(0.5F, -5.0F, -0.5F, 3.0F, 1.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 16.0F, -1.0F, 0.0F, 0.2617994F, 0.0F));
        partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(52, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F), PartPose.offsetAndRotation(0.0F, 20.0F, 7.0F, -0.3490659F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(32, 9).addBox(-0.5F, -2.5F, -5.5F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 16.0F, -1.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(0.56666666F, 0.56666666F, 0.56666666F);
            poseStack.translate(0.0D, 1.375D, 0.125D);
            ImmutableList.of(this.head, this.leftEar, this.leftEarStalk, this.rightEar, this.rightEarStalk, this.nose).forEach((part) -> {
                part.render(poseStack, consumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.scale(0.4F, 0.4F, 0.4F);
            poseStack.translate(0.0D, 2.25D, 0.0D);
            ImmutableList.of(this.leftRearFoot, this.rightRearFoot, this.leftHaunch, this.rightHaunch, this.body, this.leftFrontLeg, this.rightFrontLeg, this.tail).forEach((part) -> {
                part.render(poseStack, consumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            poseStack.scale(0.6F, 0.6F, 0.6F);
            poseStack.translate(0.0D, 1.0D, 0.0D);
            ImmutableList.of(this.leftRearFoot, this.rightRearFoot, this.leftHaunch, this.rightHaunch, this.body, this.smallMushroomX, this.smallMushroomZ, this.bigMushroomX, this.bigMushroomZ, this.leftFrontLeg, this.rightFrontLeg, this.head, this.rightEar, this.rightEarStalk, this.leftEar, this.leftEarStalk, this.tail, this.nose).forEach((p_103572_) -> {
                p_103572_.render(poseStack, consumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
            poseStack.popPose();
        }
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float) entityIn.tickCount;
        this.nose.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.xRot = this.nose.xRot;
        this.rightEar.xRot = this.nose.xRot;
        this.leftEar.xRot = this.nose.xRot;
        this.rightEarStalk.xRot = this.nose.xRot;
        this.leftEarStalk.xRot = this.nose.xRot;
        this.nose.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.yRot = this.nose.yRot;
        this.rightEar.yRot = this.nose.yRot - 0.2617994F;
        this.leftEar.yRot = this.nose.yRot + 0.2617994F;
        this.rightEarStalk.yRot = this.rightEar.yRot;
        this.leftEarStalk.yRot = this.leftEar.yRot;
        this.jumpRotation = Mth.sin(entityIn.getJumpCompletion(f) * (float) Math.PI);
        this.leftHaunch.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
        this.rightHaunch.xRot = this.leftHaunch.xRot;
        this.leftRearFoot.xRot = this.jumpRotation * 50.0F * ((float) Math.PI / 180F);
        this.rightRearFoot.xRot = this.leftRearFoot.xRot;
        this.leftFrontLeg.xRot = (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
        this.rightFrontLeg.xRot = this.leftFrontLeg.xRot;
    }

    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
        this.jumpRotation = Mth.sin(entityIn.getJumpCompletion(partialTick) * (float) Math.PI);
    }
}