package me.mahasamut.OnePlayerSleep.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.clip.placeholderapi.PlaceholderAPI;
import me.mahasamut.OnePlayerSleep.Main;
import me.mahasamut.OnePlayerSleep.utils.Config;
import me.mahasamut.OnePlayerSleep.utils.StringUtils;

public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (player.isOp() && Config.UPDATE_CHECKER) {
			Main.getLatestVersion(version -> {
				if (!Main.INSTANCE.getDescription().getVersion().equals(version))
					player.sendMessage(StringUtils.formatColor(Config.MESSAGE_NEW_UPDATE));
			});
		}
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();

		if (player.hasPermission("oneplayersleep.use")) {
			Bukkit.getScheduler().runTaskLater(Main.INSTANCE, () -> {
				if (player.isSleeping()) {
					player.getWorld().setTime(0L);
					if (Config.RESET_STORM)
						player.getWorld().setStorm(false);
					if (Config.RESET_THUNDER)
						player.getWorld().setThundering(false);
					if (Config.RESET_PHANTOM_STATS)
						player.setStatistic(Statistic.TIME_SINCE_REST, 0);
					if (Config.MESSAGE_SLEPT_ENABLE) {
						if (Config.MESSAGE_SLEPT_PER_WORLD)
							player.getWorld().getPlayers().forEach(p -> p.sendMessage(getMessage(player)));
						else
							Bukkit.getServer().broadcastMessage(getMessage(player));
					}
				}
			}, 100L);
		}
	}

	private String getMessage(Player player) {
		return StringUtils.formatColor(PlaceholderAPI.setPlaceholders(player,
				Config.MESSAGE_SLEPT_MESSAGES.get(new Random().nextInt(Config.MESSAGE_SLEPT_MESSAGES.size()))));
	}

}
