package com.mrbysco.generikmobs.datagen.client;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.registry.GenerikSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class GenerikSoundProvider extends SoundDefinitionsProvider {

	public GenerikSoundProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
		super(packOutput, GenerikMod.MOD_ID, existingFileHelper);
	}

	@Override
	public void registerSounds() {
		this.add(GenerikSounds.BOOGER_EATER_IDLE, definition()
				.subtitle(modSubtitle(GenerikSounds.BOOGER_EATER_IDLE.getId()))
				.with(
						sound(modLoc("hermitcraft")).pitch(0.8),
						sound(modLoc("meee1")).pitch(0.8),
						sound(modLoc("meee2")).pitch(0.8),
						sound(modLoc("meee3")).pitch(0.8),
						sound(modLoc("meee4")).pitch(0.8),
						sound(modLoc("meeegrowl")).pitch(0.8),
						sound(modLoc("server")).pitch(0.8)
				));

		this.add(GenerikSounds.BOOGER_EATER_HURT, definition()
				.subtitle(modSubtitle(GenerikSounds.BOOGER_EATER_HURT.getId()))
				.with(
						sound(modLoc("hurt1")).pitch(0.8),
						sound(modLoc("hurt2")).pitch(0.8),
						sound(modLoc("hurt2")).pitch(0.8),
						sound(modLoc("growl1")).pitch(0.8),
						sound(modLoc("growl2")).pitch(0.8)
				));

		this.add(GenerikSounds.BOOGER_EATER_DEATH, definition()
				.subtitle(modSubtitle(GenerikSounds.BOOGER_EATER_DEATH.getId()))
				.with(
						sound(modLoc("death1")).pitch(0.8),
						sound(modLoc("death2")).pitch(0.8),
						sound(modLoc("death3")).pitch(0.8),
						sound(modLoc("death4")).pitch(0.8)
				));

	}


	public String modSubtitle(ResourceLocation id) {
		return GenerikMod.MOD_ID + ".subtitle." + id.getPath();
	}

	public ResourceLocation modLoc(String name) {
		return GenerikMod.modLoc(name);
	}
}
