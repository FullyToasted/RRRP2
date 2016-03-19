package net.re_renderreality.rrrp2.database;

public class MuteCore {

	private int ID;
	private String uuid;
	private double duration;
	private String reason;
	
	public MuteCore(int ID, String uuid, double duration, String reason) {
		this.ID = ID;
		this.uuid = uuid;
		this.duration = duration;
		this.reason = reason;
	}
	
	public void setID(int ID) { this.ID = ID; }
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setDuration(double duration) { this.duration = duration; }
	public void setReason(String reason) { this.reason = reason; }
	
	public int getID() { return ID; }
	public String getUUID() { return uuid; }
	public double getDuration() { return duration; }
	public String getReason() { return reason; }
	
	public void insert() {
		Database.queue("INSERT INTO mutes VALUES ('" + uuid + "', " + duration + ", '" + reason + "')");
		Database.addMute(ID, this);
	}
	
	public void update() {
		Database.queue("UPDATE mutes SET duration = " + duration + ", reason = '" + reason + "' WHERE ID = '" + ID + "'");
		Database.removeMute(ID);
		Database.addMute(ID, this);
	}
	
	public void delete() {
		Database.queue("DELETE FROM mutes WHERE ID = '" + ID + "'");
		Database.removeMute(ID);
	}
}
