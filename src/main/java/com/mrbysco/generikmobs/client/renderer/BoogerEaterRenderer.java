package com.mrbysco.generikmobs.client.renderer;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.client.model.BoogerEaterModel;
import com.mrbysco.generikmobs.entities.BoogerEater;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BoogerEaterRenderer extends MobRenderer<BoogerEater, BoogerEaterModel<BoogerEater>> {
	private static final ResourceLocation BOOGER_EATER_TEXTURE = new ResourceLocation(GenerikMod.MOD_ID, "textures/entity/booger_eater/booger_eater.png");
	public BoogerEaterRenderer(EntityRendererProvider.Context context) {
		super(context, new BoogerEaterModel<>(context.bakeLayer(ClientHandler.BOOGER_EATER)), 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(BoogerEater pEntity) {
		return BOOGER_EATER_TEXTURE;
	}
}
