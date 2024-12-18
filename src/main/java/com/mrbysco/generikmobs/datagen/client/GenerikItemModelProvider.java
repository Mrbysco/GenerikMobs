package com.mrbysco.generikmobs.datagen.client;

import com.mrbysco.generikmobs.GenerikMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GenerikItemModelProvider extends ItemModelProvider {
	public GenerikItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, GenerikMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		withExistingParent("slime_puddle", modLoc("block/slime_puddle"));
	}
}
