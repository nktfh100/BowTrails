package com.nktfh100.BowTrails.events;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.nktfh100.BowTrails.enums.ArrowEffectType;
import com.nktfh100.BowTrails.info.ArrowInfo;
import com.nktfh100.BowTrails.info.arrows.EntityArrow;
import com.nktfh100.BowTrails.main.BowTrails;

public class ProjectileHit implements Listener {

	private BowTrails plugin;

	public ProjectileHit(BowTrails instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onHit(ProjectileHitEvent ev) {
		if (ev.getEntityType() == EntityType.ARROW) {
			if (ev.getHitEntity() != null) {
				if (ev.getHitEntity().hasMetadata("trail")) {
					EntityArrow arrowInfo = (EntityArrow) plugin.getArrowsManager().getArrowInfo((Arrow) ev.getEntity());
					if (arrowInfo != null) {
						ev.getHitEntity().remove();
						ev.getEntity().remove();
						Location loc_ = ev.getEntity().getLocation();
						Arrow newArrow = (Arrow) loc_.getWorld().spawnEntity(loc_, ev.getEntityType());
						newArrow.setVelocity(ev.getEntity().getVelocity());
						plugin.getArrowsManager().addArrow(arrowInfo.getBowTrail().createArrowInfo(newArrow));
						plugin.getArrowsManager().removeArrow((Arrow) ev.getEntity());
						return;
					}
				}
			}
			ArrowInfo arrowInfo = plugin.getArrowsManager().getArrowInfo((Arrow) ev.getEntity());
			if (arrowInfo != null && arrowInfo.getEffectType() == ArrowEffectType.ENTITY) {
				plugin.getArrowsManager().removeArrow((Arrow) ev.getEntity());
			}
		}
	}
}
