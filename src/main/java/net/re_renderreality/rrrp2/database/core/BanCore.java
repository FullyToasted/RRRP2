package net.re_renderreality.rrrp2.database.core;

import net.re_renderreality.rrrp2.database.Bans;
import net.re_renderreality.rrrp2.database.Database;

public class BanCore {
	private int ID;
	private String uuid;
	private String sender;
	private String reason;
	private double time;
	private double duration;
	
	/**
	 * @param ID ID of the player that got banned
	 * @param uuid uuid of the player that got banned
	 * @param sender who banned the player
	 * @param reason why they were banned
	 * @param time time they were banned 
	 * @param duration how long they were banned for
	 */
	public BanCore(int ID, String uuid, String sender, String reason, double time, double duration) {
		this.ID = ID;
		this.sender = sender;
		this.reason = reason;
		this.time = time;
		this.duration = duration;
	}
	
	//Setters
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setID(int ID) { 
		this.ID = ID; 
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
	public void setTime(double time) { 
		this.time = time; 
	}
	
	/**
	 * @param ID playerID of the player who was banned
	 */
	public void setDuration(double duration) { 
		this.duration = duration; 
	}
	
	//Getters
	
	/**
	 * @return the ID number of the player that got banned
	 */
	public int getID() { 
		return ID; 
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
	public double getTime() { 
		return time; 
	}
	
	/**
	 * @return the Duration of the ban
	 */
	public double getDuration() { 
		return duration; 
	}
	
	//Database Handlers
	/**
	 * inserts ban into the database
	 */
	public void insert() {
		Bans.addBan(ID, this);
	}
	
	/**
	 * updates the ban in the database
	 */
	public void update() {
		Database.queue("UPDATE bans SET sender = '" + sender + "', reason = '" + reason + "', time = " + time + ", duration = " + duration + " WHERE uuid = '" + uuid + "'");
		Bans.removeBan(ID);
		Bans.addBan(ID, this);
	}
	
	/**
	 * unbans the player
	 */
	public void delete() {
		Database.queue("DELETE FROM bans WHERE ID = '" + ID + "'");
		Bans.removeBan(ID);
	}
}
