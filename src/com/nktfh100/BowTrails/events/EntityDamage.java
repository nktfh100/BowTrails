package com.nktfh100.BowTrails.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.nktfh100.BowTrails.main.BowTrails;

public class EntityDamage implements Listener {

	private BowTrails plugin;

	public EntityDamage(BowTrails instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent ev) {
		if (ev.getEntity().hasMetadata("trail")) {
			ev.setCancelled(true);
		}
	}
}
