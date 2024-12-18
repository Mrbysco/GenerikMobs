package com.mrbysco.generikmobs.datagen;

import com.mrbysco.generikmobs.datagen.client.GenerikBlockStateProvider;
import com.mrbysco.generikmobs.datagen.client.GenerikItemModelProvider;
import com.mrbysco.generikmobs.datagen.client.GenerikLanguageProvider;
import com.mrbysco.generikmobs.datagen.client.GenerikSoundProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GenerikDataGenerator {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {

		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new GenerikLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new GenerikSoundProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new GenerikBlockStateProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new GenerikItemModelProvider(packOutput, helper));
		}
	}
}
