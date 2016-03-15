package net.re_renderreality.rrrp2.main;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;

public class Registry {
	
	private static Game game;
	private static Logger logger;
	
	/**
	 * @param g Game to set registered Game object to.
	 */
	public static void setGame(Game g) { game = g; }
	
	/**
	 * @param l Logger to set registered Logger object to.
	 */
	public static void setLogger(Logger l) { logger = l; }
	
	/**
	 * @param p RRRP2 to set registered RRRP2 object to.
	 */
	//public void setPlugin(RRRP2 p) { RRRP2.plugin = plugin; }
	
	/**
	 * @return registered RRRP2 plugin object.
	 */
	//public static RRRP2 getPlugin() { return plugin; }
	
	/**
	 * @return the registered Game object.
	 */
	public static Game getGame() { return game; }
	
	/**
	 * @return the registered Server object.
	 */
	public static Server getServer() { return game.getServer(); }	
	
	/**
	 * @return the registered Logger object.
	 */
	public static Logger getLogger() { return logger; }
}