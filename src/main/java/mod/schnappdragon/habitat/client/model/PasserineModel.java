package mod.schnappdragon.habitat.client.model;

import mod.schnappdragon.habitat.common.entity.animal.Passerine;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

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
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -4.0F + 24.0F, -2.0F));
		partdefinition.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(22, 2).addBox(0.0F, -6.0F, -2.0F, 0.0F, 5.0F, 5.0F), PartPose.offset(0.0F, -4.0F + 24.0F, -2.0F));
		partdefinition.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -3.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, -4.0F + 24.0F, -2.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -2.5F + 24.0F, 0.0F, -0.0873F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 8).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(-2.0F, -3.0F + 24.0F, -1.0F, -0.1963F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(2.0F, -3.0F + 24.0F, -1.0F, -0.1963F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(12, 0).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -1.0F + 24.0F, 1.0F, 0.0F, 0.2618F, 0.0F));
		partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(12, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -1.0F + 24.0F, 1.0F, 0.0F, -0.2618F, 0.0F));
		partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(4, 8).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 0.0F, 6.0F), PartPose.offsetAndRotation(0.0F, -2.0F + 24.0F, 2.0F, 0.3927F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 16);
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void prepareMobModel(Passerine passerine, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
	}

	public void setupAnim(Passerine passerine, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// after entity
	}
}