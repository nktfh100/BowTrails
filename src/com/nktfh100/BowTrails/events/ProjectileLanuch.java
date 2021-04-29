package com.nktfh100.BowTrails.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import com.nktfh100.BowTrails.info.PlayerInfo;
import com.nktfh100.BowTrails.main.BowTrails;

public class ProjectileLanuch implements Listener {

	private BowTrails plugin;

	public ProjectileLanuch(BowTrails instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onLanuch(ProjectileLaunchEvent ev) {
		if (ev.getEntityType() == EntityType.ARROW && ev.getEntity().getShooter() != null && ev.getEntity().getShooter() instanceof Player) {
			PlayerInfo pInfo = plugin.getPlayersManager().getPlayerInfo((Player) ev.getEntity().getShooter());
			plugin.getArrowsManager().addArrow(pInfo.getSelectedTrail().createArrowInfo((Arrow) ev.getEntity()));
		}
	}
}
