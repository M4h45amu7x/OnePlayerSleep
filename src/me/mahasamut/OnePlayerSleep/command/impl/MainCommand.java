package me.mahasamut.OnePlayerSleep.command.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.mahasamut.OnePlayerSleep.Main;
import me.mahasamut.OnePlayerSleep.command.CommandInterface;
import me.mahasamut.OnePlayerSleep.utils.Config;
import me.mahasamut.OnePlayerSleep.utils.StringUtils;

public class MainCommand implements CommandInterface {

	@SuppressWarnings("unused")
	private Main main;

	public MainCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		sender.sendMessage(StringUtils.formatColor(Config.MESSAGE_HELP));
		return true;
	}

}