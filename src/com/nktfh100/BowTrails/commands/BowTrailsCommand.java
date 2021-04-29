package com.nktfh100.BowTrails.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.nktfh100.BowTrails.info.BowTrail;
import com.nktfh100.BowTrails.info.PlayerInfo;
import com.nktfh100.BowTrails.main.BowTrails;

public class BowTrailsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			BowTrails plugin = BowTrails.getInstance();
			Player player = (Player) sender;
			if (args.length == 0) {
				sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "------------------------------------------");
				sender.sendMessage("       " + ChatColor.GOLD + "" + ChatColor.BOLD + "BowTrails by nktfh100");

				sender.sendMessage(ChatColor.YELLOW + "/bowtrails select <trail>" + ChatColor.WHITE + " - " + ChatColor.GOLD + "Select a trail for your bow");
				if (player.hasPermission("bowtrails.admin")) {
					sender.sendMessage(ChatColor.YELLOW + "/bowtrails reload" + ChatColor.WHITE + " - " + ChatColor.GOLD + "Reload all the configs");
				}

				sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "------------------------------------------");
				return true;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("bowtrails.admin")) {
						plugin.getArrowsManager().getArrows().clear();
						plugin.getConfigManager().loadConfig();
						plugin.getMessagesManager().loadAll();
						plugin.getMessagesManager().sendMessage(player, "reload");
					} else {
						plugin.getMessagesManager().sendMessage(player, "no-permission");
					}
					return true;
				}
			} else if (args.length >= 2) {
				if (args[0].equalsIgnoreCase("select")) {
					BowTrail selected = plugin.getConfigManager().getBowTrail(args[1]);
					if (selected != null) {
						if (!selected.getPermission().isEmpty() && !player.hasPermission(selected.getPermission())) {
							plugin.getMessagesManager().sendMessage(player, "cant-select-trail");
							return true;
						}
						PlayerInfo pInfo = plugin.getPlayersManager().getPlayerInfo(player);
						pInfo.setSelectedTrail(selected);
						plugin.getMessagesManager().sendMessage(player, "trail-selected", args[1]);
						YamlConfiguration dataConfig = plugin.getPlayersManager().getDataConfig();
						if (plugin.isEnabled() && dataConfig != null) {
							dataConfig.set("players." + player.getUniqueId().toString(), selected.getConfigKey());
							try {
								dataConfig.save(new File(plugin.getDataFolder(), "data.yml"));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						plugin.getMessagesManager().sendMessage(player, "trail-doesnt-exist");
					}
					return true;
				}
			}
		}
		return false;
	}

}
