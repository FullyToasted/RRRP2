package net.re_renderreality.rrrp2.main;

import java.util.Hashtable;
import java.util.Set;

import org.spongepowered.api.entity.living.player.Player;

/**
 * @author Avarai
 */
public class PlayerRegistry {
	
	private Hashtable<String, String> lastSeen;
	private Hashtable<String, String> players;
	
	public PlayerRegistry() {
		//UUID:NAME
		players = new Hashtable<String, String>();
		//UUID:TIME
		lastSeen = new Hashtable<String, String>();
	}
	
	/**
	 * @param players Hashtable<String, String> object of players to initiate with.
	 * @param lastSeen Hashtable<String, String> object of times last seen to initiate with.
	 */
	public PlayerRegistry(Hashtable<String, String> players, Hashtable<String, String> lastSeen) {
		this.players = players;
		this.lastSeen = lastSeen;
	}
	
	/**
	 * @return Hashtable<String, String> of players to return.
	 */
	public Hashtable<String, String> getPlayers() { return players; }
	
	/**
	 * @return Hashtable<String, String> of times last seen to return.
	 */
	public Hashtable<String, String> getLastSeen() { return lastSeen; }
	
	/**
	 * @return Set<String> set of UUIDs associated with players to return.
	 */
	public Set<String> getUuidSet() { return players.keySet(); }
	
	/**
	 * @param uuid UUID associated with player to return.
	 * @return Player associated with UUID.
	 */
	public String getPlayer(String uuid) { return players.get(uuid); }
	
	/**
	 * @param name Name of player to get UUID for.
	 * @return UUID for player with name of parameter.
	 */
	public String getUuid(String name) {
		for (String n:players.keySet()) {
			if (getPlayer(n).equals(name))
				return n;
		}
		return "";
	}
	
	/**
	 * @param uuid of player to get last seen time for.
	 * @return String holding last seen time.
	 */
	public String getTime(String uuid) { return lastSeen.get(uuid); }
	
	/**
	 * @param player Player object to add to registry.
	 */
	public void addPlayer(Player player) {
		if (!players.contains(player.getUniqueId().toString()))
			players.put(player.getUniqueId().toString(), player.getName());
		else
			players.replace(player.getUniqueId().toString(), player.getName());
	}
	
	/**
	 * @param uuid UUID of player to add to registry.
	 * @param name Name of player to add to registry.
	 */
	public void addPlayer(String uuid, String name) {
		if (!players.contains(uuid))
			players.put(uuid, name);
		else
			players.replace(uuid, name);
	}
	
	/**
	 * @param uuid UUID of player to add last seen time for to registry.
	 * @param time Time player was last seen to add to registry.
	 */
	public void addLastSeen(String uuid, String time) {
		if (!lastSeen.contains(uuid))
			lastSeen.put(uuid, time);
		else
			lastSeen.replace(uuid, time);
	}
	
	/**
	 * @param name Name of player to check for in registry.
	 * @return Boolean, True if registry contains player, False otherwise.
	 */
	public boolean containsPlayer(String name) {
		return players.contains(name) ? true : false;
	}
	
	/**
	 * @param player Player to check for in registry.
	 * @return Boolean, True if registry contains name, False otherwise.
	 */
	public boolean containsPlayer(Player player) {
		return players.contains(player.getName()) ? true : false;
	}
	
	/**
	 * @param uuid UUID to check for in registry.
	 * @return Boolean, True if registry contains uuid, False otherwise.
	 */
	public boolean containsUuid(String uuid) {
		return players.containsKey(uuid) ? true : false;
	}
}