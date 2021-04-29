package com.nktfh100.BowTrails.info.arrows;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import com.nktfh100.BowTrails.info.ArrowInfo;
import com.nktfh100.BowTrails.info.BowTrail;
import com.nktfh100.BowTrails.main.BowTrails;

public class EntityArrow extends ArrowInfo {

	private BowTrail bowTrail;
	private Entity spawnedEntity;
	private Vector startingDirection = null;

	public EntityArrow(Arrow arrow, BowTrail bowTrail) {
		super(arrow, bowTrail.getType(), bowTrail);
		this.bowTrail = bowTrail;
		this.spawnedEntity = arrow.getWorld().spawnEntity(arrow.getLocation(), bowTrail.getEntityType());
		this.spawnedEntity.setMetadata("trail", new FixedMetadataValue(BowTrails.getInstance(), true));
		this.spawnedEntity.setInvulnerable(true);
		this.spawnedEntity.setSilent(true);
		this.spawnedEntity.setCustomNameVisible(false);
		this.spawnedEntity.setGravity(false);
	}

	@Override
	public void update() {
		if (this.spawnedEntity != null && !this.spawnedEntity.isDead()) {
			this.spawnedEntity.setFireTicks(0);
			if (this.startingDirection == null) {
				this.startingDirection = this.getArrow().getLocation().getDirection();
			}
			Location arrowLoc = this.getArrow().getLocation();
			Location tpTo = new Location(arrowLoc.getWorld(), arrowLoc.getX(), arrowLoc.getY(), arrowLoc.getZ());
			tpTo.setDirection(this.startingDirection);
			tpTo.add(this.getArrow().getVelocity().clone().multiply(-1).normalize().multiply(0.8));
			this.spawnedEntity.teleport(tpTo);
		}
	}

	@Override
	public void destroyed() {
		if (this.spawnedEntity != null && !this.spawnedEntity.isDead()) {
			this.spawnedEntity.remove();
		}
	}

	public BowTrail getBowTrail() {
		return this.bowTrail;
	}

	public Entity getSpawnedEntity() {
		return this.spawnedEntity;
	}

}
