package com.mrbysco.generikmobs.client.model;

import com.google.common.collect.ImmutableList;
import com.mrbysco.generikmobs.entities.Chet;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;

public class ChetModel extends PlayerModel<Chet> {
	public ChetModel(ModelPart modelPart, boolean slim) {
		super(modelPart, slim);

		this.hat.setRotation(0.0F, -1.75F, 0.0F);
		this.rightSleeve.setRotation(-5.0F, 2.0F, 0.0F);
	}

	@Override
	public void setupAnim(Chet chet, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(chet, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.head, this.hat);
	}
}
