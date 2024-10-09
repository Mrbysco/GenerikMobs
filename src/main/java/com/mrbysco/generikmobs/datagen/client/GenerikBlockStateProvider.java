package com.mrbysco.generikmobs.datagen.client;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.registry.GenerikRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class GenerikBlockStateProvider extends BlockStateProvider {
	public GenerikBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, GenerikMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		model(GenerikRegistry.SLIME_PUDDLE);
	}

	protected void model(RegistryObject<? extends Block> registryObject) {
		ModelFile file = models().getExistingFile(registryObject.getId());
		getVariantBuilder(registryObject.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(file).build());
	}
}
