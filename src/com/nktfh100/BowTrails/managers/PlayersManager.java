package com.nktfh100.BowTrails.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.nktfh100.BowTrails.info.BowTrail;
import com.nktfh100.BowTrails.info.PlayerInfo;
import com.nktfh100.BowTrails.main.BowTrails;

public class PlayersManager implements Listener {

	private BowTrails plugin;

	private HashMap<String, PlayerInfo> players = new HashMap<>();

	private YamlConfiguration dataConfig;

	public PlayersManager(BowTrails instance) {
		this.plugin = instance;

		File dataConfigFIle = new File(this.plugin.getDataFolder(), "data.yml");
		if (!dataConfigFIle.exists()) {
			try {
				this.plugin.saveResource("data.yml", false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.dataConfig = YamlConfiguration.loadConfiguration(dataConfigFIle);

		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			players.put(player.getUniqueId().toString(), new PlayerInfo(player));
		}

		for (PlayerInfo pInfo : this.getPlayers()) {
			String selectedStr = this.getPlayerSelectedTrail(pInfo.getPlayer().getUniqueId().toString());
			BowTrail selected = plugin.getConfigManager().getBowTrail(selectedStr);
			if (selected != null) {
				pInfo.setSelectedTrail(selected);
			}
		}
	}

	public PlayerInfo getPlayerInfo(Player player) {
		if (player == null) {
			return null;
		}
		return players.get(player.getUniqueId().toString());
	}

	public PlayerInfo getPlayerByUUID(String uuid) {
		return this.players.get(uuid);
	}

	public List<PlayerInfo> getPlayers() {
		List<PlayerInfo> players_ = new ArrayList<PlayerInfo>(this.players.values());
		return players_;
	}

	public String getPlayerSelectedTrail(String uuid) {
		if (this.dataConfig.contains("players." + uuid)) {
			return this.dataConfig.getString("players." + uuid);
		}
		return plugin.getConfigManager().getDefaultBowTrail().getConfigKey();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev) {
		PlayerInfo pInfo = new PlayerInfo(ev.getPlayer());
		players.put(ev.getPlayer().getUniqueId().toString(), pInfo);

		String selectedStr = this.getPlayerSelectedTrail(pInfo.getPlayer().getUniqueId().toString());
		BowTrail selected = plugin.getConfigManager().getBowTrail(selectedStr);
		if (selected != null) {
			pInfo.setSelectedTrail(selected);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent ev) {
		Player player = ev.getPlayer();
		PlayerInfo pInfo = this.players.get(player.getUniqueId().toString());
		if (pInfo == null) {
			return;
		}
		players.remove(player.getUniqueId().toString());
	}

	public YamlConfiguration getDataConfig() {
		return this.dataConfig;
	}
}
