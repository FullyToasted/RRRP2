package net.re_renderreality.rrrp2.database.core;

import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Mutes;

public class MuteCore {

	private int ID;
	private String uuid;
	private double duration;
	private String reason;
	
	/**
	 * @param ID ID number of the muted user
	 * @param uuid uuid of the muted user
	 * @param duration length of the mute
	 * @param reason why was he muted
	 */
	public MuteCore(int ID, String uuid, double duration, String reason) {
		this.ID = ID;
		this.uuid = uuid;
		this.duration = duration;
		this.reason = reason;
	}
	//Setters
	/**
	 * @param ID ID number of the user who is muted
	 */
	public void setID(int ID) { 
		this.ID = ID; 
	}
	
	/**
	 * @param uuid uuid of the muted user
	 */
	public void setUUID(String uuid) { 
		this.uuid = uuid; 
	}
	
	/**
	 * @param duration How long the user is muted in Minutes
	 */
	public void setDuration(double duration) { 
		this.duration = duration; 
	}
	
	/**
	 * @param reason Why the player was muted
	 */
	public void setReason(String reason) { 
		this.reason = reason; 
	}
	
	//Getter
	/**
	 * @return ID of the muted player
	 */
	public int getID() { 
		return ID; 
	}
	
	/**
	 * @return uuid of the muted player
	 */
	public String getUUID() { 
		return uuid; 
	}
	
	/**
	 * @return duration of the mute
	 */
	public double getDuration() { 
		return duration; 
	}
	
	/**
	 * @return Reason the player was muted
	 */
	public String getReason() { 
		return reason; 
	}
	
	//Database Handlers
	/**
	 * inserts mute into database
	 */
	public void insert() {
		Database.queue("INSERT INTO mutes VALUES ('" + uuid + "', " + duration + ", '" + reason + "')");
		Mutes.addMute(ID, this);
	}
	
	/**
	 * updates the mute in the database
	 */
	public void update() {
		Database.queue("UPDATE mutes SET duration = " + duration + ", reason = '" + reason + "' WHERE ID = '" + ID + "'");
		Mutes.removeMute(ID);
		Mutes.addMute(ID, this);
	}
	
	/**
	 * deletes the mute in the database
	 */
	public void delete() {
		Database.queue("DELETE FROM mutes WHERE ID = '" + ID + "'");
		Mutes.removeMute(ID);
	}
}
