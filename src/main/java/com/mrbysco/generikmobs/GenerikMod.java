package com.mrbysco.generikmobs;

import com.mojang.logging.LogUtils;
import com.mrbysco.generikmobs.client.ClientHandler;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import com.mrbysco.generikmobs.registry.GenerikRegistry;
import com.mrbysco.generikmobs.registry.GenerikSounds;
import net.minecraft.resources.ResourceLocation;
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

		eventBus.addListener(GenerikMobs::registerAttributes);

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
		}
	}

	public static ResourceLocation modLoc(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
