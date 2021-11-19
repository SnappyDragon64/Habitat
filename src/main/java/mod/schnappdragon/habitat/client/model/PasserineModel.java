package mod.schnappdragon.habitat.client.model;

import mod.schnappdragon.habitat.common.entity.monster.Pooka;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
//EXTENDS PASSERINE
public class PasserineModel<T extends Pooka> extends HierarchicalModel<T> {
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
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F), PartPose.offset(0.0F, -3.0F, -1.0F));
		partdefinition.addOrReplaceChild("crest", CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, -10.0F, -4.0F, 0.0F, 5.0F, 5.0F), PartPose.offset(0.0F, -3.0F, -1.0F));
		partdefinition.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -5.0F, -5.0F, 1.0F, 1.0F, 1.0F), PartPose.offset(0.0F, -3.0F, -1.0F));
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(9, 9).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 3.0F, 4.0F), PartPose.offset(0.0F, -2.5F, 0.0F));
		partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(-2.0F, -4.0F, -1.0F, -0.2618F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 4.0F), PartPose.offsetAndRotation(2.0F, -4.0F, -1.0F, -0.2618F, 0.0F, 0.0F));
		partdefinition.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(14, 6).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.0F, 0.2618F, 0.0F));
		partdefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(14, 6).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(0.0F, -1.0F, 1.0F, 0.0F, -0.2618F, 0.0F));
		partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 5.0F), PartPose.offsetAndRotation(0.0F, -2.0F, 2.0F, 0.5236F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public ModelPart root() {
		return this.root;
	}

	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// after entity
	}
}