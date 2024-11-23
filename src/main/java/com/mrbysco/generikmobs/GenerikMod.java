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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(GenerikMod.MOD_ID)
public class GenerikMod {
	public static final String MOD_ID = "generikmobs";
	public static final Logger LOGGER = LogUtils.getLogger();

	public GenerikMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		GenerikRegistry.BLOCKS.register(eventBus);
		GenerikRegistry.ITEMS.register(eventBus);
		GenerikMobs.ENTITY_TYPES.register(eventBus);
		GenerikSounds.SOUND_EVENTS.register(eventBus);
		GenerikSerializers.ENTITY_DATA_SERIALIZER.register(eventBus);

		eventBus.addListener(GenerikMobs::registerAttributes);
		MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
			MinecraftForge.EVENT_BUS.addListener(ClientHandler::onLogin);
			MinecraftForge.EVENT_BUS.addListener(ClientHandler::onRespawn);
		}
	}

	public void serverAboutToStart(final ServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		ProfileUtil.setup(server.getProfileCache(), server.getSessionService(), server);
		GameProfileCache.setUsesAuthentication(server.usesAuthentication());
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
