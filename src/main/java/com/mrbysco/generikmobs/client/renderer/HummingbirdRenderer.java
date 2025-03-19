package com.mrbysco.generikmobs.client.renderer;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.client.model.HummingbirdModel;
import com.mrbysco.generikmobs.entities.Hummingbird;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class HummingbirdRenderer extends MobRenderer<Hummingbird, HummingbirdModel<Hummingbird>> {
	private static final ResourceLocation BOOGER_EATER_TEXTURE = GenerikMod.modLoc("textures/entity/hummingbird/hummingbird.png");
	public HummingbirdRenderer(EntityRendererProvider.Context context) {
		super(context, new HummingbirdModel<>(context.bakeLayer(ClientHandler.HUMMINGBIRD)), 0.3F);
	}

	@Override
	public ResourceLocation getTextureLocation(Hummingbird pEntity) {
		return BOOGER_EATER_TEXTURE;
	}
}
