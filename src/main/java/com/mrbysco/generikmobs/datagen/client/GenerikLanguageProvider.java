package com.mrbysco.generikmobs.datagen.client;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import com.mrbysco.generikmobs.registry.GenerikSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class GenerikLanguageProvider extends LanguageProvider {

	public GenerikLanguageProvider(PackOutput packOutput) {
		super(packOutput, GenerikMod.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.addEntityType(GenerikMobs.BOOGER, "Booger");
		this.addEntityType(GenerikMobs.BOOGER_EATER, "Booger Eater");
		this.addEntityType(GenerikMobs.BOOGER_PROJECTILE, "Booger Projectile");

		this.addSubtitle(GenerikSounds.BOOGER_EATER_IDLE, "Booger Eater annoys");
		this.addSubtitle(GenerikSounds.BOOGER_EATER_HURT, "Booger Eater hurts");
		this.addSubtitle(GenerikSounds.BOOGER_EATER_DEATH, "Booger Eater dies");
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event
	 * @param text  The subtitle text
	 */
	public void addSubtitle(RegistryObject<SoundEvent> sound, String text) {
		this.addSubtitle(sound.get(), text);
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	public void addSubtitle(SoundEvent sound, String text) {
		String path = GenerikMod.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, text);
	}
}
