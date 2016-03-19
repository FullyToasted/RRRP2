package net.re_renderreality.rrrp2.database.core;

import net.re_renderreality.rrrp2.database.Database;

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
	
	/**
	 * @param ID ID of the player that set the home
	 * @param uuid uuid of the player that set the home
	 * @param name Username of the player that set the home
	 * @param world world the home was set
	 * @param x the x value of the home
	 * @param y the y value of the home
	 * @param z the z value of the home
	 * @param yaw the yaw value of the home
	 * @param pitch the pitch value of the home
	 */
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
	
	//Database Handlers
	/**
	 * Inserts Home into the Database
	 */
	public void insert() {
		Database.queue("INSERT INTO homes VALUES ('" + uuid + "', '" + name + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ")");
	}
	
	/**
	 * Updates home in the Database
	 */
	public void update() {
		Database.queue("UPDATE homes SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + " WHERE uuid = '" + uuid + "' AND name = '" + name + "'");
	}
	
	/**
	 * Deletes Home in the Database
	 */
	public void delete() {
		Database.queue("DELETE FROM homes WHERE ID = '" + ID + "' AND name = '" + name + "'");
	}
	
	//Setters
	/**
	 * @param ID ID of the player who set the home
	 */
	public void setID(int ID) {
		this.ID = ID; 
	}
	
	/**
	 * @param uuid uuid of the player who set the home
	 */
	public void setUUID(String uuid) { 
		this.uuid = uuid; 
	}

	/**
	 * @param name Username of the player who set the home
	 */
	public void setName(String name) { 
		this.name = name; 
	}

	/**
	 * @param world world ID of the home
	 */
	public void setWorld(int world) { 
		this.world = world; 
	}

	/**
	 * @param x X position of the home
	 */
	public void setX(double x) { 
		this.x = x; 
	}
	
	/**
	 * @param y Y position of the home
	 */
	public void setY(double y) { 
		this.y = y; 
	}

	/**
	 * @param z Z position of the player who set the home
	 */
	public void setZ(double z) { 
		this.z = z; 
	}

	/**
	 * @param yaw Yaw position of the player who set the home
	 */
	public void setYaw(double yaw) { 
		this.yaw = yaw; 
	}

	/**
	 * @param pitch Pitch of the player who set the home
	 */
	public void setPitch(double pitch) { 
		this.pitch = pitch; 
	}
	
	//Getters
	/**
	 * @return the id of the Player who set the home
	 */
	public int getID() { 
		return ID; 
	}
	
	/**
	 * @return the uuid of the player who set the home
	 */
	public String getUUID() { 
		return uuid; 
	}
	
	/**
	 * @return gets the name of the user who set the home
	 */
	public String getName() { 
		return name; 
	}
	
	/**
	 * @return gets the world id of the home
	 */
	public int getWorld() { 
		return world; 
	}
	
	/**
	 * @return gets the x position of the home
	 */
	public double getX() { 
		return x; 
	}
	
	/**
	 * @return gets the y position of the home
	 */
	public double getY() { 
		return y; 
	}
	
	/**
	 * @return gets the z position of the home
	 */
	public double getZ() { 
		return z; 
	}
	
	/**
	 * @return gets the yaw position of the home
	 */
	public double getYaw() { 
		return yaw; 
	}
	
	/**
	 * @return gets the pitch position of the home
	 */
	public double getPitch() { 
		return pitch; 
	}
}
