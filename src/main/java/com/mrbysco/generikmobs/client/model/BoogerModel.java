package com.mrbysco.generikmobs.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.generikmobs.entities.Booger;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BoogerModel<T extends Booger> extends HierarchicalModel<T> implements HeadedModel {
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart head;

	public BoogerModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.head = this.main.hasChild("head") ? this.main.getChild("head") : null;
	}

	public static LayerDefinition createSnotLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		main.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.5F))
						.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		main.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 16).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 16.0F, 16.0F),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		main.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F,
								new CubeDeformation(-0.5F))
						.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		main.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 16).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 16.0F, 16.0F),
				PartPose.offset(0.0F, -4.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void translateToMain(PoseStack poseStack) {
		this.main.translateAndRotate(poseStack);
	}

	@Override
	public void setupAnim(T booger, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (this.head != null) {
			this.head.visible = booger.getCustomHead().isEmpty();
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public ModelPart getHead() {
		return head;
	}
}