package net.re_renderreality.rrrp2.database;

import java.util.HashMap;

import net.re_renderreality.rrrp2.database.core.MuteCore;

public class Mutes {
	//Start Mute
	private static HashMap<Integer, MuteCore> mutes = new HashMap<Integer, MuteCore>();
	
	/**
	 * @param ID Player ID of the player to be muted
	 * @param mute MuteCore object defininf the mute
	 */
	public static void addMute(int ID, MuteCore mute) { 
		if(!mutes.containsKey(ID)) mutes.put(ID, mute); 
	
	}
	
	/**
	 * @param ID Player ID of the player to be unmuted
	 */
	public static void removeMute(int ID) { 
		if(mutes.containsKey(ID)) mutes.remove(ID); 
	}
	
	/**
	 * @param ID Player ID to check mute on
	 * @return If muted player is fount T or F
	 */
	public static MuteCore getMute(int ID) { 
		return mutes.containsKey(ID) ? mutes.get(ID) : null; 
	}
	
	/**
	 * @return get a hashmap of all muted players
	 */
	public static HashMap<Integer, MuteCore> getMutes() { 
		return mutes; 
	}
		//End Mute
}
