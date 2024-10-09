package com.mrbysco.generikmobs.registry;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.entities.Booger;
import com.mrbysco.generikmobs.entities.BoogerEater;
import com.mrbysco.generikmobs.entities.projectile.BoogerProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GenerikMobs {
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GenerikMod.MOD_ID);

	public static final RegistryObject<EntityType<BoogerEater>> BOOGER_EATER = ENTITY_TYPES.register("booger_eater", () ->
			EntityType.Builder.<BoogerEater>of(BoogerEater::new, MobCategory.MONSTER)
					.sized(0.5F, 1.0F).clientTrackingRange(10)
					.build("booger_eater"));

	public static final RegistryObject<EntityType<Booger>> BOOGER = ENTITY_TYPES.register("booger", () ->
			EntityType.Builder.<Booger>of(Booger::new, MobCategory.MONSTER)
					.sized(3.5F, 3.5F).clientTrackingRange(10)
					.build("booger"));

	public static final RegistryObject<EntityType<BoogerProjectile>> BOOGER_PROJECTILE = ENTITY_TYPES.register("booger_projectile", () ->
			EntityType.Builder.<BoogerProjectile>of(BoogerProjectile::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
					.build("booger_projectile"));


	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(BOOGER_EATER.get(), BoogerEater.registerAttributes().build());
		event.put(BOOGER.get(), Booger.createAttributes().build());
	}
}
