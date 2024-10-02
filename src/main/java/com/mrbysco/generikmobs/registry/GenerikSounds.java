package com.mrbysco.generikmobs.registry;

import com.mrbysco.generikmobs.GenerikMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GenerikSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GenerikMod.MOD_ID);

	public static final RegistryObject<SoundEvent> BOOGER_EATER_HURT = registerSound("booger_eater.hurt");
	public static final RegistryObject<SoundEvent> BOOGER_EATER_IDLE = registerSound("booger_eater.idle");
	public static final RegistryObject<SoundEvent> BOOGER_EATER_DEATH = registerSound("booger_eater.death");

	public static RegistryObject<SoundEvent> registerSound(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(GenerikMod.modLoc(name)));
	}
}
