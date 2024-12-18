package com.mrbysco.generikmobs;

import com.mojang.logging.LogUtils;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import com.mrbysco.generikmobs.registry.GenerikRegistry;
import com.mrbysco.generikmobs.registry.GenerikSerializers;
import com.mrbysco.generikmobs.registry.GenerikSounds;
import com.mrbysco.generikmobs.util.ProfileUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.slf4j.Logger;

@Mod(GenerikMod.MOD_ID)
public class GenerikMod {
	public static final String MOD_ID = "generikmobs";
	public static final Logger LOGGER = LogUtils.getLogger();

	public GenerikMod(IEventBus eventBus, Dist dist, ModContainer container) {
		GenerikRegistry.BLOCKS.register(eventBus);
		GenerikRegistry.ITEMS.register(eventBus);
		GenerikMobs.ENTITY_TYPES.register(eventBus);
		GenerikSounds.SOUND_EVENTS.register(eventBus);
		GenerikSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);

		eventBus.addListener(GenerikMobs::registerAttributes);
		NeoForge.EVENT_BUS.addListener(this::serverAboutToStart);

		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			NeoForge.EVENT_BUS.addListener(ClientHandler::onLogin);
			NeoForge.EVENT_BUS.addListener(ClientHandler::onRespawn);
		}
	}

	public void serverAboutToStart(final ServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		ProfileUtil.setup(server.services, server);
		GameProfileCache.setUsesAuthentication(server.usesAuthentication());
	}

	public static ResourceLocation modLoc(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
