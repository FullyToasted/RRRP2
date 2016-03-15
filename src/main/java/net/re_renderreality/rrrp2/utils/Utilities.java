package net.re_renderreality.rrrp2.utils;

import java.util.Optional;

import net.re_renderreality.rrrp2.cmd.BaseCommand;
import net.re_renderreality.rrrp2.main.Registry;

import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Utilities {
	
	public static BaseCommand[] baseCommands;
	
	/**
	 * @return PaginationService object associated with Registry's Game object.
	 */
	public static PaginationService getPaginationService() {
		return Registry.getGame().getServiceManager().provide(PaginationService.class).get();
	}
	
	/**
	 * @param strings to be formatted
	 * @return String array of formatted strings.
	 */
	public static String[] stringFormatter(String... strings) {
		return strings;
	}
	
	/**
	 * @param vals a integer array of values.
	 * @return the mean of all values in parameter array.
	 */
	public static double getMean(int[] vals) {
		double sum = 0.0;
		for (int val : vals)
			sum+=val;
		return sum/vals.length;
	}

	/**
	 * @return current server ticks per second.
	 */
	public static double getTps() { 
		return Registry.getGame().getServer().getTicksPerSecond();	
	}

	/**
	 * @return MessageChannel to message all server players.
	 */
	public static MessageChannel getBroadcastChannel() { 
		return Registry.getServer().getBroadcastChannel();
	}
	
	/**
	 * @param message Text message to be broadcast to server.
	 */
	public static void broadcastMessage(Text message) { 
		getBroadcastChannel().send(message);
	}
	
	/**
	 * @param message String message to be broadcast to server.
	 */
	public static void broadcastMessage(String message) { 
		getBroadcastChannel().send(Text.of(message));
	}
	
	/**
	 * @param name of player to search for.
	 * @return Optional<Player> with player data or Optional.empty() if unfound.
	 */
	public static Optional<Player> getPlayer(String name) {
		return Registry.getServer().getPlayer(name);
	}
	
	/**
	 * @param player Player to get location for.
	 * @return Location<World> of Player player.
	 */
	public static Location<World> getPlayerLocation(Player player) {
		return player.getLocation();
	}
	
	/**
	 * @param name String of player name to get location for.
	 * @return Location<World> of Player with String name.
	 */
	public static Location<World> getPlayerLocation(String name) {
		return getPlayer(name).get().getLocation();
	}
	
	/**
	 * @return names of all server worlds stored in a String array.
	 */
	public static String[] getWorldNames() {
		Object[] objs = Registry.getServer().getWorlds().toArray();
		String[] names = new String[objs.length];
		int i = 0;
		for (Object obj:objs)
		{
			names[i] = obj.toString();
			i+=1;
		}
		return names;
	}
	
	/**
	 * @return boolean check if PlayerSource.
	 */
	public static boolean isPlayer(Object src) { return (src instanceof Player); }
	
	/**
	 * @return boolean check if ConsoleSource.
	 */
	public static boolean isConsole(Object src) { return (src instanceof ConsoleSource); }
	
	/**
	 * @return boolean check if CommandBlockSource.
	 */
	public static boolean isCommandBlock(Object src) { return (src instanceof CommandBlockSource); }
	
	/**
	 * @param src Object to check for class of.
	 * @return String of class name.
	 */
	public static String srcOf(Object src) {
		return src.getClass().getName();
	}
	
	/**
	 * @return CommandManager object associated with Registry's stored Game Object.
	 */
	public static CommandManager getCommandManager() {
		return Registry.getGame().getCommandManager();
	}
	
	/**
	 * @param bool Boolean to convert to a String.
	 * @return Converted Boolean in a String object.
	 */
	public static String boolToString(boolean bool) {
		return bool ? "True" : "False";
	}
}