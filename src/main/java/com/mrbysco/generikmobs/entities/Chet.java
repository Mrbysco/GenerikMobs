package com.mrbysco.generikmobs.entities;

import com.mojang.authlib.properties.PropertyMap;
import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.registry.GenerikSerializers;
import com.mrbysco.generikmobs.util.ProfileUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Optional;

public class Chet extends Monster {
	private static final EntityDataAccessor<Optional<ResolvableProfile>> RESOLVABLE_PROFILE = SynchedEntityData.defineId(Chet.class, GenerikSerializers.OPTIONAL_RESOLVABLE_PROFILE.get());
	private static final EntityDataAccessor<Boolean> TEX_MEX = SynchedEntityData.defineId(Chet.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	private boolean isSlim;

	public Chet(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
		builder.define(RESOLVABLE_PROFILE, Optional.empty());
		builder.define(TEX_MEX, false);
	}

	public boolean isTexMex() {
		return this.entityData.get(TEX_MEX);
	}

	public void setTexMex(boolean texMex) {
		this.entityData.set(TEX_MEX, texMex);
	}

	public Optional<ResolvableProfile> getGameProfile() {
		return entityData.get(RESOLVABLE_PROFILE);
	}

	protected void setGameProfileInternal(ResolvableProfile profile) {
		if (profile != null) {
			entityData.set(RESOLVABLE_PROFILE, Optional.of(profile));
		} else {
			entityData.set(RESOLVABLE_PROFILE, Optional.empty());
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("profileExists", entityData.get(RESOLVABLE_PROFILE).isPresent());
		if (getGameProfile().isPresent()) {
			ResolvableProfile.CODEC.encodeStart(NbtOps.INSTANCE, entityData.get(RESOLVABLE_PROFILE).get())
					.resultOrPartial(GenerikMod.LOGGER::error)
					.ifPresent(profile -> compound.put("profile", profile));

		}
		compound.putBoolean("texMex", isTexMex());
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		boolean profileExists = compound.getBoolean("profileExists");
		if (profileExists) {
			entityData.set(RESOLVABLE_PROFILE, ResolvableProfile.CODEC
					.parse(NbtOps.INSTANCE, compound.get("profile"))
					.resultOrPartial(p_332637_ -> GenerikMod.LOGGER.error("Failed to load profile from Chet: {}", p_332637_)));
		} else {
			entityData.set(RESOLVABLE_PROFILE, Optional.empty());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setTexMex(compound.getBoolean("texMex"));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
	                                    @Nullable SpawnGroupData spawnData) {
		spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);
		ResolvableProfile profile = new ResolvableProfile(Optional.of("generikb"), Optional.empty(), new PropertyMap());
		ProfileUtil.resolve(profile).thenAcceptAsync(this::setGameProfileInternal);
//		setTexMex(true);

		return spawnData;
	}

	@Override
	public boolean removeWhenFarAway(double distance) {
		return false;
	}

	@Override
	public boolean isPreventingPlayerRest(Player player) {
		return false;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		super.onSyncedDataUpdated(key);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			this.setupAnimationStates();
		}
	}

	private void setupAnimationStates() {
		if (this.idleAnimationTimeout <= 0) {
			this.idleAnimationTimeout = this.random.nextInt(40) + 80;
			this.idleAnimationState.start(this.tickCount);
		} else {
			--this.idleAnimationTimeout;
		}
	}
}
