package com.mrbysco.generikmobs.entities.goal;

import com.mrbysco.generikmobs.entities.BoogerEater;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ThrowBoogerGoal extends Goal {
	private final BoogerEater boogerEater;
	@Nullable
	private LivingEntity target;
	private int attackTime = -1;
	private final double speedModifier;
	private int seeTime;
	private final int attackIntervalMin;
	private final int attackIntervalMax;
	private final float attackRadius;
	private final float attackRadiusSqr;

	public ThrowBoogerGoal(BoogerEater boogerEater, double speedModifier, int attackInterval, float attackRadius) {
		this(boogerEater, speedModifier, attackInterval, attackInterval, attackRadius);
	}

	public ThrowBoogerGoal(BoogerEater boogerEater, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
		this.boogerEater = boogerEater;
		this.speedModifier = speedModifier;
		this.attackIntervalMin = attackIntervalMin;
		this.attackIntervalMax = attackIntervalMax;
		this.attackRadius = attackRadius;
		this.attackRadiusSqr = attackRadius * attackRadius;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	@Override
	public boolean canUse() {
		LivingEntity livingentity = this.boogerEater.getTarget();
		if (livingentity != null && livingentity.isAlive()) {
			this.target = livingentity;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean canContinueToUse() {
		return this.canUse() || this.target.isAlive() && !this.boogerEater.getNavigation().isDone();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void stop() {
		this.target = null;
		this.seeTime = 0;
		this.attackTime = -1;
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
		double sqrDistance = this.boogerEater.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
		boolean hasLineOfSight = this.boogerEater.getSensing().hasLineOfSight(this.target);
		if (hasLineOfSight) {
			++this.seeTime;
		} else {
			this.seeTime = 0;
		}

		if (!(sqrDistance > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
			this.boogerEater.getNavigation().stop();
		} else {
			this.boogerEater.getNavigation().moveTo(this.target, this.speedModifier);
		}

		this.boogerEater.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
		if (this.attackTime == 20) {
			this.boogerEater.setThrowing(true);
		}
		if (--this.attackTime == 0) {
			if (!hasLineOfSight) {
				return;
			}

			float f = (float) Math.sqrt(sqrDistance) / this.attackRadius;
			float f1 = Mth.clamp(f, 0.1F, 1.0F);
			this.boogerEater.setThrowing(false);
			this.boogerEater.performRangedAttack(this.target, f1);
			this.attackTime = Mth.floor(f * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
		} else if (this.attackTime < 0) {
			this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(sqrDistance) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
		}

	}
}