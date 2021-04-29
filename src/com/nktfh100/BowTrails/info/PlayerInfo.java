package com.nktfh100.BowTrails.info;

import org.bukkit.entity.Player;

import com.nktfh100.BowTrails.main.BowTrails;

public class PlayerInfo {

	private Player player;

	private BowTrail selectedTrail;

	public PlayerInfo(Player player) {
		this.player = player;
		this.selectedTrail = BowTrails.getInstance().getConfigManager().getDefaultBowTrail();
	}

	public Player getPlayer() {
		return player;
	}

	public BowTrail getSelectedTrail() {
		return selectedTrail;
	}

	public void setSelectedTrail(BowTrail selectedTrail) {
		this.selectedTrail = selectedTrail;
	}

}
