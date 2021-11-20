package me.mahasamut.OnePlayerSleep.command.impl.subs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import me.mahasamut.OnePlayerSleep.Main;
import me.mahasamut.OnePlayerSleep.command.CommandInterface;
import me.mahasamut.OnePlayerSleep.utils.Config;
import me.mahasamut.OnePlayerSleep.utils.StringUtils;

public class ReloadCommand implements CommandInterface {

	private Main main;

	public ReloadCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		main.config = YamlConfiguration.loadConfiguration(main.configFile);
		main.initConfig();
		sender.sendMessage(StringUtils.formatColor(Config.MESSAGE_RELOADED));
		return false;
	}

}