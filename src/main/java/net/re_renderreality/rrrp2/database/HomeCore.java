package net.re_renderreality.rrrp2.database;

public class HomeCore {
	private int ID;
	private String uuid;
	private String name;
	private int world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	
	public HomeCore(int ID, String uuid, String name, int world, double x, double y, double z, double yaw, double pitch) {
		this.ID = ID;
		this.uuid = uuid;
		this.name = name;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public void insert() {
		Database.queue("INSERT INTO homes VALUES ('" + uuid + "', '" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ")");
	}
	
	public void update() {
		Database.queue("UPDATE homes SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + " WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	public void delete() {
		Database.queue("DELETE FROM homes WHERE ID = '" + ID + "' AND name = '" + name + "'");
	}
	
	public void setID(int ID) {this.ID = ID; }
	public void setUUID(String uuid) { this.uuid = uuid; }
	public void setName(String name) { this.name = name; }
	public void setWorld(int world) { this.world = world; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	public void setYaw(double yaw) { this.yaw = yaw; }
	public void setPitch(double pitch) { this.pitch = pitch; }
	
	public int getID(int ID) { return ID; }
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public int getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getYaw() { return yaw; }
	public double getPitch() { return pitch; }
}
