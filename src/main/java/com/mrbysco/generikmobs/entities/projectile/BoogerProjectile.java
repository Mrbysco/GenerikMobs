package com.mrbysco.generikmobs.entities.projectile;

import com.mrbysco.generikmobs.entities.BoogerEater;
import com.mrbysco.generikmobs.registry.GenerikMobs;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class BoogerProjectile extends Projectile {
	public BoogerProjectile(EntityType<? extends BoogerProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public BoogerProjectile(Level level, BoogerEater boogerEater) {
		this(GenerikMobs.BOOGER_PROJECTILE.get(), level);
		this.setOwner(boogerEater);
		this.setPos(boogerEater.getX() - (double) (boogerEater.getBbWidth() + 1.0F) * 0.5D * (double) Mth.sin(boogerEater.yBodyRot * ((float) Math.PI / 180F)), boogerEater.getEyeY() - (double) 0.1F, boogerEater.getZ() + (double) (boogerEater.getBbWidth() + 1.0F) * 0.5D * (double) Mth.cos(boogerEater.yBodyRot * ((float) Math.PI / 180F)));
	}


	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();
		Vec3 delta = this.getDeltaMovement();
		HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult))
			this.onHit(hitresult);
		double d0 = this.getX() + delta.x;
		double d1 = this.getY() + delta.y;
		double d2 = this.getZ() + delta.z;
		this.updateRotation();
		if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
			this.discard();
		} else if (this.isInWaterOrBubble()) {
			this.discard();
		} else {
			this.setDeltaMovement(delta.scale((double) 0.99F));
			if (!this.isNoGravity()) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0D, (double) -0.06F, 0.0D));
			}

			this.setPos(d0, d1, d2);
		}
	}

	/**
	 * Called when the arrow hits an entity
	 */
	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity entity = this.getOwner();
		if (entity instanceof LivingEntity livingentity) {
			result.getEntity().hurt(this.damageSources().mobProjectile(this, livingentity), 1.0F);
		}
		spawnSlimeParticles();
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		if (!this.level().isClientSide) {
			this.discard();
		}
		spawnSlimeParticles();
	}

	/**
	 * Spawn slime particles for the projectile
	 */
	private void spawnSlimeParticles() {
		for (int i = 0; i < 7; ++i) {
			double d3 = 0.4D + 0.1D * (double) i;
			this.level().addParticle(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(), xo * d3, yo, zo * d3);
		}
	}

	@Override
	protected void defineSynchedData(Builder builder) {
	}

	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		double xa = packet.getXa();
		double ya = packet.getYa();
		double za = packet.getZa();

		for (int i = 0; i < 7; ++i) {
			double d3 = 0.4D + 0.1D * (double) i;
			this.level().addParticle(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(), xa * d3, ya, za * d3);
		}

		this.setDeltaMovement(xa, ya, za);
	}
}
