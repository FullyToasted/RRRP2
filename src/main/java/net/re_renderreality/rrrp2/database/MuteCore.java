package net.re_renderreality.rrrp2.database;

public class MuteCore {

	private String uuid;
	private double duration;
	private String reason;
	
	public MuteCore(String uuid, double duration, String reason) {
		this.uuid = uuid;
		this.duration = duration;
		this.reason = reason;
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setDuration(double duration) { this.duration = duration; }
	public void setReason(String reason) { this.reason = reason; }
	
	public String getUUID() { return uuid; }
	public double getDuration() { return duration; }
	public String getReason() { return reason; }
	
	public void insert() {
		Database.queue("INSERT INTO mutes VALUES ('" + uuid + "', " + duration + ", '" + reason + "')");
		Database.addMute(uuid, this);
	}
	
	public void update() {
		Database.queue("UPDATE mutes SET duration = " + duration + ", reason = '" + reason + "' WHERE uuid = '" + uuid + "'");
		Database.removeMute(uuid);
		Database.addMute(uuid, this);
	}
	
	public void delete() {
		Database.queue("DELETE FROM mutes WHERE uuid = '" + uuid + "'");
		Database.removeMute(uuid);
	}
}
