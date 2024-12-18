package com.mrbysco.generikmobs.datagen.client;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.registry.GenerikRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class GenerikBlockStateProvider extends BlockStateProvider {
	public GenerikBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, GenerikMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		model(GenerikRegistry.SLIME_PUDDLE);
	}

	protected void model(DeferredHolder<Block, ? extends Block> registryObject) {
		ModelFile file = models().getExistingFile(registryObject.getId());
		getVariantBuilder(registryObject.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(file).build());
	}
}
