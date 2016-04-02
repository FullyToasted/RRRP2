package net.re_renderreality.rrrp2.database.core;

import net.re_renderreality.rrrp2.database.Database;

public class BanCore {
	private int id;
	private String bannedName;
	private String uuid;
	private String sender;
	private String reason;
	private String time;
	private String duration;
	
	/**
	 * @param ID ID of the player that got banned
	 * @param uuid uuid of the player that got banned
	 * @param sender who banned the player
	 * @param reason why they were banned
	 * @param time time they were banned 
	 * @param duration how long they were banned for
	 */
	public BanCore(int id, String bannedName, String uuid, String sender, String reason, String time, String duration) {
		this.id = id;
		this.bannedName = bannedName;
		this.uuid = uuid;
		this.sender = sender;
		this.reason = reason;
		this.time = time;
		this.duration = duration;
	}
	
	public BanCore() {
		;
	}
	
	//Setters
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setID(int id) { 
		this.id = id; 
	}
	
	public void setBannedName(String name) {
		this.bannedName = name;
	}
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setUUID(String uuid) { 
		this.uuid = uuid; 
	}
	
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setSender(String sender) { 
		this.sender = sender; 
	}
	
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setReason(String reason) { 
		this.reason = reason; 
	}
	
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setTime(String time) { 
		this.time = time; 
	}
	
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setDuration(String duration) { 
		this.duration = duration; 
	}
	
	//Getters
	
	/**
	 * @return the ID number of the player that got banned
	 */
	public int getID() { 
		return id; 
	}
	
	public String getbannedName() {
		return bannedName;
	}
	/**
	 * @return the uuid of the player that got banned
	 */
	public String getUUID() { 
		return uuid; 
	}
	
	/**
	 * @return the Banner of the player that got banned
	 */
	public String getSender() { 
		return sender; 
	}
	
	/**
	 * @return the reason of the player that got banned
	 */
	public String getReason() { 
		return reason; 
	}
	
	/**
	 * @return the Time of the player that got banned
	 */
	public String getTime() { 
		return time; 
	}
	
	/**
	 * @return the Duration of the ban
	 */
	public String getDuration() { 
		return duration; 
	}
	
	//Database Handlers
	/**
	 * inserts ban into the database
	 */
	public void insert() {
		String command = "INSERT INTO bans VALUES (" + id + ", '" + bannedName + "', '" + uuid + "', '" + sender + "', '" + reason + "', '" + time + "', '" + duration + "');";
		Database.execute(command);
	}
	
	/**
	 * updates the ban in the database
	 */
	public void update() {
		Database.execute("UPDATE bans SET ID = " + id + ", bannedname = '" + bannedName + "', uuid = '" + uuid + "', sender = '" + sender + "', reason = '" + reason + "', time = " + time + ", duration = '" + duration + "' WHERE ID = '" + id + "'");
	}
	
	/**
	 * unbans the player
	 */
	public void delete() {
		Database.execute("DELETE FROM bans WHERE ID = " + id + ";");
	}
}
