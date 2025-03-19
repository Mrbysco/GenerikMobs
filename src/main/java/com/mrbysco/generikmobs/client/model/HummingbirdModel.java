package com.mrbysco.generikmobs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.generikmobs.client.animation.BoogerEaterAnimations;
import com.mrbysco.generikmobs.client.animation.HummingbirdAnimations;
import com.mrbysco.generikmobs.entities.Hummingbird;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HummingbirdModel<T extends Hummingbird> extends HierarchicalModel<T> {
	private final ModelPart main;
	private final ModelPart main_2;
	private final ModelPart body;
	private final ModelPart body_2;
	private final ModelPart tail;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart right_wing;
	private final ModelPart left_wing;
	private final ModelPart head_animation;
	private final ModelPart head;
	private final ModelPart beak;
	private final ModelPart cigar;
	private final ModelPart smoke;
	private final ModelPart right_eyebrow;
	private final ModelPart left_eyebrow;

	public HummingbirdModel(ModelPart root) {
		this.main = root.getChild("main");
		this.main_2 = this.main.getChild("main_2");
		this.body = this.main_2.getChild("body");
		this.body_2 = this.body.getChild("body_2");
		this.tail = this.body_2.getChild("tail");
		this.left_leg = this.body_2.getChild("left_leg");
		this.right_leg = this.body_2.getChild("right_leg");
		this.right_wing = this.body.getChild("right_wing");
		this.left_wing = this.body.getChild("left_wing");
		this.head_animation = this.main_2.getChild("head_animation");
		this.head = this.head_animation.getChild("head");
		this.beak = this.head.getChild("beak");
		this.cigar = this.beak.getChild("cigar");
		this.smoke = this.cigar.getChild("smoke");
		this.right_eyebrow = this.head.getChild("right_eyebrow");
		this.left_eyebrow = this.head.getChild("left_eyebrow");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition main_2 = main.addOrReplaceChild("main_2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = main_2.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.6F, -2.0F));

		PartDefinition body_2 = body.addOrReplaceChild("body_2", CubeListBuilder.create().texOffs(16, 14).addBox(-1.5F, -1.0F, -2.5F, 3.0F, 6.0F, 4.0F)
				.texOffs(0, 14).addBox(-2.0F, -1.1F, -2.8F, 4.0F, 6.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition tail = body_2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(16, 30).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(0.0F, 5.0F, 1.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition left_leg = body_2.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(12, 14).mirror().addBox(-1.0F, -0.6F, -1.0F, 2.0F, 2.0F, 2.0F).mirror(false), PartPose.offsetAndRotation(1.4F, 5.1F, -2.4F, -1.2654F, -0.2182F, 0.0F));

		PartDefinition right_leg = body_2.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(12, 14).addBox(-1.0F, -0.6F, -1.0F, 2.0F, 2.0F, 2.0F), PartPose.offsetAndRotation(-1.4F, 5.1F, -2.4F, -1.2654F, 0.2182F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(18, 7).mirror().addBox(-5.3F, -0.5F, -1.0F, 6.0F, 1.0F, 3.0F).mirror(false)
				.texOffs(0, 0).addBox(-12.3F, 0.0F, -1.0F, 11.0F, 0.0F, 7.0F), PartPose.offsetAndRotation(-2.1F, 0.9F, -1.0F, -0.4363F, 0.0873F, 0.0873F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(18, 7).addBox(-0.7F, -0.5F, -1.0F, 6.0F, 1.0F, 3.0F)
				.texOffs(0, 0).mirror().addBox(1.3F, 0.0F, -1.0F, 11.0F, 0.0F, 7.0F).mirror(false), PartPose.offsetAndRotation(2.1F, 0.9F, -1.0F, -0.4363F, -0.0873F, -0.0873F));

		PartDefinition head_animation = main_2.addOrReplaceChild("head_animation", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -2.0F));

		PartDefinition head = head_animation.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 3.0F, 4.0F)
				.texOffs(16, 24).addBox(-2.0F, -2.5F, -3.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.01F))
				.texOffs(26, 11).addBox(-2.0F, -4.0F, -3.0F, 4.0F, 2.0F, 4.0F)
				.texOffs(28, 20).addBox(-2.0F, -3.2F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(0, 7).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 1.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(29, 0).mirror().addBox(0.0F, -1.0F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -4.0F, -1.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(29, 0).addBox(-3.0F, -1.0F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.0F, -4.0F, -1.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(30, 25).addBox(-0.5F, -4.0F, -8.0F, 1.0F, 1.0F, 5.0F), PartPose.offset(0.0F, 2.6F, 0.0F));

		PartDefinition cigar = beak.addOrReplaceChild("cigar", CubeListBuilder.create().texOffs(0, 0).addBox(0.75F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F)
				.texOffs(18, 11).addBox(-0.25F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.35F, -3.2F, -4.1F, 0.1745F, 0.5672F, 0.3491F));

		PartDefinition smoke = cigar.addOrReplaceChild("smoke", CubeListBuilder.create().texOffs(0, 2).addBox(-0.4F, -3.6F, 0.0F, 1.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(2.65F, 0.1F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition right_eyebrow = head.addOrReplaceChild("right_eyebrow", CubeListBuilder.create().texOffs(12, 24).mirror().addBox(-0.0326F, -0.2008F, -0.55F, 2.0F, 1.0F, 2.0F).mirror(false), PartPose.offsetAndRotation(-2.0F, -2.4F, -2.5F, 0.0F, 0.0F, 0.0873F));

		PartDefinition left_eyebrow = head.addOrReplaceChild("left_eyebrow", CubeListBuilder.create().texOffs(12, 24).addBox(-1.9674F, -0.2008F, -0.55F, 2.0F, 1.0F, 2.0F), PartPose.offsetAndRotation(2.0F, -2.4F, -2.5F, 0.0F, 0.0F, -0.0873F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.idleAnimationState, HummingbirdAnimations.FLY, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}

	@Override
	public ModelPart root() {
		return this.main;
	}
}