package net.re_renderreality.rrrp2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.re_renderreality.rrrp2.backend.CommandLoader;
import net.re_renderreality.rrrp2.config.*;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.main.Registry;
import net.re_renderreality.rrrp2.utils.AFK;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import me.flibio.updatifier.Updatifier;

@Updatifier(repoName = "RRRP2", repoOwner = "ReRenderReality", version = "v" + PluginInfo.VERSION)
@Plugin(id = "rrr.commands", name = "RRRP2", version = "0.1.0-ALPHA", description = "basic commands for RRP2 Bundle") 
public class RRRP2{
	
	protected RRRP2 () {
		
	}
	
	@Inject 
	public Game game;
	@Inject 
	private PluginContainer container;
	@Inject 
	private Logger logger;
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;
	
	public static RRRP2 plugin;
	private Server server;
	private PlayerRegistry players;
	public static HashMap<UUID, AFK> afkList = new HashMap<>();
	public static List<Player> recentlyJoined = Lists.newArrayList();
	
	public static RRRP2 getRRRP2() {
		return plugin;
	}
	/**
	 * @param event Listener for GameStartingServerEvent.
	 * @note Initialization of Plugin and Registry.
	 */
	
	@Listener
	public void onPreInitialization(GamePreInitializationEvent event)
	{
		getLogger().info(container.getName() + ": Config Initiallation Beginning....");
		plugin = this;

		// Create Config Directory for EssentialCmds
		if (!Files.exists(configDir))
		{
			getLogger().info(container.getName() + ": Config root not found generating...");
			if (Files.exists(configDir.resolveSibling("RRRP2")))
			{
				try
				{
					Files.move(configDir.resolveSibling("RRRP2"), configDir);
					getLogger().info(container.getName() + ": Config root generated");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					Files.createDirectories(configDir);
					getLogger().info(container.getName() + ": Config root generated");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		// Create data Directory for EssentialCmds
		if (!Files.exists(configDir.resolve("data")))
		{
			getLogger().info(container.getName() + ": Config data subfolder not found generating...");
			try
			{
				getLogger().info(container.getName() + ": Config data subfolder generated");
				Files.createDirectories(configDir.resolve("data"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		getLogger().info(container.getName() + ": Config Initiallation Finished");
	}
	@Listener 
	public void gameStarting(GameStartingServerEvent event) {
		server = game.getServer();
		Registry.setGame(getGame());
		Registry.setLogger(getLogger());
		players = new PlayerRegistry();
		CommandLoader.registerCommands();
		
		Config.getConfig().setup();
		Messages.getConfig().setup();
		MOTD.getConfig().setup();
		Warps.getConfig().setup();
		Spawn.getConfig().setup();
		
		Database.setup(game);
    	Database.load(game);
		
		//To be replaced with local SQL database usage instead of text file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("players.rrr"));
			while (reader.ready()) {
				String line = reader.readLine();
				players.addPlayer(line.substring(line.indexOf('/', line.lastIndexOf('/'))), line.substring(0, line.indexOf('/')));
				players.addLastSeen(line.substring(line.indexOf('/', line.lastIndexOf('/'))), line.substring(line.lastIndexOf('/')+1, line.length()));
			}
			reader.close();
		} catch (Exception e) { getLogger().info("[ERROR] Something went wrong with RRRP2."); }
		//END TO BE REPLACED SECTION
		
		getLogger().info(container.getName() + " v" + container.getVersion().get() + " has successfully been initialized.");
	}
	
	/**
	 * @param event Listener for "GameStoppingServerEvent" event.
	 */
	@Listener 
	public void gameStopping(GameStoppingServerEvent event) {
		
		//To be replaced with local SQL database usage instead of text file
		try {
			new java.io.File("players.rrr").delete();
			BufferedWriter writer = new BufferedWriter(new FileWriter("players.rrr"));
			for (String uuid:players.getUuidSet()) {
				writer.write(players.getPlayer(uuid) + "/" + uuid + "/" + players.getTime(uuid));
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
		//END TO BE REPLACED SECTION
		
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been un-initialized.");
	}
	
	/**
	 * @param event Listener for "ClientConnectionEvent.Login" event.
	 */
	@Listener
	public void playerJoined(ClientConnectionEvent.Login event) {
		players.addPlayer(event.getTargetUser().getUniqueId().toString(), event.getTargetUser().getName());
		String time = LocalTime.now().toString();
		time = LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.'));
		players.addLastSeen(event.getTargetUser().getUniqueId().toString(), time);
	}
	
	/**
	 * @param event Listener for "ClientConnectionEvent.Disconnect" event.
	 */
	@Listener
	public void playerLeft(ClientConnectionEvent.Disconnect event) {
		String time = LocalTime.now().toString();
		time = LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.'));
		players.addLastSeen(event.getTargetEntity().getUniqueId().toString(), time);	
	}
	
	public String goml() {
		return "";
	}
	
	public Path getConfigDir() {
		return configDir;
	}
	
	/**
	 * @return Logger for logging status messages.
	 */
	public Logger getLogger() { return logger; }

	/**
	 * @return current Server Object.
	 */
	public Server getServer() { return server; }
	
	/**
	 * @return current Game Object.
	 */
	public Game getGame() { return game; }
	
	/**
	 * @return get PlayerRegistry Object.
	 */
	public PlayerRegistry getPlayerRegistry() { return players; }
}