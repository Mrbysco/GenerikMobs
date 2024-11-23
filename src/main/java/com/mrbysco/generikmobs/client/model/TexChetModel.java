package com.mrbysco.generikmobs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.generikmobs.client.animation.TexChetAnimation;
import com.mrbysco.generikmobs.entities.Chet;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

public class TexChetModel extends HierarchicalModel<Chet> implements HeadedModel, ArmedModel {
	private final ModelPart main;
	private final ModelPart right_leg;
	private final ModelPart right_pants;
	private final ModelPart left_leg;
	private final ModelPart left_pants;
	private final ModelPart body;
	private final ModelPart jacket;
	private final ModelPart left_arm;
	private final ModelPart left_sleve;
	private final ModelPart right_arm;
	private final ModelPart right_sleve;
	private final ModelPart head;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart headwear;

	public TexChetModel(ModelPart root) {
		this.main = root.getChild("main");
		this.right_leg = this.main.getChild("right_leg");
		this.right_pants = this.right_leg.getChild("right_pants");
		this.left_leg = this.main.getChild("left_leg");
		this.left_pants = this.left_leg.getChild("left_pants");
		this.body = this.main.getChild("body");
		this.jacket = this.body.getChild("jacket");
		this.left_arm = this.body.getChild("left_arm");
		this.left_sleve = this.left_arm.getChild("left_sleve");
		this.right_arm = this.body.getChild("right_arm");
		this.right_sleve = this.right_arm.getChild("right_sleve");
		this.head = this.body.getChild("head");
		this.bone = this.head.getChild("bone");
		this.bone2 = this.head.getChild("bone2");
		this.headwear = this.head.getChild("headwear");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_leg = main.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(48, 20).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, -12.0F, 0.0F));

		PartDefinition right_pants = right_leg.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg = main.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 52).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, -12.0F, 0.0F));

		PartDefinition left_pants = left_leg.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(48, 43).addBox(2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 43).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition jacket = body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 36).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 59).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, -10.0F, 0.0F));

		PartDefinition left_sleve = left_arm.addOrReplaceChild("left_sleve", CubeListBuilder.create().texOffs(32, 59).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 59).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, -10.0F, 0.0F));

		PartDefinition right_sleve = right_arm.addOrReplaceChild("right_sleve", CubeListBuilder.create().texOffs(54, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(24, 29).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F)
				.texOffs(0, 0).addBox(-9.0F, -8.0F, -9.0F, 18.0F, 2.0F, 18.0F)
				.texOffs(82, 30).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 8.0F)
				.texOffs(24, 20).addBox(-2.0F, -3.0F, -5.3F, 4.0F, 3.0F, 2.0F), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(66, 0).addBox(0.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F), PartPose.offsetAndRotation(0.0F, -0.8F, -5.3F, 0.0F, 0.2182F, 0.2182F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(66, 0).mirror().addBox(-4.0F, -1.0F, 0.0F, 4.0F, 2.0F, 0.0F).mirror(false), PartPose.offsetAndRotation(0.0F, -0.8F, -5.3F, 0.0F, -0.2182F, -0.2182F));

		PartDefinition headwear = head.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(0, 20).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Chet entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(TexChetAnimation.WALK, limbSwing, limbSwingAmount, 1.0F, 1.0F);
		this.animate(entity.idleAnimationState, TexChetAnimation.IDLE, ageInTicks);
	}

	@Override
	public ModelPart root() {
		return this.main;
	}

	@Override
	public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {

	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}
}