package dev.xkmc.fastprojectileapi.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

public abstract class SimplifiedEntity extends Entity {

	public SimplifiedEntity(EntityType<?> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	public void baseTick() {
		this.level().getProfiler().push("entityBaseTick");
		if (this.boardingCooldown > 0) {
			--this.boardingCooldown;
		}
		this.walkDistO = this.walkDist;
		this.xRotO = this.getXRot();
		this.yRotO = this.getYRot();
		handlePortal();
		this.checkBelowWorld();
		this.firstTick = false;
		this.level().getProfiler().pop();
	}

	protected boolean updateInWaterStateAndDoFluidPushing() {
		return false;
	}

	protected void doWaterSplashEffect() {
	}

	@Override
	public boolean canSpawnSprintParticle() {
		return false;
	}

	@Override
	protected void tryCheckInsideBlocks() {
	}

	@Override
	protected void checkInsideBlocks() {
	}

	@Override
	public int getRemainingFireTicks() {
		return 0;
	}

	@Override
	public void setRemainingFireTicks(int pRemainingFireTicks) {
	}

	@Override
	public boolean ignoreExplosion(Explosion explosion) {
		return true;
	}

	@Override
	public void clearFire() {
	}

	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	public boolean mayInteract(Level pLevel, BlockPos pPos) {
		return false;
	}

}
