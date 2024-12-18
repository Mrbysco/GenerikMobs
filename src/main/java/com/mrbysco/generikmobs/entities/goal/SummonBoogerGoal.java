package com.mrbysco.generikmobs.entities.goal;

import com.mrbysco.generikmobs.entities.Booger;
import com.mrbysco.generikmobs.entities.BoogerEater;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class SummonBoogerGoal extends Goal {
	private final TargetingConditions boogerCountTargeting = TargetingConditions.forNonCombat()
			.range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

	private final BoogerEater caster;

	protected int attackWarmupDelay;
	protected int nextAttackTickCount;

	public SummonBoogerGoal(BoogerEater mob) {
//		this.setFlags(EnumSet.of(Flag.JUMP, Goal.Flag.LOOK));
		this.caster = mob;
	}

	public boolean canUse() {
		if (!canNormallyUse()) {
			return false;
		} else {
			int i = caster.level().getNearbyEntities(Booger.class, this.boogerCountTargeting, caster, caster.getBoundingBox().inflate(16.0D)).size();
			return caster.getRandom().nextInt(8) + 1 > i;
		}
	}

	public boolean canNormallyUse() {
		if (caster.getMode() != 2) {
			return false;
		}
		LivingEntity target = caster.getTarget();
		if (target != null && target.isAlive()) {
			if (caster.isSummoning()) {
				return false;
			} else {
				return caster.tickCount >= this.nextAttackTickCount;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canContinueToUse() {
		LivingEntity target = caster.getTarget();
		return target != null && target.isAlive() && this.attackWarmupDelay > 0;
	}

	@Override
	public void start() {
		this.attackWarmupDelay = this.adjustedTickDelay(this.getCastWarmupTime());
		this.nextAttackTickCount = caster.tickCount + this.getCastingInterval();
		caster.playSound(SoundEvents.EVOKER_PREPARE_WOLOLO, 1.0F, 1.0F);
		caster.setSummoning(true);
	}

	@Override
	public void stop() {
		super.stop();
		caster.setSummoning(false);
	}

	@Override
	public void tick() {
		--this.attackWarmupDelay;
		if (this.attackWarmupDelay == 0) {
			this.performBoogerSummoning();
			caster.playSound(caster.getSummoningSound(), 100.0F, 1.0F);
		}
	}

	protected int getCastWarmupTime() {
		return 20;
	}

	protected int getCastingInterval() {
		return 340;
	}

	protected void performBoogerSummoning() {
		ServerLevel serverlevel = (ServerLevel) caster.level();

		BlockPos blockpos = caster.blockPosition().offset(-2 + caster.getRandom().nextInt(5), 1, -2 + caster.getRandom().nextInt(5));
		Booger booger = GenerikMobs.BOOGER.get().create(caster.level());
		if (booger != null) {
			booger.moveTo(blockpos, 0.0F, 0.0F);
			booger.finalizeSpawn(serverlevel, caster.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData) null);
			booger.setTarget(caster.getTarget());
			serverlevel.addFreshEntityWithPassengers(booger);
		}
	}
}
