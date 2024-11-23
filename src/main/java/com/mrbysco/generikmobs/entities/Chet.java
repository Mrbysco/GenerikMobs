package com.mrbysco.generikmobs.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mrbysco.generikmobs.registry.GenerikSerializers;
import com.mrbysco.generikmobs.util.ProfileUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class Chet extends Monster {
	private static final EntityDataAccessor<Optional<GameProfile>> GAMEPROFILE = SynchedEntityData.defineId(Chet.class, GenerikSerializers.OPTIONAL_GAME_PROFILE.get());
	private static final EntityDataAccessor<Boolean> TEX_MEX = SynchedEntityData.defineId(Chet.class, EntityDataSerializers.BOOLEAN);
	public final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTimeout = 0;

	private boolean isSlim;

	public Chet(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(GAMEPROFILE, Optional.empty());
		this.entityData.define(TEX_MEX, false);
	}

	public boolean isTexMex() {
		return this.entityData.get(TEX_MEX);
	}

	public void setTexMex(boolean texMex) {
		this.entityData.set(TEX_MEX, texMex);
	}

	public Optional<GameProfile> getGameProfile() {
		return entityData.get(GAMEPROFILE);
	}

	public void setGameProfile(String name) {
		setGameProfile(new GameProfile(null, name));
	}

	public void setGameProfile(UUID id) {
		setGameProfile(new GameProfile(id, null));
	}

	public void setGameProfile(GameProfile playerProfile) {
		// Check if the content of the profile is empty
		if (playerProfile.getId() == null && playerProfile.getName() == null) {
			return;
		}

		ProfileUtil.updateGameProfile(playerProfile, this::setGameProfileInternal);
	}

	protected void setGameProfileInternal(GameProfile playerProfile) {
		if (playerProfile != null) {
			entityData.set(GAMEPROFILE, Optional.of(playerProfile));
		} else {
			entityData.set(GAMEPROFILE, Optional.empty());
		}
	}

	public void setSlim(boolean slim) {
		this.isSlim = slim;
	}

	public boolean isSlim() {
		return this.isSlim;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("gameProfileExists", entityData.get(GAMEPROFILE).isPresent());
		if (getGameProfile().isPresent()) {
			compound.put("gameProfile", NbtUtils.writeGameProfile(new CompoundTag(), entityData.get(GAMEPROFILE).get()));
		}
		compound.putBoolean("texMex", isTexMex());
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		setGameProfileInternal(!compound.getBoolean("gameProfileExists") ? null :
				NbtUtils.readGameProfile(compound.getCompound("gameProfile")));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		setTexMex(compound.getBoolean("texMex"));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason,
	                                    @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
		setGameProfile("generikb");
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
		if (GAMEPROFILE.equals(key) && this.level().isClientSide()) {
			this.getGameProfile().ifPresent(gameprofile -> {
				if (gameprofile.isComplete()) {
					Minecraft.getInstance().getSkinManager().registerSkins(gameprofile, (textureType, textureLocation, profileTexture) -> {
						if (textureType.equals(MinecraftProfileTexture.Type.SKIN)) {
							String metadata = profileTexture.getMetadata("model");
							this.setSlim(metadata != null && metadata.equals("slim"));
						}
					}, true);
				}
			});
		}
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
