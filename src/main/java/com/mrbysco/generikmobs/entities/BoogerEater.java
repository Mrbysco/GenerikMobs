package com.mrbysco.generikmobs.entities;

import com.mrbysco.generikmobs.GenerikMod;
import com.mrbysco.generikmobs.entities.goal.ThrowBoogerGoal;
import com.mrbysco.generikmobs.entities.projectile.BoogerProjectile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class BoogerEater extends Monster implements RangedAttackMob {
	private static final EntityDataAccessor<Byte> MODE = SynchedEntityData.defineId(BoogerEater.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> IS_THROWING = SynchedEntityData.defineId(BoogerEater.class, EntityDataSerializers.BOOLEAN);

	private final ThrowBoogerGoal throwGoal = new ThrowBoogerGoal(this, 1.0D, 40, 15.0F);
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void stop() {
			super.stop();
			BoogerEater.this.setAggressive(false);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void start() {
			super.start();
			BoogerEater.this.setAggressive(true);
		}
	};

	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState throwAnimationState = new AnimationState();

	public BoogerEater(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		this.reassessWeaponGoal();
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(MODE, (byte) 0);
		this.getEntityData().define(IS_THROWING, false);
	}

	public byte getMode() {
		return this.getEntityData().get(MODE);
	}

	public void setMode(byte mode) {
		this.entityData.set(MODE, mode);
	}

	public boolean isThrowing() {
		return this.entityData.get(IS_THROWING);
	}

	public void setThrowing(boolean throwing) {
		this.entityData.set(IS_THROWING, throwing);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putByte("BehaviorMode", this.getMode());
		tag.putBoolean("IsThrowing", this.isThrowing());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setMode(tag.getByte("BehaviorMode"));
		this.setThrowing(tag.getBoolean("IsThrowing"));
		this.reassessWeaponGoal();
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float velocity) {
		GenerikMod.LOGGER.info("Animation time {}", this.throwAnimationState.getAccumulatedTime());

		BoogerProjectile boogerProjectile = new BoogerProjectile(this.level(), this);
		double d0 = livingEntity.getX() - this.getX();
		double d1 = livingEntity.getY(0.3333333333333333D) - boogerProjectile.getY();
		double d2 = livingEntity.getZ() - this.getZ();
		double d3 = Math.sqrt(d0 * d0 + d2 * d2) * (double) 0.2F;
		boogerProjectile.shoot(d0, d1 + d3, d2, 1.5F, 10.0F);
		if (!this.isSilent()) {
			this.level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), SoundEvents.LLAMA_SPIT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
		}

		this.level().addFreshEntity(boogerProjectile);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return BoogerEater.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.25F);
	}

	/**
	 * Sets this entity's combat AI.
	 */
	public void reassessWeaponGoal() {
		if (this.level() != null && !this.level().isClientSide) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.throwGoal);
			switch (this.getMode()) {
				default: {
					this.goalSelector.addGoal(4, this.meleeGoal);
					break;
				}
				case 1, 2: { // Tosser/Summoner mode
					int i = 20;
					if (this.level().getDifficulty() != Difficulty.HARD) {
						i = 40;
					}

					this.goalSelector.addGoal(4, this.throwGoal);
					break;
				}
			}
		}
	}

	@Override
	public void handleEntityEvent(byte pId) {
		if (pId == 4) {
			this.idleAnimationState.stop();
			this.setThrowing(true);
			this.throwAnimationState.start(this.tickCount);
		} else {
			super.handleEntityEvent(pId);
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (key.equals(IS_THROWING)) {
			this.throwAnimationState.animateWhen(isThrowing(), this.tickCount);
		}

		super.onSyncedDataUpdated(key);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		if (this.level().isClientSide()) {
			this.idleAnimationState.animateWhen(!this.walkAnimation.isMoving(), this.tickCount);
		}

		super.tick();
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
		pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
		this.setMode((byte) 1);
		this.reassessWeaponGoal();
		return pSpawnData;
	}
}
