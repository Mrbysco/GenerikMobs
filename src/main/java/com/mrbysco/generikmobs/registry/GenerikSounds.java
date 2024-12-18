package com.mrbysco.generikmobs.registry;

import com.mrbysco.generikmobs.GenerikMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GenerikSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, GenerikMod.MOD_ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> BOOGER_EATER_HURT = registerSound("booger_eater.hurt");
	public static final DeferredHolder<SoundEvent, SoundEvent> BOOGER_EATER_IDLE = registerSound("booger_eater.idle");
	public static final DeferredHolder<SoundEvent, SoundEvent> BOOGER_EATER_DEATH = registerSound("booger_eater.death");

	public static DeferredHolder<SoundEvent, SoundEvent> registerSound(String name) {
		return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(GenerikMod.modLoc(name)));
	}
}
