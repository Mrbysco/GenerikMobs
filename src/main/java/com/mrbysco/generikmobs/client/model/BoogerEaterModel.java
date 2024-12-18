package com.mrbysco.generikmobs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.generikmobs.client.animation.BoogerEaterAnimations;
import com.mrbysco.generikmobs.entities.BoogerEater;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BoogerEaterModel<T extends BoogerEater> extends HierarchicalModel<T> {
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart head_rotation;
	private final ModelPart propeller;
	private final ModelPart left_arm;
	private final ModelPart left_arm2;
	private final ModelPart right_arm;
	private final ModelPart right_arm_2;
	private final ModelPart booger;
	private final ModelPart right_leg;
	private final ModelPart left_leg;

	public BoogerEaterModel(ModelPart root) {
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.head = this.body.getChild("head");
		this.head_rotation = this.head.getChild("head_rotation");
		this.propeller = this.head_rotation.getChild("propeller");
		this.left_arm = this.body.getChild("left_arm");
		this.left_arm2 = this.left_arm.getChild("left_arm2");
		this.right_arm = this.body.getChild("right_arm");
		this.right_arm_2 = this.right_arm.getChild("right_arm_2");
		this.booger = this.right_arm_2.getChild("booger");
		this.right_leg = this.all.getChild("right_leg");
		this.left_leg = this.all.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 16).addBox(-3.0F, -5.0F, -2.5F, 6.0F, 6.0F, 5.0F),
				PartPose.offset(0.0F, -5.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition head_rotation = head.addOrReplaceChild("head_rotation", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F)
						.texOffs(0, 34).addBox(-2.0F, -10.0F, -2.0F, 4.0F, 2.0F, 4.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		head_rotation.addOrReplaceChild("cube_r1", CubeListBuilder.create()
						.texOffs(0, 0).mirror().addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F).mirror(false),
				PartPose.offsetAndRotation(-4.0F, -3.5F, -0.5F, 0.0F, 0.2182F, -0.0436F));

		head_rotation.addOrReplaceChild("cube_r2", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F),
				PartPose.offsetAndRotation(4.0F, -3.5F, -0.5F, 0.0F, -0.2182F, 0.0436F));

		head_rotation.addOrReplaceChild("cube_r3", CubeListBuilder.create()
						.texOffs(-3, 40).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 0.0F, 3.0F),
				PartPose.offsetAndRotation(0.0F, -8.0F, -2.0F, -0.1134F, 0.0F, 0.0F));

		head_rotation.addOrReplaceChild("cube_r4", CubeListBuilder.create()
						.texOffs(17, 39).addBox(0.0F, -0.25F, -0.5F, 0.0F, 1.0F, 1.0F),
				PartPose.offsetAndRotation(0.0F, -10.25F, -0.1F, 0.0F, 0.7854F, 0.0F));

		PartDefinition propeller = head_rotation.addOrReplaceChild("propeller", CubeListBuilder.create(),
				PartPose.offset(0.0F, -10.25F, -0.1F));

		propeller.addOrReplaceChild("cube_r5", CubeListBuilder.create()
						.texOffs(13, 35).addBox(-0.5F, -0.25F, -2.5F, 1.0F, 0.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(),
				PartPose.offsetAndRotation(3.3F, -4.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create()
						.texOffs(22, 16).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-3.3F, -4.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition right_arm_2 = right_arm.addOrReplaceChild("right_arm_2", CubeListBuilder.create()
						.texOffs(24, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		right_arm_2.addOrReplaceChild("booger", CubeListBuilder.create()
						.texOffs(36, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F),
				PartPose.offset(0.0F, 3.0F, 1.0F));

		all.addOrReplaceChild("right_leg", CubeListBuilder.create()
						.texOffs(0, 27).addBox(-1.5F, 1.0F, -1.5F, 3.0F, 4.0F, 3.0F),
				PartPose.offset(-1.5F, -5.0F, 0.0F));

		all.addOrReplaceChild("left_leg", CubeListBuilder.create()
						.texOffs(19, 25).addBox(-1.5F, 1.0F, -1.5F, 3.0F, 4.0F, 3.0F),
				PartPose.offset(1.5F, -5.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(BoogerEaterAnimations.WALK, limbSwing, limbSwingAmount, 1.0F, 1.0F);
		this.animate(entity.idleAnimationState, BoogerEaterAnimations.IDLE, ageInTicks);
		this.animate(entity.throwAnimationState, BoogerEaterAnimations.THROW, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return this.all;
	}
}