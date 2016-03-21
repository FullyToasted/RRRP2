package net.re_renderreality.rrrp2.database.core;

import org.slf4j.Logger;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Players;
import net.re_renderreality.rrrp2.utils.Utilities;

public class PlayerCore {
	private int ID;
	private String uuid;
	private String name;
	private String nick;
	private String channel;
	private double money;
	private boolean god;
	private boolean fly;
	private boolean tptoggle;
	private boolean invisible;
	private double onlinetime;
	private String lastlocation;
	private String lastdeath;
	private String firstseen;
	private String lastseen;
	/**
	 * @param ID the ID of the player
	 * @param uuid the UUID of the player
	 * @param name the username of the player
	 * @param nick the nickname of the player
	 * @param channel the chat channel the player is in
	 * @param money the money the player has
	 * @param god if the player is god
	 * @param fly if the player can fly
	 * @param tptoggle if tptoggle is enabled
	 * @param invisible if the user is invisible
	 * @param onlinetime how long the player has been online
	 * @param lastlocation last location the player was at
	 * @param lastdeath last death location
	 * @param firstseen date the player first joined
	 * @param lastseen last date the player has joined
	 */
	public PlayerCore(int ID,
					  String uuid, 
					  String name, 
					  String nick, 
					  String channel,
					  double money, 
					  boolean god, 
					  boolean fly, 
					  boolean tptoggle, 
					  boolean invisible, 
					  double onlinetime,
					  String lastlocation, 
					  String lastdeath, 
					  String firstseen, 
					  String lastseen) {
		
		this.ID = ID;
		this.uuid = uuid;
		this.name = name;
		this.nick = nick;
		this.channel = channel;
		this.money = money;
		this.god = god;
		this.fly = fly;
		this.tptoggle = tptoggle;
		this.invisible = invisible;
		this.onlinetime = onlinetime;
		this.lastlocation = lastlocation;
		this.lastdeath = lastdeath;
		this.firstseen = firstseen;
		this.lastseen = lastseen;		
	}
	
	public PlayerCore() {
		;
	}

	/**
	 * inserts original player into the database
	 */
	public void insert() {
		String command = "INSERT INTO players VALUES (" + ID + ", '" + uuid + "', '" + name + "', '" + nick + "', '" + channel + "', " + money + ", " + Utilities.boolToInt(god) + ", " 
												 + Utilities.boolToInt(fly) + ", " + Utilities.boolToInt(tptoggle) + ", " + Utilities.boolToInt(invisible) + ", " + onlinetime 
												 + ", '" + lastlocation + "', '" + lastdeath + "', '" + firstseen + "', '" + lastseen + "')";
		Logger l = RRRP2.getRRRP2().getLogger();
		l.info(command);
		Database.execute(command);
		Players.addPlayer(ID, this);
		Database.addUUID(uuid, ID);
	}
	
	/**
	 * update player in the database
	 * @note update on login
	 */
	public void update() {
		Database.queue("UPDATE players SET name = '" + name + "', nick = '" + nick + "', channel = '" + channel + "', money = " + money + ", god = " + god + ", fly = " + fly 
				 									 + ", tptoggle = " + tptoggle + ", invisible = " + invisible + ", onlinetime = " + onlinetime + ", lastlocation = '" + lastlocation 
				 									 + "', lastdeath = '" + lastdeath + "', firstseen = " + firstseen + ", lastseen = " + lastseen + " WHERE uuid = '" + uuid + "'");
		Players.removePlayer(ID);
		Database.removeUUID(uuid);
		Players.addPlayer(ID, this);
		Database.addUUID(uuid, ID);
	}
	
	/**
	 * delete player from database
	 */
	public void delete() {
		Database.queue("DELETE FROM players WHERE uuid = '" + uuid + "'");
		Players.removePlayer(ID);
		Database.removeUUID(uuid);
	}
	
	//Setters
	/**
	 * @param ID set ID number of the player
	 */
	public void setID(int ID) {
		this.ID = ID; 
	}
	
	/**
	 * @param uuid set uuid of the player
	 */
	public void setUUID(String uuid) { 
		this.uuid = uuid; 
	}
	
	/** 
	 * @param name set Username of Player
	 */
	public void setName(String name) { 
		this.name = name; 
	}
	
	/**
	 * @param nick set nickname of player
	 */
	public void setNick(String nick) { 
		this.nick = nick; 
	}
	
	/**
	 * @param channel set chat channel the player is currently
	 */
	public void setChannel(String channel) { 
		this.channel = channel; 
	}
	
	/**
	 * @param set money the players current balance
	 */
	public void setMoney(double money) { 
		this.money = money; 
	}
	
	/**
	 * @param god set the player in god mode
	 */
	public void setGod(boolean god) { 
		this.god = god;
	}
	
	/**
	 * @param fly set the player to fly mode
	 */
	public void setFly(boolean fly) { 
		this.fly = fly; 
	}
	
	/**
	 * @param tptoggle set if the player has TPToggle Enabled
	 */
	public void setTPToggle(boolean tptoggle) { 
		this.tptoggle = tptoggle; 
	}
	
	/**
	 * @param invisible set if the player is invisible
	 */
	public void setInvisible(boolean invisible) { 
		this.invisible = invisible; 
	}
	
	/**
	 * @param onlinetime sets how long the player has been onlibe
	 */
	public void setOnlinetime(double onlinetime) { 
		this.onlinetime = onlinetime; 
	}
	
	/** 
	 * @param lastlocation Sets last location of player
	 */
	public void setLastlocation(String lastlocation) { 
		this.lastlocation = lastlocation; 
	}
	
	/**
	 * @param lastdeath sets last death point of player
	 */
	public void setLastdeath(String lastdeath) { 
		this.lastdeath = lastdeath; 
	}
	
	/**
	 * @param firstseen sets the first time player was seen
	 */
	public void setFirstseen(String firstseen) { 
		this.firstseen = firstseen; 
	}
	
	/**
	 * @param lastseen sets the time the player was last seen
	 */
	public void setLastseen(String lastseen) { 
		this.lastseen = lastseen; 
	}
	
	//Getters
	/**
	 * @return get ID of the player
	 */
	public int getID() { 
		return ID; 
	}
	
	/**
	 * @return get uuid of the player
	 */
	public String getUUID() { 
		return uuid; 
	}
	
	/**
	 * @return get Username of the player
	 */
	public String getName() { 
		return name; 
	}
	
	/**
	 * @return get nickname of player
	 */
	public String getNick() { 
		return nick; 
	}
	
	/**
	 * @return get players current chat channel
	 */
	public String getChannel() { 
		return channel; 
	}
	
	/**
	 * @return money the player has
	 * 
	 * @note the money is rounded to the nearest penny
	 */
	public double getMoney() { 
		return Math.round(money * 100) / 100; 
	}
	
	/**
	 * @return is the player god status
	 */
	public boolean getGod() { 
		return god; 
	}
	
	/**
	 * @return is the player fly status
	 */
	public boolean getFly() { 
		return fly; 
	}
	
	/**
	 * @return is the player's TpToggle enabled
	 */
	public boolean getTPToggle() { 
		return tptoggle; 
	}
	
	/**
	 * @return is the player invisible
	 */
	public boolean getInvisible() { 
		return invisible; 
	}
	
	/**
	 * @return the players online time
	 */
	public double getOnlinetime() { 
		return onlinetime; 
	}
	
	/**
	 * @return the players last location
	 */
	public String getLastlocation() { 
		return lastlocation; 
	}
	
	/**
	 * @return the players last death location
	 */
	public String getLastdeath() { 
		return lastdeath; 
	}
	
	/**
	 * @return get time the player was first seen
	 */
	public String getFirstseen() {
		return firstseen; 
	}
	
	/**
	 * @return get the time the player was last seen
	 */
	public String getLastseen() { 
		return lastseen; 
	}
	
	/**
	 * @param amount amount of cash to add
	 */
	public void addMoney(double amount) {
		amount *= 100; amount = Math.round(amount); amount /= 100;
		this.money += amount;
	}
	
	/**
	 * @param amount amount of cash to subtract
	 */
	public void removeMoney(double amount) {
		amount *= 100; amount = Math.round(amount); amount /= 100;
		this.money -= amount;
	}
	
	public String toString() {
		String player = "";
		player = player + "ID: " + getID() + "\n";
		player = player + "UUID: " + getUUID() + "\n";
		player = player + "NAME: " + getName() + "\n";
		player = player + "NICK: " + getNick() + "\n";
		player = player + "CHANNEL: " + getChannel() + "\n";
		player = player + "MONEY: " + getMoney() + "\n";
		player = player + "GOD: " + getGod() + "\n";
		player = player + "FLY: " + getFly() + "\n";
		player = player + "TPTOGGLE: " + getTPToggle() + "\n";
		player = player + "INVISIBLE: " + getInvisible() + "\n";
		player = player + "ONLINETIME: " + getOnlinetime() + "\n";
		player = player + "LAST LOCATION: " + getLastlocation() + "\n";
		player = player + "LAST DEATH: " + getLastdeath() + "\n";
		player = player + "FIRST SEEN: " + getFirstseen() + "\n";
		player = player + "Last SEEN: " + getLastseen();
		
		return player;
	}
}