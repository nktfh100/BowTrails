package com.nktfh100.BowTrails.managers;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.nktfh100.BowTrails.info.BowTrail;
import com.nktfh100.BowTrails.main.BowTrails;

public class ConfigManager {

	private BowTrails plugin;

	private HashMap<String, BowTrail> bowTrails = new HashMap<String, BowTrail>();

	private BowTrail defaultBowTrail = null;

	public ConfigManager(BowTrails instance) {
		this.plugin = instance;
	}

	public void loadConfig() {
		this.plugin.saveDefaultConfig();
		this.loadConfigVars();
	}

	public void loadConfigVars() {
		this.bowTrails.clear();

		this.plugin.reloadConfig();
		FileConfiguration config = this.plugin.getConfig();
		ConfigurationSection trailsSC = config.getConfigurationSection("trails");
		for (String key : trailsSC.getKeys(false)) {
			ConfigurationSection trailSC = trailsSC.getConfigurationSection(key);
			BowTrail createdTrail = new BowTrail(key, trailSC);
			if (createdTrail != null) {
				this.bowTrails.put(key, createdTrail);
				if (this.defaultBowTrail == null) {
					this.defaultBowTrail = createdTrail;
				}
			}
		}
	}

	public BowTrail getBowTrail(String key) {
		return this.bowTrails.get(key);
	}

	public HashMap<String, BowTrail> getBowTrails() {
		return bowTrails;
	}

	public BowTrail getDefaultBowTrail() {
		return defaultBowTrail;
	}
}
