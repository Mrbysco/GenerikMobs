package com.mrbysco.generikmobs.client.model.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.client.model.BoogerModel;
import com.mrbysco.generikmobs.entities.Booger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BoogerSnotLayer<T extends Booger> extends RenderLayer<T, BoogerModel<T>> {
	private static final ResourceLocation BOOGER_LOCATION = new ResourceLocation(GenerikMod.MOD_ID, "textures/entity/booger/booger_medium.png");
	private final BoogerModel<T> model;
	private final ItemRenderer itemRenderer;

	public BoogerSnotLayer(RenderLayerParent<T, BoogerModel<T>> renderer, EntityModelSet modelSet, ItemRenderer itemRenderer) {
		super(renderer);
		this.model = new BoogerModel<>(modelSet.bakeLayer(ClientHandler.BOOGER_OUTER));
		this.itemRenderer = itemRenderer;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T booger,
	                   float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
	                   float netHeadYaw, float headPitch) {
		Minecraft minecraft = Minecraft.getInstance();
		boolean flag = minecraft.shouldEntityAppearGlowing(booger) && booger.isInvisible();
		if (!booger.isInvisible() || flag) {
			if (!booger.getCustomHead().isEmpty()) {
				poseStack.pushPose();
				ItemStack headStack = booger.getCustomHead();
				this.model.translateToMain(poseStack);
				poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
				poseStack.translate(0.0D, 0.75D, 0.0D);
				this.itemRenderer.renderStatic(headStack, ItemDisplayContext.NONE, packedLight,
						LivingEntityRenderer.getOverlayCoords(booger, 0.0F), poseStack, bufferSource, booger.level(), 0);
				poseStack.popPose();
			}

			VertexConsumer vertexConsumer;
			if (flag) {
				vertexConsumer = bufferSource.getBuffer(RenderType.outline(this.getTextureLocation(booger)));
			} else {
				vertexConsumer = bufferSource.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(booger)));
			}
			this.getParentModel().copyPropertiesTo(this.model);
			this.model.prepareMobModel(booger, limbSwing, limbSwingAmount, partialTicks);
			this.model.setupAnim(booger, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(booger, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	protected ResourceLocation getTextureLocation(T entity) {
		return BOOGER_LOCATION;
	}
}