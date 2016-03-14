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

public class Utilities { //basic api
	
	public static BaseCommand[] baseCommands; //kind of global Array of Commands
	
	/**
	 * @return PaginationService object associated with Registry's Game object.
	 */
	public static PaginationService getPaginationService() { //Gets the Pagination service. Allows pages. Ex: Help pages
		return Registry.getGame().getServiceManager().provide(PaginationService.class).get(); //uses Optional's to make sure there is a pagination service and returns the pagination service
	}
	
	/**
	 * @param strings to be formatted
	 * @return String array of formatted strings.
	 */
	public static String[] stringFormatter(String... strings) { //Takes out spaces?
		return strings;
	}
	
	/**
	 * @param vals a integer array of values.
	 * @return the mean of all values in parameter array.
	 */
	public static double getMean(int[] vals) { //calculates mean of an array of ints
		double sum = 0.0; //sum
		for (int val : vals) //add sum
			sum+=val;
		return sum/vals.length; //Divide sum by length of array
	}

	/**
	 * @return current server ticks per second.
	 */
	public static double getTps() { //gets tps of server
		return Registry.getGame().getServer().getTicksPerSecond(); //from the registered game object get the registered server from the server get TPS	
	}

	/**
	 * @return MessageChannel to message all server players.
	 */
	public static MessageChannel getBroadcastChannel() { //gets broadcast channel
		return Registry.getServer().getBroadcastChannel(); //returns the channel found through the registry 
		//Look at SpongeApi on MessangeChannels
	}
	
	/**
	 * @param message Text message to be broadcast to server.
	 */
	public static void broadcastMessage(Text message) {  //sends message to everyone on server using Text object
		getBroadcastChannel().send(message); 
	}
	
	/**
	 * @param message String message to be broadcast to server.
	 */
	public static void broadcastMessage(String message) { //sends message to everyone on server using String object
		getBroadcastChannel().send(Text.of(message)); //convert string to Text then send
	}
	
	/**
	 * @param name of player to search for.
	 * @return Optional<Player> with player data or Optional.empty() if unfound.
	 */
	public static Optional<Player> getPlayer(String name) { //Uses Optional to avoid Null errors. Returns a Optional<Player> or optional.empty() if no player in hash table
		return Registry.getServer().getPlayer(name);
	}
	
	/**
	 * @param player Player to get location for.
	 * @return Location<World> of Player player.
	 */
	public static Location<World> getPlayerLocation(Player player) { //gets online players location in the world
		return player.getLocation(); //(x,y,z)
	}
	
	/**
	 * @param name String of player name to get location for.
	 * @return Location<World> of Player with String name.
	 */
	public static Location<World> getPlayerLocation(String name) { //gets player using IGN
		return getPlayer(name).get().getLocation(); //converts IGN to Player. uses .get to get player out of Optional then gets Players Location
	}
	
	/**
	 * @return names of all server worlds stored in a String array.
	 */
	public static String[] getWorldNames() { //gets list of worlds
		Object[] objs = Registry.getServer().getWorlds().toArray(); //converts list of world Obj to array
		String[] names = new String[objs.length]; //gets the names of worlds. array set to length of found worlds
		int i = 0;
		for (Object obj:objs) //iterates over every world object/
		{
			names[i] = obj.toString(); //converts object name to string and adds to names array
			i+=1;
		}
		return names; //returns array
	}
	
	/**
	 * @return boolean check if PlayerSource.
	 */
	public static boolean isPlayer(Object src) { //checks if the source of an object came from a player
		return (src instanceof Player); 
	}
	
	/**
	 * @return boolean check if ConsoleSource.
	 */
	public static boolean isConsole(Object src) { return (src instanceof ConsoleSource); }//checks if the source of an object came from a Console
	
	/**
	 * @return boolean check if CommandBlockSource.
	 */
	public static boolean isCommandBlock(Object src) { return (src instanceof CommandBlockSource); }//checks if the source of an object came from a Command Block
	
	/**
	 * @param src Object to check for class of.
	 * @return String of class name.
	 */
	public static String srcOf(Object src) { //finds the name of the class of which the object belongs
		return src.getClass().getName();
	}
	
	/**
	 * @return CommandManager object associated with Registry's stored Game Object.
	 */
	public static CommandManager getCommandManager() { //returns command Manager object form the Game Object
		return Registry.getGame().getCommandManager();
	}
	
	/**
	 * @param bool Boolean to convert to a String.
	 * @return Converted Boolean in a String object.
	 */
	public static String boolToString(boolean bool) { //converts boolean to string
		return bool ? "True" : "False";
	}
}