package net.re_renderreality.rrrp2.main;

import java.util.Hashtable;
import java.util.Set;

import org.spongepowered.api.entity.living.player.Player;

import net.re_renderreality.rrrp2.database.PlayerCore;

/**
 * @author Avarai/Poesidon2012
 */
public class PlayerRegistry {
	
	private Hashtable<Integer, PlayerCore> players;
	
	public PlayerRegistry() {
		//UUID:NAME
		players = new Hashtable<Integer, PlayerCore>();
	}
	
	/**
	 * @param players Hashtable<String, String> object of players to initiate with.
	 */
	public PlayerRegistry(Hashtable<Integer, PlayerCore> players) {
		this.players = players;
	}
	
	/**
	 * @return Hashtable<String, String> of players to return.
	 */
	public Hashtable<Integer, PlayerCore> getPlayers() { return players; }
	
	/**
	 * @return Hashtable<String, String> of times last seen to return.
	 */

	public Set<Integer> getIdSet() { return players.keySet(); }
	
	/**
	 * @param uuid UUID associated with player to return.
	 * @return Player associated with UUID.
	 */
	public PlayerCore getPlayer(int id) { return players.get(id); }
	
	/**
	 * @param name Name of player to get UUID for.
	 * @return UUID for player with name of parameter.
	 */
	public Integer getID(String name) {
		for (Integer n:players.keySet()) {
			if (getPlayer(n).equals(name))
				return n;
		}
		return -1;
	}
	
	/**
	 * @param player Player object to add to registry.
	 */
	public void addPlayer(PlayerCore player) {
		if (!players.contains(player.getID()))
			players.put(player.getID(), player);
		else
			players.replace(player.getID(), player);
	}
	
	/**
	 * @param uuid UUID of player to add to registry.
	 * @param name Name of player to add to registry.
	 */
	public void addPlayer(int ID, PlayerCore player) {
		if (!players.contains(ID))
			players.put(ID, player);
		else
			players.replace(ID, player);
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