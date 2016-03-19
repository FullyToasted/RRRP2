package net.re_renderreality.rrrp2.database;

import java.util.HashMap;

import net.re_renderreality.rrrp2.database.core.BanCore;

public class Bans {
	//Start Bans
		private static HashMap<Integer, BanCore> bans = new HashMap<Integer, BanCore>();
		
		/**
		 * @param ID ID # of the Player
		 * @param ban BanCore object that describes how long the player will be banned
		 * 
		 * @note Adds a ban to the server ban list
		 * TODO: hook into MySQL database
		 */
		public static void addBan(int ID, BanCore ban) { 
			if(!bans.containsKey(ID)) 
				bans.put(ID, ban); 
		}
		
		/**
		 * @param ID ID # of the Player
		 * 
		 * @note Removes a ban from the server ban list
		 * TODO: hook into MySQL database
		 */
		public static void removeBan(int ID) { 
			if(bans.containsKey(ID)) 
				bans.remove(ID); 
		}
		
		
		/**
		 * @param ID ID # of the Player
		 * @return True or False if player is on the ban list
		 * 
		 * @note Checks if a player is on the ban list 
		 * TODO: hook into MySQL database
		 */
		public static BanCore getBan(int ID) { 
			return bans.containsKey(ID) ? bans.get(ID) : null; 
		}
		
		/**
		 * @return a hashmap of every user on the ban list and their ban reasons
		 * 
		 * @note TODO: hook this into the MySQL Database
		 */
		public static HashMap<Integer, BanCore> getBans() { 
			return bans; 
		}
		//End Bans
}
