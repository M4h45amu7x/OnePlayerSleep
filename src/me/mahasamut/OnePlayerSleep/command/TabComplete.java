package me.mahasamut.OnePlayerSleep.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import me.mahasamut.OnePlayerSleep.Main;

public class TabComplete implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> cmds = new ArrayList<String>();
		List<String> completions = new ArrayList<String>();

		if (args.length == 1) {
			for (me.mahasamut.OnePlayerSleep.command.Command cmd : CommandHandler.getCommands()) {
				if (sender.hasPermission(Main.pluginName + "." + cmd.getPermission())
						&& !cmd.getCommand().equalsIgnoreCase(Main.pluginName))
					cmds.add(cmd.getCommand());
			}
			StringUtil.copyPartialMatches(args[0], cmds, completions);
			Collections.sort(cmds);
			return cmds;
		}

		return cmds;
	}

}
