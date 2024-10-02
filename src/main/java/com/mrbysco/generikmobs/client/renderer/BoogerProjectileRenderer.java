package com.mrbysco.generikmobs.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.client.model.BoogerProjectileModel;
import com.mrbysco.generikmobs.entities.projectile.BoogerProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BoogerProjectileRenderer extends EntityRenderer<BoogerProjectile> {
	private static final ResourceLocation BOOGER_PROJECTILE_TEXTURE = new ResourceLocation(GenerikMod.MOD_ID, "textures/entity/booger_projectile/booger_projectile.png");
	private final BoogerProjectileModel<BoogerProjectile> model;

	public BoogerProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new BoogerProjectileModel<>(context.bakeLayer(ClientHandler.BOOGER_PROJECTILE));
	}

	public void render(BoogerProjectile boogerProjectile, float entityYaw, float partialTicks, PoseStack poseStack,
	                   MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();
		poseStack.translate(0.0F, -1.25F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, boogerProjectile.yRotO, boogerProjectile.getYRot()) - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, boogerProjectile.xRotO, boogerProjectile.getXRot())));
		this.model.setupAnim(boogerProjectile, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer vertexConsumer = bufferSource.getBuffer(this.model.renderType(BOOGER_PROJECTILE_TEXTURE));
		this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
		super.render(boogerProjectile, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(BoogerProjectile pEntity) {
		return BOOGER_PROJECTILE_TEXTURE;
	}
}
