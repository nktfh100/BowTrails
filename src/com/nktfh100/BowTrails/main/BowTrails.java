package com.nktfh100.BowTrails.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.nktfh100.BowTrails.commands.BowTrailsCommand;
import com.nktfh100.BowTrails.commands.BowTrailsCommandTab;
import com.nktfh100.BowTrails.events.EntityDamage;
import com.nktfh100.BowTrails.events.ProjectileHit;
import com.nktfh100.BowTrails.events.ProjectileLanuch;
import com.nktfh100.BowTrails.managers.ArrowsManager;
import com.nktfh100.BowTrails.managers.ConfigManager;
import com.nktfh100.BowTrails.managers.MessagesManager;
import com.nktfh100.BowTrails.managers.PlayersManager;

public class BowTrails extends JavaPlugin {

	private static BowTrails instance;

	private ConfigManager configManager;
	private MessagesManager messagesManager;
	private ArrowsManager arrowsManager;
	private PlayersManager playersManager;

	public BowTrails() {
		instance = this;
	}

	@Override
	public void onEnable() {
		configManager = new ConfigManager(this);
		messagesManager = new MessagesManager(this);
		configManager.loadConfig();
		messagesManager.loadAll();
		arrowsManager = new ArrowsManager(this);
		playersManager = new PlayersManager(this);

		this.getCommand("bowtrails").setExecutor(new BowTrailsCommand());
		this.getCommand("bowtrails").setTabCompleter(new BowTrailsCommandTab());

		getServer().getPluginManager().registerEvents(playersManager, this);
		getServer().getPluginManager().registerEvents(new ProjectileLanuch(this), this);
		getServer().getPluginManager().registerEvents(new ProjectileHit(this), this);
		getServer().getPluginManager().registerEvents(new EntityDamage(this), this);

	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public MessagesManager getMessagesManager() {
		return messagesManager;
	}

	public ArrowsManager getArrowsManager() {
		return arrowsManager;
	}

	public PlayersManager getPlayersManager() {
		return playersManager;
	}

	public static BowTrails getInstance() {
		return instance;
	}
}
