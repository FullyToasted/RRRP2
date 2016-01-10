package net.re_renderreality.rrrp2.main;

import net.re_renderreality.rrrp2.cmd.CommandSpecFactory;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.inject.Inject;

@Plugin(id = "Re-RenderReality", name = "RRRP2", version = "0.1.0-ALPHA") 
public class RRRP2{
	
	@Inject 
	public Game game;
	@Inject 
	private PluginContainer container;
	@Inject 
	private Logger logger;
	public RRRP2 plugin;
	private Server server;
	
	/**
	 * @param event Listener for GameStartingServerEvent.
	 * @note Initialization of Plugin and Registry.
	 */
	@Listener 
	public void gameStarting(GameStartingServerEvent event) {
		plugin = this;
		server = game.getServer();
		Registry.setGame(getGame());
		Registry.setLogger(getLogger());
		Registry.setPlugin(this);
		new CommandSpecFactory().buildCommandSpecs();
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been initialized.");
	}
	
	/**
	 * @param event Listener for GameStoppingServerEvent.
	 */
	@Listener 
	public void gameStopping(GameStoppingServerEvent event) {
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been un-initialized.");
	}
	
	
	/**
	 * @return Logger for logging status messages.
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return current Server Object.
	 */
	public Server getServer() {
		return server; 
	}
	
	/**
	 * @return current Game Object.
	 */
	public Game getGame() { 
		return game;
	}
}