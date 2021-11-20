package me.mahasamut.OnePlayerSleep.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class StringUtils {

	private static Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

	public static String formatColor(String message) {
		if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17")) {
			Matcher matcher = hexPattern.matcher(message);

			while (matcher.find()) {
				String color = message.substring(matcher.start(), matcher.end());

				message = message.replace(color, String.valueOf(ChatColor.of(color)));
				matcher = hexPattern.matcher(message);
			}
		}

		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
