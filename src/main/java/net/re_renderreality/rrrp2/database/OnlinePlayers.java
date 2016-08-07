package net.re_renderreality.rrrp2.database;

import java.util.Hashtable;
import java.util.Set;

import net.re_renderreality.rrrp2.database.core.PlayerCore;

/**
 * @author Avarai
 */
public class OnlinePlayers {
	
	private Hashtable<Integer, PlayerCore> players = new Hashtable<Integer, PlayerCore>();
	
	public OnlinePlayers() {
		;
	}
	
	/**
	 * @return Hashtable<String, String> of players to return.
	 */
	public Hashtable<Integer, PlayerCore> getPlayers() { return players; }
	
	/**
	 * @return Set<String> set of UUIDs associated with players to return.
	 */
	public Set<Integer> getIDSet() { return players.keySet(); }
	
	/**
	 * @param uuid UUID associated with player to return.
	 * @return Player associated with UUID.
	 */
	public PlayerCore getPlayer(int id) { return players.get(id); }
	
	/**
	 * @param name Name of player to get UUID for.
	 * @return UUID for player with name of parameter.
	 */
	public int getID(int id) {
		for (int n:players.keySet()) {
			if (getPlayer(n).getID() == id)
				return n;
		}
		return -1;
	}
	
	/**
	 * @param name Name of player to get ID for.
	 * @return ID for player with name of parameter.
	 */
	public int getIDfromUsername(String username) {
		for (int n:players.keySet()) {
			if(getPlayer(n).getName().equals(username)) {
				return n;
			}
		}
		return -1;
	}
	
	/**
	 * @param name Name of player to get playercore for.
	 * @return playercore for player with name of parameter.
	 */
	public PlayerCore getPlayerCorefromUsername(String username) {
		for (int n:players.keySet()) {
			if(getPlayer(n).getName().equals(username)) {
				return getPlayer(n);
			} else {
				return Database.getPlayerCore(username);
			}
		}
		return null;
	}
	
	/**
	 * @param playercore Player object to add to registry.
	 */
	public void addPlayer(PlayerCore playercore) {
		if (!players.contains(playercore.getID())) 
			players.put(playercore.getID(), playercore);
		else
			players.replace(playercore.getID(), playercore);
	}
	
	public void removePlayer(PlayerCore playercore) {
		if (!players.contains(playercore.getID())) 
			players.remove(playercore.getID());
	}
	
	/**
	 * @param name Name of player to check for in registry.
	 * @return Boolean, True if registry contains player, False otherwise.
	 */
	public boolean containsPlayer(int id) {
		return players.contains(id) ? true : false;
	}
	
	/**
	 * @param player Player to check for in registry.
	 * @return Boolean, True if registry contains name, False otherwise.
	 */
	public boolean containsPlayer(PlayerCore player) {
		return players.contains(player.getID()) ? true : false;
	}
	
	/**
	 * @param uuid UUID to check for in registry.
	 * @return Boolean, True if registry contains uuid, False otherwise.
	 */
	public boolean containsID(int id) {
		return players.containsKey(id) ? true : false;
	}
}