package net.re_renderreality.rrrp2.database;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;

import net.re_renderreality.rrrp2.RRRP2;

public class Registry {
	
	private static Game game;
	private static Logger logger;
	private static RRRP2 plugin;
	private static OnlinePlayers onlinePlayers;
	public enum helpCategory { Admin, Cheater, General, Teleport, Misc}
	
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
	public static void setPlugin(RRRP2 p) { plugin = p; }
	
	public static void setOnlinePlayers(OnlinePlayers o) { onlinePlayers = o; }
	
	/**
	 * @return registered RRRP2 plugin object.
	 */
	public static RRRP2 getPlugin() { return plugin; }
	
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
	
	public static OnlinePlayers getOnlinePlayers() { return onlinePlayers; }
}