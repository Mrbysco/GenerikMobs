package com.mrbysco.generikmobs.client;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.client.model.BoogerEaterModel;
import com.mrbysco.generikmobs.client.model.BoogerModel;
import com.mrbysco.generikmobs.client.model.BoogerProjectileModel;
import com.mrbysco.generikmobs.client.model.TexChetModel;
import com.mrbysco.generikmobs.client.renderer.BoogerEaterRenderer;
import com.mrbysco.generikmobs.client.renderer.BoogerProjectileRenderer;
import com.mrbysco.generikmobs.client.renderer.BoogerRenderer;
import com.mrbysco.generikmobs.client.renderer.ChetRenderer;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import com.mrbysco.generikmobs.util.ProfileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.server.Services;
import net.minecraft.server.players.GameProfileCache;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation BOOGER_EATER = new ModelLayerLocation(GenerikMod.modLoc("booger_eater"), "main");
	public static final ModelLayerLocation BOOGER_PROJECTILE = new ModelLayerLocation(GenerikMod.modLoc("booger_projectile"), "main");
	public static final ModelLayerLocation BOOGER = new ModelLayerLocation(GenerikMod.modLoc("booger"), "main");
	public static final ModelLayerLocation BOOGER_OUTER = new ModelLayerLocation(GenerikMod.modLoc("booger"), "outer");
	public static final ModelLayerLocation TEX_CHET = new ModelLayerLocation(GenerikMod.modLoc("chet"), "tex");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(GenerikMobs.BOOGER_EATER.get(), BoogerEaterRenderer::new);
		event.registerEntityRenderer(GenerikMobs.BOOGER_PROJECTILE.get(), BoogerProjectileRenderer::new);
		event.registerEntityRenderer(GenerikMobs.BOOGER.get(), BoogerRenderer::new);
		event.registerEntityRenderer(GenerikMobs.CHET.get(), ChetRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(BOOGER_EATER, BoogerEaterModel::createBodyLayer);
		event.registerLayerDefinition(BOOGER_PROJECTILE, BoogerProjectileModel::createBodyLayer);
		event.registerLayerDefinition(BOOGER, BoogerModel::createBodyLayer);
		event.registerLayerDefinition(BOOGER_OUTER, BoogerModel::createSnotLayer);
		event.registerLayerDefinition(TEX_CHET, TexChetModel::createBodyLayer);
	}

	public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
		Minecraft mc = Minecraft.getInstance();
		if (!mc.isLocalServer()) {
			setPlayerCache(mc);
		}
	}

	public static void onRespawn(ClientPlayerNetworkEvent.Clone event) {
		Minecraft mc = Minecraft.getInstance();
		if (!mc.isLocalServer()) {
			setPlayerCache(mc);
		}
	}

	private static void setPlayerCache(Minecraft mc) {
		YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(mc.getProxy());
		Services services = Services.create(authenticationService, mc.gameDirectory);
		services.profileCache().setExecutor(mc);
		ProfileUtil.setup(services, mc);
		GameProfileCache.setUsesAuthentication(false);
	}
}
