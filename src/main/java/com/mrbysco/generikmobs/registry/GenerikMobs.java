package com.mrbysco.generikmobs.registry;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.entities.Booger;
import com.mrbysco.generikmobs.entities.BoogerEater;
import com.mrbysco.generikmobs.entities.Chet;
import com.mrbysco.generikmobs.entities.projectile.BoogerProjectile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GenerikMobs {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, GenerikMod.MOD_ID);

	public static final DeferredHolder<EntityType<?>, EntityType<BoogerEater>> BOOGER_EATER = ENTITY_TYPES.register("booger_eater", () ->
			EntityType.Builder.<BoogerEater>of(BoogerEater::new, MobCategory.MONSTER)
					.sized(0.5F, 1.0F).clientTrackingRange(10)
					.build("booger_eater"));

	public static final DeferredHolder<EntityType<?>, EntityType<Booger>> BOOGER = ENTITY_TYPES.register("booger", () ->
			EntityType.Builder.<Booger>of(Booger::new, MobCategory.MONSTER)
					.sized(0.52F, 0.52F).eyeHeight(0.325F).spawnDimensionsScale(4.0F).clientTrackingRange(10)
					.build("booger"));

	public static final DeferredHolder<EntityType<?>, EntityType<BoogerProjectile>> BOOGER_PROJECTILE = ENTITY_TYPES.register("booger_projectile", () ->
			EntityType.Builder.<BoogerProjectile>of(BoogerProjectile::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
					.build("booger_projectile"));

	public static final DeferredHolder<EntityType<?>, EntityType<Chet>> CHET = ENTITY_TYPES.register("chet", () ->
			EntityType.Builder.<Chet>of(Chet::new, MobCategory.MONSTER)
					.sized(0.6F, 1.8F)
					.clientTrackingRange(32)
					.updateInterval(2)
					.build("chet"));


	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(BOOGER_EATER.get(), BoogerEater.registerAttributes().build());
		event.put(BOOGER.get(), Booger.createAttributes().build());
		event.put(CHET.get(), Chet.createMobAttributes().build());
	}
}
