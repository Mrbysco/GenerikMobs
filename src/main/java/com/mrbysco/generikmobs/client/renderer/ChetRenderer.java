package com.mrbysco.generikmobs.client.renderer;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.client.model.ChetModel;
import com.mrbysco.generikmobs.client.model.TexChetModel;
import com.mrbysco.generikmobs.entities.Chet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class ChetRenderer extends LivingEntityRenderer<Chet, EntityModel<Chet>> {
	private final ChetModel chetModel;
	private final ChetModel chetSlimModel;
	private final TexChetModel texModel;
	public static final ResourceLocation defaultTexture = GenerikMod.modLoc("textures/entity/chet/generikskin1.png");
	public static final ResourceLocation otherTexture = GenerikMod.modLoc("textures/entity/chet/generikskin2.png");

	@SuppressWarnings("unchecked")
	public ChetRenderer(EntityRendererProvider.Context context) {
		super(context, new TexChetModel(context.bakeLayer(ClientHandler.TEX_CHET)), 0.5F);
		this.chetModel = new ChetModel(context.bakeLayer(ModelLayers.PLAYER), false);
		this.chetSlimModel = new ChetModel(context.bakeLayer(ModelLayers.PLAYER), true);
		this.texModel = new TexChetModel(context.bakeLayer(ClientHandler.TEX_CHET));

//		this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
//		this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
	}

	private ResourceLocation getSkin(GameProfile gameProfile) {
		if (!gameProfile.isComplete()) {
			return defaultTexture;
		} else {
			final Minecraft minecraft = Minecraft.getInstance();
			SkinManager skinManager = minecraft.getSkinManager();
			final Map<Type, MinecraftProfileTexture> loadSkinFromCache = skinManager.getInsecureSkinInformation(gameProfile); // returned map may or may not be typed
			if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
				return skinManager.registerTexture(loadSkinFromCache.get(Type.SKIN), Type.SKIN);
			} else {
				return DefaultPlayerSkin.getDefaultSkin(gameProfile.getId());
			}
		}
	}

	@Override
	public void render(Chet chet, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn) {
		if (chet.isTexMex()) {
			this.model = this.texModel;
		} else {
			this.model = chet.isSlim() ? this.chetModel : chetSlimModel;
		}
		super.render(chet, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
	}

	@Override
	protected void scale(Chet chet, PoseStack poseStack, float partialTickTime) {
		float f = 0.9375F;
		poseStack.scale(f, f, f);
	}

	@Override
	public ResourceLocation getTextureLocation(Chet chet) {
		if (chet.isTexMex()) {
			return otherTexture;
		}
		return chet.getGameProfile()
				.map(this::getSkin)
				.orElse(defaultTexture);
	}

	@Override
	protected boolean shouldShowName(Chet chet) {
		return chet.isCustomNameVisible();
	}
}
