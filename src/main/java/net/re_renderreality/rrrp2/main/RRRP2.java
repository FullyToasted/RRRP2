package net.re_renderreality.rrrp2.main;

import net.re_renderreality.rrrp2.cmd.CommandSpecFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.slf4j.Logger;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
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
	private Hashtable<String, String> players = new Hashtable<String, String>();
	
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
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("players.rrr"));
			while (reader.ready()) {
				String line = reader.readLine();
				players.put(line.substring(line.indexOf(':', line.lastIndexOf(':'))), line.substring(0, line.indexOf(':')));
			}
			reader.close();
		} catch (Exception e) { getLogger().info("[ERROR] \"players.rrr\" does not yet exist, will be instantiated on first player login."); }
	
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
	 * @author Avarai
	 * @note Work in progress -- PLANS: Add "Last seen on server" and expand previously named functionality.
	 * @param event Listener for "ClientConnectEvent.Login" event.
	 */
	@Listener
	public void playerJoined(ClientConnectionEvent.Login event) {	
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("players.rrr"));
	
			if(!players.containsKey(event.getTargetUser().getUniqueId()) || !players.get(event.getTargetUser().getUniqueId()).equals(event.getTargetUser().getName())) {
				writer.write(event.getTargetUser().getName() + ":" + event.getTargetUser().getUniqueId() + ":");
				writer.newLine();
			}
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	/**
	 * @author Avarai
	 * @note Work in progress -- PLANS: use to add "Last seen on server".
	 * @param event Listener for "ClientConnectionEvent.Disconnect" event.
	 */
	@Listener
	public void playerLeft(ClientConnectionEvent.Disconnect event) {
		
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