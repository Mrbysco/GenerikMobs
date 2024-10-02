package com.mrbysco.generikmobs.client;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.model.BoogerEaterModel;
import com.mrbysco.generikmobs.client.model.BoogerModel;
import com.mrbysco.generikmobs.client.model.BoogerProjectileModel;
import com.mrbysco.generikmobs.client.renderer.BoogerEaterRenderer;
import com.mrbysco.generikmobs.client.renderer.BoogerProjectileRenderer;
import com.mrbysco.generikmobs.client.renderer.BoogerRenderer;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation BOOGER_EATER = new ModelLayerLocation(new ResourceLocation(GenerikMod.MOD_ID, "booger_eater"), "main");
	public static final ModelLayerLocation BOOGER_PROJECTILE = new ModelLayerLocation(new ResourceLocation(GenerikMod.MOD_ID, "booger_projectile"), "main");
	public static final ModelLayerLocation BOOGER = new ModelLayerLocation(new ResourceLocation(GenerikMod.MOD_ID, "booger"), "main");
	public static final ModelLayerLocation BOOGER_OUTER = new ModelLayerLocation(new ResourceLocation(GenerikMod.MOD_ID, "booger"), "outer");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(GenerikMobs.BOOGER_EATER.get(), BoogerEaterRenderer::new);
		event.registerEntityRenderer(GenerikMobs.BOOGER_PROJECTILE.get(), BoogerProjectileRenderer::new);
		event.registerEntityRenderer(GenerikMobs.BOOGER.get(), BoogerRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(BOOGER_EATER, BoogerEaterModel::createBodyLayer);
		event.registerLayerDefinition(BOOGER_PROJECTILE, BoogerProjectileModel::createBodyLayer);
		event.registerLayerDefinition(BOOGER, BoogerModel::createBodyLayer);
		event.registerLayerDefinition(BOOGER_OUTER, BoogerModel::createSnotLayer);
	}
}
