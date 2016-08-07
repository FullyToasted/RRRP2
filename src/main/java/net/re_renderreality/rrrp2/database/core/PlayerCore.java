package net.re_renderreality.rrrp2.database.core;

import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.utils.Log;
import net.re_renderreality.rrrp2.utils.Utilities;

public class PlayerCore {
	private int ID;
	private String uuid;
	private String name;
	private String ip;
	private String nick;
	private String channel;
	private double money;
	private boolean muted;
	private boolean banned;
	private boolean god;
	private boolean fly;
	private boolean jailed;
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
					  String ip,
					  String nick, 
					  String channel,
					  double money, 
					  boolean muted,
					  boolean banned,
					  boolean god, 
					  boolean fly, 
					  boolean jailed,
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
		this.ip = ip;
		this.nick = nick;
		this.channel = channel;
		this.banned = banned;
		this.money = money;
		this.muted = muted;
		this.god = god;
		this.fly = fly;
		this.jailed = jailed;
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
		String command = "INSERT INTO players VALUES (" + ID + ", '" + uuid + "', '" + name + "', '" + ip + "', '" + nick + "', '" + channel + "', " + money + ", " + Utilities.boolToInt(muted) + ", " 
														+ Utilities.boolToInt(banned) + ", " + Utilities.boolToInt(god) + ", " + Utilities.boolToInt(fly) + ", " + Utilities.boolToInt(tptoggle) + ", " 
														+ Utilities.boolToInt(jailed) + ", " + Utilities.boolToInt(invisible) + ", " + onlinetime + ", '" + lastlocation + "', '" + lastdeath + "', '" 
														+ firstseen + "', '" + lastseen + "')";
		Database.execute(command);
		Database.addUUID(uuid, ID);
	}
	
	/**
	 * update player in the database
	 * @note update on login
	 */
	public void update() {
		String command = "UPDATE players SET ID = " + ID + ", uuid = '" + uuid + "', name = '" + name + "', IP = '" + ip + "', nick = '" + nick + "', channel = '" + channel + "', money = " + money + ", muted = " + Utilities.boolToInt(muted)
 													+ ", banned = " + Utilities.boolToInt(banned) + ", god = " + Utilities.boolToInt(god) + ", fly = " + Utilities.boolToInt(fly) + ", jailed = " + Utilities.boolToInt(jailed) + ", tptoggle = " + Utilities.boolToInt(tptoggle) 
													+ ", invisible = " + Utilities.boolToInt(invisible) + ", onlinetime = " + onlinetime + ", lastlocation = '" + lastlocation 
													+ "', lastdeath = '" + lastdeath + "', firstseen = '" + firstseen + "', lastseen = '" + lastseen + "' WHERE ID = " + ID + ";";
		Log.debug(command);
		Database.execute(command);

	}
	
	/**
	 * delete player from database
	 */
	public void delete() {
		Database.queue("DELETE FROM players WHERE uuid = '" + uuid + "'");
		Database.removeUUID(uuid);
	}
	
	/**
	 * @param ID set ID of user
	 */
	public void setIDUpdate(int ID) {
		this.ID = ID; 
		String command = "UPDATE players SET ID = " + this.ID + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param uuid set uuid of the player
	 */
	public void setUUIDUpdate(String uuid) { 
		this.uuid = uuid; 
		String command = "UPDATE players SET uuid = '" + this.uuid + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/** 
	 * @param name set Username of Player
	 */
	public void setNameUpdate(String name) { 
		this.name = name; 
		String command = "UPDATE players SET name = '" + this.name + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/** 
	 * @param name set IP of Player
	 */
	public void setIPUpdate(String ip) { 
		this.ip = ip; 
		String command = "UPDATE players SET IP = '" + this.ip + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param nick set nickname of player
	 */
	public void setNickUpdate(String nick) { 
		this.nick = nick; 
		String command = "UPDATE players SET nick = '" + this.nick + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param channel set chat channel the player is currently
	 */
	public void setChannelUpdate(String channel) { 
		this.channel = channel; 
		String command = "UPDATE players SET channel = '" + this.channel + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param set money the players current balance
	 */
	public void setMoneyUpdate(double money) { 
		this.money = money; 
		String command = "UPDATE players SET money = " + this.money + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param sets player's muted status
	 */
	public void setMutedUpdate(boolean muted) { 
		this.muted = muted; 
		String command = "UPDATE players SET muted = " + this.muted + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param god set the player in god mode
	 */
	public void setBannedUpdate(boolean banned) { 
		this.banned = banned;
		String command = "UPDATE players SET banned = " + Utilities.boolToInt(this.banned) + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param god set the player in god mode
	 */
	public void setGodUpdate(boolean god) { 
		this.god = god;
		String command = "UPDATE players SET god = " + Utilities.boolToInt(this.god) + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param fly set the player to fly mode
	 */
	public void setFlyUpdate(boolean fly) { 
		this.fly = fly; 
		String command = "UPDATE players SET fly = " + Utilities.boolToInt(this.fly) + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param sets player's jailed status
	 */
	public void setJailedUpdate(boolean jailed) { 
		this.jailed = jailed; 
		String command = "UPDATE players SET jailed = " + this.jailed + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param tptoggle set if the player has TPToggle Enabled
	 */
	public void setTPToggleUpdate(boolean tptoggle) { 
		this.tptoggle = tptoggle;
		String command = "UPDATE players SET tptoggle = " + Utilities.boolToInt(this.tptoggle) + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param invisible set if the player is invisible
	 */
	public void setInvisibleUpdate(boolean invisible) { 
		this.invisible = invisible;
		String command = "UPDATE players SET invisible = " + Utilities.boolToInt(this.invisible) + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param onlinetime sets how long the player has been onlibe
	 */
	public void setOnlinetimeUpdate(double onlinetime) { 
		this.onlinetime = onlinetime; 
		String command = "UPDATE players SET onlinetime = " + this.onlinetime + " WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/** 
	 * @param lastlocation Sets last location of player
	 */
	public void setLastlocationUpdate(String lastlocation) { 
		this.lastlocation = lastlocation; 
		String command = "UPDATE players SET lastlocation = '" + this.lastlocation + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param lastdeath sets last death point of player
	 */
	public void setLastdeathUpdate(String lastdeath) { 
		this.lastdeath = lastdeath; 
		String command = "UPDATE players SET lastdeath = '" + this.lastdeath + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param firstseen sets the first time player was seen
	 */
	public void setFirstseenUpdate(String firstseen) { 
		this.firstseen = firstseen; 
		String command = "UPDATE players SET firstseen  = '" + this.firstseen + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
	}
	
	/**
	 * @param lastseen sets the time the player was last seen
	 */
	public void setLastseenUpdate(String lastseen) { 
		this.lastseen = lastseen; 
		String command = "UPDATE players SET lastseen = '" + this.lastseen + "' WHERE ID = "+ this.ID + ";";
		Database.execute(command);
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
	 * @param ip set ip of Player
	 */
	public void setIP(String ip) { 
		this.ip = ip; 
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
	 * @param set the player to be muted or unmuted
	 */
	public void setMuted(boolean muted) { 
		this.muted = muted;
	}
	
	/**
	 * @param god set the player in god mode
	 */
	public void setBanned(boolean banned) { 
		this.banned = banned;
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
	 * @param god set the player in jailed mode
	 */
	public void setJailed(boolean jailed) { 
		this.jailed = jailed;
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
	 * @return ip of player
	 */
	public String getIP() {
		return ip;
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
	 * @return returns Mute status
	 */
	public boolean getMuted() {
		return muted;
	}
	
	/**
	 * @return returns Ban status
	 */
	public boolean getBanned() {
		return banned;
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
	 * @return returns Jail status
	 */
	public boolean getJailed() {
		return jailed;
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
		player = player + "IP: " + getIP() + "\n";
		player = player + "NICK: " + getNick() + "\n";
		player = player + "CHANNEL: " + getChannel() + "\n";
		player = player + "MONEY: " + getMoney() + "\n";
		player = player + "MUTED: " + getMuted() + "\n";
		player = player + "BANNED: " + getBanned() + "\n";
		player = player + "GOD: " + getGod() + "\n";
		player = player + "FLY: " + getFly() + "\n";
		player = player + "JAILED: " + getJailed() + "\n";
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