package com.mrbysco.generikmobs.entities;

import com.mojang.authlib.properties.PropertyMap;
import com.mrbysco.generikmobs.registry.GenerikRegistry;
import com.mrbysco.generikmobs.util.ProfileUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;

public class Booger extends Mob implements Enemy {
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Booger.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<ItemStack> HEAD = SynchedEntityData.defineId(Booger.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<String> HEAD_NAME = SynchedEntityData.defineId(Booger.class, EntityDataSerializers.STRING);
	public static final int MIN_SIZE = 1;
	public static final int MAX_SIZE = 127;
	public float targetSquish;
	public float squish;
	public float oSquish;
	private boolean wasOnGround;

	public Booger(EntityType<? extends Booger> entityType, Level level) {
		super(entityType, level);
		this.fixupDimensions();
		this.moveControl = new Booger.BoogerMoveControl(this);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new Booger.BoogerFloatGoal(this));
		this.goalSelector.addGoal(2, new Booger.BoogerAttackGoal(this));
		this.goalSelector.addGoal(3, new Booger.BoogerRandomDirectionGoal(this));
		this.goalSelector.addGoal(5, new Booger.BoogerKeepOnJumpingGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (livingEntity) -> {
			return Math.abs(livingEntity.getY() - this.getY()) <= 4.0D;
		}));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ID_SIZE, 1);
		builder.define(HEAD_NAME, "");
		builder.define(HEAD, ItemStack.EMPTY);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes();
	}

	public void setSize(int size, boolean resetHealth) {
		int i = Mth.clamp(size, MIN_SIZE, MAX_SIZE);
		this.entityData.set(ID_SIZE, i);
		this.reapplyPosition();
		this.refreshDimensions();
		this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (i * i));
		this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.8F + 0.1F * (float) i));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double) i);
		if (resetHealth) {
			this.setHealth(this.getMaxHealth());
		}

		this.xpReward = i;
	}

	/**
	 * Returns the size of the booger.
	 */
	public int getSize() {
		return this.entityData.get(ID_SIZE);
	}

	public String getHeadName() {
		return this.entityData.get(HEAD_NAME);
	}

	public void setHeadName(String name) {
		this.entityData.set(HEAD_NAME, name);

		if (getCustomHead().isEmpty() && !name.isEmpty()) {
			ItemStack headStack = new ItemStack(Items.PLAYER_HEAD);
			ResolvableProfile profile = new ResolvableProfile(Optional.of(name), Optional.empty(), new PropertyMap());
			ProfileUtil.resolve(profile).thenAcceptAsync(resolvedProfile -> {
				headStack.set(DataComponents.PROFILE, resolvedProfile);
				this.entityData.set(HEAD, headStack);
			});
		}
	}

	public ItemStack getCustomHead() {
		return this.entityData.get(HEAD);
	}

	public void setCustomHead(ItemStack stack) {
		this.entityData.set(HEAD, stack);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Size", this.getSize() - 1);
		tag.putBoolean("wasOnGround", this.wasOnGround);
		tag.putString("HeadName", this.getHeadName());
		if (!getCustomHead().isEmpty())
			tag.put("Head", this.getCustomHead().saveOptional(this.registryAccess()));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		this.setSize(tag.getInt("Size") + 1, false);
		super.readAdditionalSaveData(tag);
		this.wasOnGround = tag.getBoolean("wasOnGround");
		this.setHeadName(tag.getString("HeadName"));
		if (tag.contains("Head", 10))
			this.setCustomHead(ItemStack.parseOptional(this.registryAccess(), tag.getCompound("Head")));
	}

	public boolean isTiny() {
		return this.getSize() <= 1;
	}

	protected ParticleOptions getParticleType() {
		return ParticleTypes.ITEM_SLIME;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return this.getSize() > 0;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		this.squish += (this.targetSquish - this.squish) * 0.5F;
		this.oSquish = this.squish;
		super.tick();
		if (this.onGround() && !this.wasOnGround) {
			int i = this.getSize();

			// Forge: Don't spawn particles if it's handled by the implementation itself
			if (!spawnCustomParticles())
				for (int j = 0; j < i * 8; ++j) {
					float f = this.random.nextFloat() * ((float) Math.PI * 2F);
					float f1 = this.random.nextFloat() * 0.5F + 0.5F;
					float f2 = Mth.sin(f) * (float) i * 0.5F * f1;
					float f3 = Mth.cos(f) * (float) i * 0.5F * f1;
					this.level().addParticle(this.getParticleType(), this.getX() + (double) f2, this.getY(), this.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);
				}

			if (!this.level().isClientSide) {
				BlockState puddleState = GenerikRegistry.SLIME_PUDDLE.get().defaultBlockState();
				if (getBlockStateOn().canBeReplaced() && puddleState.canSurvive(this.level(), this.blockPosition())) {
					this.level().setBlockAndUpdate(this.blockPosition(), puddleState);
					puddleState.onPlace(this.level(), this.blockPosition(), puddleState, false);
				}
			}

			this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			this.targetSquish = -0.5F;
		} else if (!this.onGround() && this.wasOnGround) {
			this.targetSquish = 1.0F;
		}

		this.wasOnGround = this.onGround();
		this.decreaseSquish();
	}

	protected void decreaseSquish() {
		this.targetSquish *= 0.6F;
	}

	/**
	 * Gets the amount of time the booger needs to wait between jumps.
	 */
	protected int getJumpDelay() {
		return this.random.nextInt(20) + 10;
	}

	@Override
	public void refreshDimensions() {
		double d0 = this.getX();
		double d1 = this.getY();
		double d2 = this.getZ();
		super.refreshDimensions();
		this.setPos(d0, d1, d2);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		if (ID_SIZE.equals(pKey)) {
			this.refreshDimensions();
			this.setYRot(this.yHeadRot);
			this.yBodyRot = this.yHeadRot;
			if (this.isInWater() && this.random.nextInt(20) == 0) {
				this.doWaterSplashEffect();
			}
		}

		super.onSyncedDataUpdated(pKey);
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
		int i = this.getSize();
		if (!this.level().isClientSide && i > 1 && this.isDeadOrDying()) {
			Component component = this.getCustomName();
			boolean flag = this.isNoAi();
			float f = (float) i / 4.0F;
			int j = i / 2;
			int k = 2 + this.random.nextInt(3);

			for (int l = 0; l < k; ++l) {
				float f1 = ((float) (l % 2) - 0.5F) * f;
				float f2 = ((float) (l / 2) - 0.5F) * f;
				if (this.getType().create(this.level()) instanceof Booger booger) {
					if (this.isPersistenceRequired()) {
						booger.setPersistenceRequired();
					}

					booger.setCustomName(component);
					booger.setNoAi(flag);
					booger.setInvulnerable(this.isInvulnerable());
					booger.setSize(j, true);
					booger.moveTo(this.getX() + (double) f1, this.getY() + 0.5D, this.getZ() + (double) f2, this.random.nextFloat() * 360.0F, 0.0F);
					this.level().addFreshEntity(booger);
				}
			}
		}

		super.remove(reason);
	}

	/**
	 * Applies a velocity to the entities, to push them away from each other.
	 */
	@Override
	public void push(Entity pEntity) {
		super.push(pEntity);
		if (pEntity instanceof IronGolem && this.isDealsDamage()) {
			this.dealDamage((LivingEntity) pEntity);
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void playerTouch(Player pEntity) {
		if (this.isDealsDamage()) {
			this.dealDamage(pEntity);
		}
	}

	protected void dealDamage(LivingEntity livingEntity) {
		if (this.isAlive()) {
			int i = this.getSize();
			DamageSource damagesource = this.damageSources().mobAttack(this);
			if (this.distanceToSqr(livingEntity) < 0.6D * (double) i * 0.6D * (double) i && this.hasLineOfSight(livingEntity) &&
					livingEntity.hurt(damagesource, this.getAttackDamage())) {
				this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				if (this.level() instanceof ServerLevel serverlevel) {
					EnchantmentHelper.doPostAttackEffects(serverlevel, livingEntity, damagesource);
				}
			}
		}
	}

	/**
	 * Indicates weather the booger is able to damage the player (based upon the booger's size)
	 */
	protected boolean isDealsDamage() {
		return !this.isTiny() && this.isEffectiveAi();
	}

	protected float getAttackDamage() {
		return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return this.isTiny() ? SoundEvents.SLIME_HURT_SMALL : SoundEvents.SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isTiny() ? SoundEvents.SLIME_DEATH_SMALL : SoundEvents.SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		return this.isTiny() ? SoundEvents.SLIME_SQUISH_SMALL : SoundEvents.SLIME_SQUISH;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F * (float) this.getSize();
	}

	/**
	 * The speed it takes to move the entity's head rotation through the faceEntity method.
	 */
	@Override
	public int getMaxHeadXRot() {
		return 0;
	}

	/**
	 * Returns {@code true} if the booger makes a sound when it jumps (based upon the booger's size)
	 */
	protected boolean doPlayJumpSound() {
		return this.getSize() > 0;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	public void jumpFromGround() {
		Vec3 vec3 = this.getDeltaMovement();
		this.setDeltaMovement(vec3.x, (double) this.getJumpPower(), vec3.z);
		this.hasImpulse = true;
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
		spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData);
		RandomSource randomSource = level.getRandom();
		int i = randomSource.nextInt(3);
		if (i < 2 && randomSource.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
			++i;
		}

		int j = 1 << i;
		this.setSize(j, true);

		this.setHeadName("mrbysco");

		return spawnData;
	}

	float getSoundPitch() {
		float f = this.isTiny() ? 1.4F : 0.8F;
		return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
	}

	protected SoundEvent getJumpSound() {
		return this.isTiny() ? SoundEvents.SLIME_JUMP_SMALL : SoundEvents.SLIME_JUMP;
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale((float) this.getSize());
	}

	/**
	 * Called when the booger spawns particles on landing, see onUpdate.
	 * Return true to prevent the spawning of the default particles.
	 */
	protected boolean spawnCustomParticles() {
		return false;
	}

	static class BoogerAttackGoal extends Goal {
		private final Booger booger;
		private int growTiredTimer;

		public BoogerAttackGoal(Booger pBooger) {
			this.booger = pBooger;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			LivingEntity livingentity = this.booger.getTarget();
			if (livingentity == null) {
				return false;
			} else {
				return !this.booger.canAttack(livingentity) ? false : this.booger.getMoveControl() instanceof Booger.BoogerMoveControl;
			}
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			this.growTiredTimer = reducedTickDelay(300);
			super.start();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean canContinueToUse() {
			LivingEntity livingentity = this.booger.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!this.booger.canAttack(livingentity)) {
				return false;
			} else {
				return --this.growTiredTimer > 0;
			}
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			LivingEntity livingentity = this.booger.getTarget();
			if (livingentity != null) {
				this.booger.lookAt(livingentity, 10.0F, 10.0F);
			}

			MoveControl movecontrol = this.booger.getMoveControl();
			if (movecontrol instanceof Booger.BoogerMoveControl moveControl) {
				moveControl.setDirection(this.booger.getYRot(), this.booger.isDealsDamage());
			}

		}
	}

	static class BoogerFloatGoal extends Goal {
		private final Booger booger;

		public BoogerFloatGoal(Booger booger) {
			this.booger = booger;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
			booger.getNavigation().setCanFloat(true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return (this.booger.isInWater() || this.booger.isInLava()) && this.booger.getMoveControl() instanceof Booger.BoogerMoveControl;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			if (this.booger.getRandom().nextFloat() < 0.8F) {
				this.booger.getJumpControl().jump();
			}

			MoveControl movecontrol = this.booger.getMoveControl();
			if (movecontrol instanceof Booger.BoogerMoveControl booger$boogermovecontrol) {
				booger$boogermovecontrol.setWantedMovement(1.2D);
			}
		}
	}

	static class BoogerKeepOnJumpingGoal extends Goal {
		private final Booger booger;

		public BoogerKeepOnJumpingGoal(Booger booger) {
			this.booger = booger;
			this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return !this.booger.isPassenger();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			MoveControl movecontrol = this.booger.getMoveControl();
			if (movecontrol instanceof Booger.BoogerMoveControl booger$boogermovecontrol) {
				booger$boogermovecontrol.setWantedMovement(1.0D);
			}
		}
	}

	static class BoogerMoveControl extends MoveControl {
		private float yRot;
		private int jumpDelay;
		private final Booger booger;
		private boolean isAggressive;

		public BoogerMoveControl(Booger pBooger) {
			super(pBooger);
			this.booger = pBooger;
			this.yRot = 180.0F * pBooger.getYRot() / (float) Math.PI;
		}

		public void setDirection(float pYRot, boolean pAggressive) {
			this.yRot = pYRot;
			this.isAggressive = pAggressive;
		}

		public void setWantedMovement(double pSpeed) {
			this.speedModifier = pSpeed;
			this.operation = MoveControl.Operation.MOVE_TO;
		}

		@Override
		public void tick() {
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
			this.mob.yHeadRot = this.mob.getYRot();
			this.mob.yBodyRot = this.mob.getYRot();
			if (this.operation != MoveControl.Operation.MOVE_TO) {
				this.mob.setZza(0.0F);
			} else {
				this.operation = MoveControl.Operation.WAIT;
				if (this.mob.onGround()) {
					this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					if (this.jumpDelay-- <= 0) {
						this.jumpDelay = this.booger.getJumpDelay();
						if (this.isAggressive) {
							this.jumpDelay /= 3;
						}

						this.booger.getJumpControl().jump();
						if (this.booger.doPlayJumpSound()) {
							this.booger.playSound(this.booger.getJumpSound(), this.booger.getSoundVolume(), this.booger.getSoundPitch());
						}
					} else {
						this.booger.xxa = 0.0F;
						this.booger.zza = 0.0F;
						this.mob.setSpeed(0.0F);
					}
				} else {
					this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				}
			}
		}
	}

	static class BoogerRandomDirectionGoal extends Goal {
		private final Booger booger;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public BoogerRandomDirectionGoal(Booger pBooger) {
			this.booger = pBooger;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return this.booger.getTarget() == null && (this.booger.onGround() || this.booger.isInWater() || this.booger.isInLava() || this.booger.hasEffect(MobEffects.LEVITATION)) && this.booger.getMoveControl() instanceof Booger.BoogerMoveControl;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = this.adjustedTickDelay(40 + this.booger.getRandom().nextInt(60));
				this.chosenDegrees = (float) this.booger.getRandom().nextInt(360);
			}

			MoveControl movecontrol = this.booger.getMoveControl();
			if (movecontrol instanceof Booger.BoogerMoveControl booger$boogermovecontrol) {
				booger$boogermovecontrol.setDirection(this.chosenDegrees, false);
			}
		}
	}
}
