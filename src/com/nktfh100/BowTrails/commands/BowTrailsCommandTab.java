package com.nktfh100.BowTrails.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.nktfh100.BowTrails.info.BowTrail;
import com.nktfh100.BowTrails.main.BowTrails;

public class BowTrailsCommandTab implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> COMMANDS = new ArrayList<>();
		int arg = 0;
		if (args.length == 1) {
			arg = 0;
			COMMANDS.add("select");
			if (sender.hasPermission("bowtrails.admin")) {
				COMMANDS.add("reload");
			}
		} else if (args.length == 2) {
			arg = 1;
			for (BowTrail trail : BowTrails.getInstance().getConfigManager().getBowTrails().values()) {
				if (trail.getPermission().isEmpty() || sender.hasPermission(trail.getPermission())) {
					COMMANDS.add(trail.getConfigKey());
				}
			}
		}

		final List<String> completions = new ArrayList<>();
		StringUtil.copyPartialMatches(args[arg], COMMANDS, completions);

		Collections.sort(completions);
		return completions;
	}

}
