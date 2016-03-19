package net.re_renderreality.rrrp2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.re_renderreality.rrrp2.backend.CommandLoader;
import net.re_renderreality.rrrp2.config.*;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.listeners.*;
import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.main.Registry;
import net.re_renderreality.rrrp2.utils.AFK;
import net.re_renderreality.rrrp2.utils.HelpGenerator;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
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
		Config.getConfig().setup();
		Messages.getConfig().setup();
		MOTD.getConfig().setup();
		Warps.getConfig().setup();
		Spawn.getConfig().setup();
		
		HelpGenerator.getHelp().populate();
		getLogger().info(container.getName() + ": Config Initiallation Finished");
	}
	
	@Listener
	public void onServerInit(GameInitializationEvent event)
	{
		getGame().getEventManager().registerListeners(this, new PlayerJoinListener());
		getGame().getEventManager().registerListeners(this, new MailListener());
		getGame().getEventManager().registerListeners(this, new PlayerLeftEvent());
	}
	
	@Listener 
	public void gameStarting(GameStartingServerEvent event) {
		server = game.getServer();
		Registry.setGame(getGame());
		Registry.setLogger(getLogger());
		players = new PlayerRegistry();
		CommandLoader.registerCommands();
		
		Database.setup(game);
    	Database.load(game);
		
		getLogger().info(container.getName() + " v" + container.getVersion().get() + " has successfully been initialized.");
	}
	
	/**
	 * @param event Listener for "GameStoppingServerEvent" event.
	 */
	@Listener 
	public void gameStopping(GameStoppingServerEvent event) {
		
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been un-initialized.");
	}
	
	/**
	 * @param event Listener for "ClientConnectionEvent.Disconnect" event.
	 */
	
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