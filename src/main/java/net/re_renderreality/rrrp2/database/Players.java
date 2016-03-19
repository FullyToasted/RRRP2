package net.re_renderreality.rrrp2.database;

import java.util.HashMap;

import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class Players {
	//Start Players
	private static HashMap<Integer, PlayerCore> players = new HashMap<Integer, PlayerCore>();
	
	/**
	 * @param ID of player to add to Player Hashmap
	 * @param player PlayerCore object defining player
	 * 
	 * @note call this when a player joins
	 */
	public static void addPlayer(int ID, PlayerCore player) { 
		if(!players.containsKey(ID)) 
			players.put(ID, player); 
	}
	
	/**
	 * @param ID of PlayerCore obj for wanted player
	 * @param time time the player was last on the server
	 * 
	 * @note call thes when /played /whois or /seen is called
	 */
	public static void setLastTimePlayerJoined(int ID, String time)
	{
		if(players.containsKey(ID)) {
			players.get(ID).setLastseen(time);
			if(players.get(ID).getFirstseen() == null) {
				players.get(ID).setFirstseen(time);
			}
		}
	}
	
	/**
	 * @param ID of PlayerCore obj for wanted player
	 * 
	 * @note call this when a player logs off
	 */
	public static void removePlayer(int ID) { 
		if(players.containsKey(ID)) 
			players.remove(ID); 
	}
	
	/**
	 * @param ID of PlayerCore obj for wanted player
	 * @return if desired player is logged onto the server
	 */
	public static PlayerCore getPlayer(int ID) { 
		return players.containsKey(ID) ? players.get(ID) : null; 
	
	}
	
	/**
	 * @return a list of players that are currently on the server
	 */
	public static HashMap<Integer, PlayerCore> getPlayers() { 
		return players; 
	}
	//End Players
}
