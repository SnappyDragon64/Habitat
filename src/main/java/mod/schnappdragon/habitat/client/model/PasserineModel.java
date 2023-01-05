package mod.schnappdragon.habitat.client.model;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PasserineModel<T extends Passerine> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightFoot;
	private final ModelPart leftFoot;
	private final ModelPart tail;

	public PasserineModel(ModelPart part) {
		this.root = part;
		this.head = part.getChild("head");
		this.body = part.getChild("body");
		this.tail = this.body.getChild("tail");
		this.rightWing = part.getChild("right_wing");
		this.leftWing = part.getChild("left_wing");
		this.rightFoot = part.getChild("right_foot");
		this.leftFoot = part.getChild("left_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 20.0F, -2.0F));
		head.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(22, 2).addBox(0.0F, -6.0F, -2.0F, 0.0F, 5.0F, 5.0F), PartPose.ZERO);
		head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.offset(0.0F, 21.5F, 0.0F));
		body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(5, 8).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F), PartPose.offset(0.0F, 0.5F, 2.0F));
		partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offset(-2.0F, 21.0F, -1.0F));
		partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offset(2.0F, 21.0F, -1.0F));
		partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(12, 0).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 23.0F, 1.0F));
		partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(12, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 23.0F, 1.0F));
		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void prepareMobModel(Passerine passerine, float limbSwing, float limbSwingAmount, float partialTick) {
		this.head.y = 20.0F;
		this.head.z = -2.0F;
		this.body.y = 21.5F;
		this.rightWing.y = 21.0F;
		this.leftWing.y = 21.0F;
		this.rightFoot.y = 23.0F;
		this.leftFoot.y = 23.0F;

		this.body.xRot = 0.0F;
		this.rightWing.xRot = 0.0F;
		this.leftWing.xRot = 0.0F;
		this.rightWing.zRot = 0.0F;
		this.leftWing.zRot = 0.0F;
		this.rightFoot.xRot = 0.0F;
		this.leftFoot.xRot = 0.0F;

		switch (getState(passerine)) {
			case SLEEPING:
				this.head.y = 21.0F;
				this.head.z = -1.5F;
				this.body.y = 22.5F;
				this.rightWing.y = 22.0F;
				this.leftWing.y = 22.0F;
				this.head.xRot = 0.3491F;
				this.head.yRot = 2.094F;
				this.tail.xRot = 0.1745F;
				break;
			case FLYING:
				this.body.xRot = -0.3927F;
				this.rightWing.xRot = -0.5236F;
				this.leftWing.xRot = -0.5236F;
				this.rightFoot.xRot = -0.5236F;
				this.leftFoot.xRot = -0.5236F;
				this.rightFoot.yRot = 0.1571F;
				this.leftFoot.yRot = -0.1571F;
				this.tail.xRot = 0.1309F;
				break;
			case PREENING:
				this.head.z = -1.0F;
				this.head.xRot = 0.1745F;
				this.head.yRot = 1.833F;
				this.rightWing.xRot = -0.5236F;
				this.rightWing.zRot = 1.396F;
			case STANDING:
				if (getState(passerine) != State.PREENING)
					this.rightWing.xRot = -0.1963F;
			default:
				this.body.xRot = -0.0873F;
				this.leftWing.xRot = -0.1963F;
				this.rightFoot.yRot = 0.1745F;
				this.leftFoot.yRot = -0.1745F;
				this.tail.xRot = 0.3927F;
		}
	}

	public void setupAnim(Passerine passerine, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (getState(passerine) == State.SLEEPING) {
			this.head.zRot = Mth.cos(ageInTicks * 0.027F) / 22.0F;
		} else if (getState(passerine) == PasserineModel.State.PREENING) {
			this.head.xRot += 0.1745F * (1.0F - Mth.cos(ageInTicks * 1.5F)) / 2.0F;
			this.head.yRot += 0.2793F * (1.0F - Mth.cos(ageInTicks)) / 2.0F;
		} else {
			this.head.xRot = headPitch * ((float) Math.PI / 180F);
			this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
			if (getState(passerine) == State.FLYING) {
				float f = ageInTicks * 0.2F;
				this.head.y = 20.0F + f;
				this.body.y = 21.5F + f;
				this.rightWing.y = 21.0F + f;
				this.leftWing.y = 21.0F + f;
				this.rightFoot.y = 23.0F + f;
				this.leftFoot.y = 23.0F + f;

				this.tail.xRot += Mth.cos(limbSwing * 0.6662F) * 0.35F * limbSwingAmount;

				this.rightWing.zRot = 0.2618F + ageInTicks;
				this.leftWing.zRot = -0.2618F - ageInTicks;
			} else {
				this.rightFoot.xRot += Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
				this.leftFoot.xRot += Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
			}
		}
	}

	private static State getState(Passerine passerine) {
		if (passerine.isPreening()) return State.PREENING;
		if (passerine.isAsleep()) return State.SLEEPING;
		if (passerine.isFlying()) return State.FLYING;
		return State.STANDING;
	}

	public enum State {
		FLYING,
		STANDING,
		SLEEPING,
		PREENING
	}
}