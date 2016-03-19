package net.re_renderreality.rrrp2.database;

public class BanCore {
	private int ID;
	private String uuid;
	private String sender;
	private String reason;
	private double time;
	private double duration;
	
	public BanCore(int ID, String uuid, String sender, String reason, double time, double duration) {
		this.ID = ID;
		this.sender = sender;
		this.reason = reason;
		this.time = time;
		this.duration = duration;
	}
	
	public void setID(int ID) { this.ID = ID; }
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setSender(String sender) { this.sender = sender; }
	public void setReason(String reason) { this.reason = reason; }
	public void setTime(double time) { this.time = time; }
	public void setDuration(double duration) { this.duration = duration; }
	
	public int getID() { return ID; }
	public String getUUID() { return uuid; }
	public String getSender() { return sender; }
	public String getReason() { return reason; }
	public double getTime() { return time; }
	public double getDuration() { return duration; }
	
	public void insert() {
		Database.addBan(ID, this);
	}
	
	public void update() {
		Database.queue("UPDATE bans SET sender = '" + sender + "', reason = '" + reason + "', time = " + time + ", duration = " + duration + " WHERE uuid = '" + uuid + "'");
		Database.removeBan(ID);
		Database.addBan(ID, this);
	}
	
	public void delete() {
		Database.queue("DELETE FROM bans WHERE ID = '" + ID + "'");
		Database.removeBan(ID);
	}
	
}
