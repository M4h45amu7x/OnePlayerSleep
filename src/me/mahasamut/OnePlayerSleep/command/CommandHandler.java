package me.mahasamut.OnePlayerSleep.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.mahasamut.OnePlayerSleep.Main;
import me.mahasamut.OnePlayerSleep.utils.Config;
import me.mahasamut.OnePlayerSleep.utils.StringUtils;

public class CommandHandler implements CommandExecutor {

	private static List<Command> commands = new ArrayList<Command>();

	public void register(Command command) {
		commands.add(command);
	}

	public Command getCommand(String command) {
		for (Command cmd : commands) {
			if (cmd.getCommand().equalsIgnoreCase(command))
				return cmd;
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			getCommand(Main.pluginName).getCommandInterface().onCommand(sender, cmd, commandLabel, args);
			return true;
		}
		if (args.length > 0) {
			if (getCommand(args[0]) != null) {
				if (getCommand(args[0]).getPermission() == null || getCommand(args[0]).getPermission().isEmpty()
						|| sender.hasPermission(getCommand(args[0]).getPermission()))
					getCommand(args[0]).getCommandInterface().onCommand(sender, cmd, commandLabel, args);
				else
					sender.sendMessage(StringUtils.formatColor(Config.MESSAGE_NO_PERMISSION));
				return true;
			} else {
				sender.sendMessage(StringUtils.formatColor(Config.MESSAGE_HELP));
				return true;
			}
		}
		return false;
	}

	public static List<Command> getCommands() {
		return commands;
	}

}