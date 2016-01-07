package net.re_renderreality.rrrp2.main;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;

public class Registry {
	
	public static Game game;
	public static Logger logger;
	public static RRRP2 plugin;
	
	/**
	 * @return registered RRRP2 plugin object.
	 */
	public static RRRP2 getPlugin() {
		return plugin;
	}
	
	/**
	 * @return the registered Game object.
	 */
	public static Game getGame() {
		return game;
	}
	
	/**
	 * @return the registered Server object.
	 */
	public static Server getServer() { 
		return game.getServer();
	}	
	
	/**
	 * @return the registered Logger object.
	 */
	public static Logger getLogger() {
		return logger;
	}
}
