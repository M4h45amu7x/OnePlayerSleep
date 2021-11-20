package me.mahasamut.OnePlayerSleep;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import me.mahasamut.OnePlayerSleep.command.Command;
import me.mahasamut.OnePlayerSleep.command.CommandHandler;
import me.mahasamut.OnePlayerSleep.command.TabComplete;
import me.mahasamut.OnePlayerSleep.command.impl.MainCommand;
import me.mahasamut.OnePlayerSleep.command.impl.subs.ReloadCommand;
import me.mahasamut.OnePlayerSleep.listener.PlayerListener;
import me.mahasamut.OnePlayerSleep.metrics.Metrics;
import me.mahasamut.OnePlayerSleep.utils.Config;
import me.mahasamut.OnePlayerSleep.utils.StringUtils;

public class Main extends JavaPlugin {

	public static Plugin INSTANCE;
	public File configFile;
	public FileConfiguration config;

	public static int resourceID = 97044;
	public static int metricsID = 13121;

	public static String pluginName;

	@Override
	public void onEnable() {
		INSTANCE = this;

		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		configFile = new File(getDataFolder(), "config.yml");
		initConfig();

		pluginName = Main.INSTANCE.getDescription().getName().toLowerCase();

		CommandHandler handler = new CommandHandler();

		handler.register(new Command(pluginName, null, new MainCommand(this)));
		handler.register(new Command("reload", "reload", new ReloadCommand(this)));
		getCommand(pluginName).setExecutor(handler);
		getCommand(pluginName).setTabCompleter(new TabComplete());

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		} else {
			getLogger().warning("Couldn't find PlaceholderAPI! This plugin is required.");
			getServer().getPluginManager().disablePlugin(this);
		}

		if (Config.UPDATE_CHECKER) {
			Main.getLatestVersion(version -> {
				if (!Main.INSTANCE.getDescription().getVersion().equals(version))
					Bukkit.getLogger().info(StringUtils.formatColor(Config.MESSAGE_NEW_UPDATE));
			});
		}

		new Metrics(this, metricsID);

		super.onEnable();
	}

	public void initConfig() {
		Config.UPDATE_CHECKER = config.getBoolean("update-checker");

		Config.RESET_PHANTOM_STATS = config.getBoolean("reset.phantom-statistics");
		Config.RESET_STORM = config.getBoolean("reset.storm");
		Config.RESET_THUNDER = config.getBoolean("reset.thunder");

		Config.MESSAGE_HELP = config.getString("message.help");
		Config.MESSAGE_RELOADED = config.getString("message.reloaded");
		Config.MESSAGE_NO_PERMISSION = config.getString("message.no-permission");
		Config.MESSAGE_NEW_UPDATE = config.getString("message.new-update");

		Config.MESSAGE_SLEPT_ENABLE = config.getBoolean("message.slept.enable");
		Config.MESSAGE_SLEPT_PER_WORLD = config.getBoolean("message.slept.per-world");
		Config.MESSAGE_SLEPT_MESSAGES = config.getStringList("message.slept.messages");
	}

	public static void getLatestVersion(Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.INSTANCE, () -> {
			try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID)
					.openStream(); Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				Bukkit.getLogger().warning("Unable to check for updates");
			}
		});
	}

}
