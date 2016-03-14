package net.re_renderreality.rrrp2.main;

import java.util.Hashtable;
import java.util.Set;

import org.spongepowered.api.entity.living.player.Player;

/**
 * @author Avarai
 */
public class PlayerRegistry {
	
	private Hashtable<String, String> lastSeen; //hash table for last seen
	private Hashtable<String, String> players; //hash table for players each key is the players UUID
	
	public PlayerRegistry() {  //constructor for new hash tables
		//UUID:NAME
		players = new Hashtable<String, String>(); //creates new public players hashtable
		//UUID:TIME
		lastSeen = new Hashtable<String, String>(); //creates new public lastseen hashtable
	}
	
	/**
	 * @param players Hashtable<String, String> object of players to initiate with.
	 * @param lastSeen Hashtable<String, String> object of times last seen to initiate with.
	 */
	public PlayerRegistry(Hashtable<String, String> players, Hashtable<String, String> lastSeen) { //Alternate hash table for initializing object with two existing hash tables
		this.players = players; //sets private hash table to the one passed to constructor
		this.lastSeen = lastSeen; //sets private hash table to the one passed to constructor
	}
	
	/**
	 * @return Hashtable<String, String> of players to return.
	 */
	public Hashtable<String, String> getPlayers() { return players; } //getter for players hash table
	
	/**
	 * @return Hashtable<String, String> of times last seen to return.
	 */
	public Hashtable<String, String> getLastSeen() { return lastSeen; } //getter for last seen hash table
	
	/**
	 * @return Set<String> set of UUIDs associated with players to return.
	 */
	public Set<String> getUuidSet() { return players.keySet(); } //returns the keys for the players hashtable 
	
	/**
	 * @param uuid UUID associated with player to return.
	 * @return Player associated with UUID.
	 */
	public String getPlayer(String uuid) { return players.get(uuid); } //returns player associated with the UUID
	
	/**
	 * @param name Name of player to get UUID for.
	 * @return UUID for player with name of parameter.
	 */
	public String getUuid(String name) { //gets the UUID of the player
		for (String n:players.keySet()) { //searches through every player till 
			if (getPlayer(n).equals(name)) // If finds one matching 
				return n; //return the UUID
		}
		return ""; //if don't find one matching return empty string
	}
	
	/**
	 * @param uuid of player to get last seen time for.
	 * @return String holding last seen time.
	 */
	public String getTime(String uuid) {  //gets last seen time based off of UUID
		String seen = lastSeen.get(uuid);  //gets their time loads into string seen
		if (!seen.isEmpty()) //if string is not empty
			return seen; //return time
		else
			return "never seen on server."; //else return that the user doesnt exsist
	}
	
	/**
	 * @param player Player object to add to registry.
	 */
	public void addPlayer(Player player) { //adds player to registry based on Player Object
		if (!players.contains(player.getUniqueId().toString())) //if player isn't already a part of the hash table
			players.put(player.getUniqueId().toString(), player.getName()); //put them in the hash table
		else
			players.replace(player.getUniqueId().toString(), player.getName()); //or update their current information
	}
	
	/**
	 * @param uuid UUID of player to add to registry.
	 * @param name Name of player to add to registry.
	 */
	public void addPlayer(String uuid, String name) { //adds player to registry with UUID
		if (!players.contains(uuid)) //if player isn't already a part of the hash table
			players.put(uuid, name);//put them in the hash table
		else
			players.replace(uuid, name);  //or update their current information
	}
	
	/**
	 * @param uuid UUID of player to add last seen time for to registry.
	 * @param time Time player was last seen to add to registry.
	 */
	public void addLastSeen(String uuid, String time) { //adds last seen to player
		if (!lastSeen.contains(uuid)) //if player lastseen isn't already a part of the hash table 
			lastSeen.put(uuid, time); //put it in the last seen hash table
		else
			lastSeen.replace(uuid, time); //else replace current info
	}
	
	/**
	 * @param name Name of player to check for in registry.
	 * @return Boolean, True if registry contains player, False otherwise.
	 */
	public boolean containsPlayer(String name) { //checks if player is in hash table based off of provided IGN
		return players.contains(name) ? true : false; //returns true if found false if not
	}
	
	/**
	 * @param player Player to check for in registry.
	 * @return Boolean, True if registry contains name, False otherwise.
	 */
	public boolean containsPlayer(Player player) { //checks if player is in hash table based off of Player Object
		return players.contains(player.getName()) ? true : false; //returns true if found false if not
	}
	
	/**
	 * @param uuid UUID to check for in registry.
	 * @return Boolean, True if registry contains uuid, False otherwise.
	 */
	public boolean containsUuid(String uuid) { //checks if a uuid is a key in the hashtable
		return players.containsKey(uuid) ? true : false; //returns true if found false if not
	}
}