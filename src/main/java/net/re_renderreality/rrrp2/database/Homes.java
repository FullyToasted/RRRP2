package net.re_renderreality.rrrp2.database;

import java.util.HashMap;

import net.re_renderreality.rrrp2.database.core.HomeCore;

public class Homes {
	//Start Homes
	private static HashMap<Integer, HomeCore> homes = new HashMap<Integer, HomeCore>();
	
	/**
	 * @param ID ID of user to which the home will be added
	 * @param home HomeCore which this home will be added to.
	 * 
	 * @NOTE: TODO: Add support for multiple homes
	 */
	public static void addHome(int ID, HomeCore home) { 
		if(!homes.containsKey(ID)) 
			homes.put(ID, home); 
	}
	
	/**
	 * @param ID ID of user to which the home will be added
	 * 
	 * @NOTE: TODO: Add support for multiple homes
	 */
	public static void removeHome(int ID) { 
		if(homes.containsKey(ID)) 
			homes.remove(ID); 
	}
	
	/**
	 * @param ID ID of user to which the home will be added
	 * @return a HomeCore of the found home
	 * 
	 * @NOTE: TODO: Add support for multiple homes
	 */
	public static HomeCore getHome(int ID) { 
		return homes.containsKey(ID) ? homes.get(ID) : null; 
	}
	
	/**
	 * @return a hashmap of every home in the table
	 * 
	 * @NOTE: TODO: Add support for multiple homes
	 */
	public static HashMap<Integer, HomeCore> getHomes() { 
		return homes; 
	}
	//End Homes
}
