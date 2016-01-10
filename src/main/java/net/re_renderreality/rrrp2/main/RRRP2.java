package net.re_renderreality.rrrp2.main;

import net.re_renderreality.rrrp2.cmd.CommandSpecFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	 * @author Avarai
	 * @param event Listener for "ClientConnectEvent.Join event".
	 */
	@Listener
	public void playerJoined(ClientConnectionEvent.Join event) {
		ArrayList<String> players = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("players.rrr"));
			while (reader.ready()) {
				players.add(reader.readLine());
			}
			reader.close();
		} catch (Exception e) {	e.printStackTrace(); }

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("players.rrr"));
	
			for (String p:players) {
				writer.write(p);
				writer.newLine();
			}
			writer.write(event.getTargetEntity().getName());
			writer.newLine();
			writer.close();
		} catch (IOException e) { e.printStackTrace(); }
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