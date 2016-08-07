package net.re_renderreality.rrrp2.database;

import org.spongepowered.api.Game;
import org.spongepowered.api.Server;

import net.re_renderreality.rrrp2.RRRP2;

public class Registry {
	
	private static Game game;
	private static RRRP2 plugin;
	private static OnlinePlayers onlinePlayers;
	public enum helpCategory { Admin, Cheater, General, Teleport, Misc}
	
	/**
	 * @param g Game to set registered Game object to.
	 */
	public static void setGame(Game g) { game = g; }
	
	/**
	 * @param p RRRP2 to set registered RRRP2 object to.
	 */
	public static void setPlugin(RRRP2 p) { plugin = p; }
	
	/**
	 * @param o sets current online players object
	 */
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
	 * @return Current Online Players Object
	 */
	public static OnlinePlayers getOnlinePlayers() { return onlinePlayers; }
}