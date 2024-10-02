package com.mrbysco.generikmobs.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.client.model.BoogerModel;
import com.mrbysco.generikmobs.client.model.layer.BoogerSnotLayer;
import com.mrbysco.generikmobs.entities.Booger;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BoogerRenderer extends MobRenderer<Booger, BoogerModel<Booger>> {
	private static final ResourceLocation HEAD_LOCATION = new ResourceLocation(GenerikMod.MOD_ID, "textures/entity/booger/booger_head.png");

	public BoogerRenderer(EntityRendererProvider.Context context) {
		super(context, new BoogerModel<>(context.bakeLayer(ClientHandler.BOOGER)), 0.25F);
		this.addLayer(new BoogerSnotLayer<>(this, context.getModelSet(), context.getItemRenderer()));
	}

	@Override
	public void render(Booger booger, float entityYaw, float partialTicks, PoseStack poseStack,
	                   MultiBufferSource bufferSource, int packedLight) {
		this.shadowRadius = 0.25F * (float) booger.getSize();
		super.render(booger, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	protected void scale(Booger booger, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(0.999F, 0.999F, 0.999F);
		poseStack.translate(0.0F, 0.001F, 0.0F);
		float f1 = (float) booger.getSize();
		float f2 = Mth.lerp(partialTickTime, booger.oSquish, booger.squish) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(Booger booger) {
		return HEAD_LOCATION;
	}
}