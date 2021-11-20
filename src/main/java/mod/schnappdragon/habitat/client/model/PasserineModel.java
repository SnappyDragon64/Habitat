package mod.schnappdragon.habitat.client.model;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import mod.schnappdragon.habitat.core.Habitat;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class PasserineModel<T extends Passerine> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart crest;
	private final ModelPart beak;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightFoot;
	private final ModelPart leftFoot;
	private final ModelPart tail;

	public PasserineModel(ModelPart part) {
		this.root = part;
		this.head = part.getChild("head");
		this.crest = part.getChild("crest");
		this.beak = part.getChild("beak");
		this.body = part.getChild("body");
		this.rightWing = part.getChild("right_wing");
		this.leftWing = part.getChild("left_wing");
		this.rightFoot = part.getChild("right_foot");
		this.leftFoot = part.getChild("left_foot");
		this.tail = part.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 20.0F, -2.0F));
		partdefinition.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(22, 2).addBox(0.0F, -6.0F, -2.0F, 0.0F, 5.0F, 5.0F), PartPose.offset(0.0F, 20.0F, -2.0F));
		partdefinition.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, 20.0F, -2.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.offset(0.0F, 21.5F, 0.0F));
		partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offset(-2.0F, 21.0F, -1.0F));
		partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offset(2.0F, 21.0F, -1.0F));
		partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(12, 0).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offset(0.0F, 23.0F, 1.0F));
		partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(12, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offset(0.0F, 23.0F, 1.0F));
		partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 8).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 6.0F), PartPose.offset(0.0F, 22.0F, 2.0F));
		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void prepareMobModel(Passerine passerine, float limbSwing, float limbSwingAmount, float partialTick) {
		if (passerine.isFlying()) {
			this.body.xRot = -0.3927F;
			this.rightWing.xRot = -0.5236F;
			this.leftWing.xRot = -0.5236F;
			this.rightFoot.xRot = -0.5236F;
			this.leftFoot.xRot = -0.5236F;
			this.rightFoot.yRot = 0.1571F;
			this.leftFoot.yRot = -0.1571F;
			this.tail.xRot = -0.2618F;
		} else {
			this.head.y = 20.0F;
			this.crest.y = 20.0F;
			this.beak.y = 20.0F;
			this.body.y = 21.5F;
			this.rightWing.y = 21.0F;
			this.leftWing.y = 21.0F;
			this.rightFoot.y = 23.0F;
			this.leftFoot.y = 23.0F;
			this.tail.y = 22.0F;

			this.body.xRot = -0.0873F;
			this.rightWing.xRot = -0.1963F;
			this.leftWing.xRot = -0.1963F;
			this.rightWing.zRot = 0.0F;
			this.leftWing.zRot = 0.0F;
			this.rightFoot.xRot = 0.0F;
			this.leftFoot.xRot = 0.0F;
			this.rightFoot.yRot = 0.1745F;
			this.leftFoot.yRot = -0.1745F;
			this.tail.xRot = 0.3927F;
		}
	}

	public void setupAnim(Passerine passerine, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = headPitch * ((float) Math.PI / 180F);
		float f1 = netHeadYaw * ((float) Math.PI / 180F);

		this.head.xRot = f;
		this.crest.xRot = f;
		this.beak.xRot = f;

		this.head.yRot = f1;
		this.crest.yRot = f1;
		this.beak.yRot = f1;

		if (passerine.isFlying()) {
			float f2 = ageInTicks * 0.3F;

			this.head.y = 20.0F + f2;
			this.crest.y = 20.0F + f2;
			this.beak.y = 20.0F + f2;
			this.body.y = 21.5F + f2;
			this.rightWing.y = 21.0F + f2;
			this.leftWing.y = 21.0F + f2;
			this.rightFoot.y = 23.0F + f2;
			this.leftFoot.y = 23.0F + f2;
			this.tail.y = 22.0F + f2;

			this.tail.xRot += Mth.cos(limbSwing * 0.6662F) * 0.35F * limbSwingAmount;

			this.rightWing.zRot = 0.2618F + ageInTicks;
			this.leftWing.zRot = -0.2618F - ageInTicks;
		} else {
			this.rightFoot.xRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
			this.leftFoot.xRot += Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		}
	}
}