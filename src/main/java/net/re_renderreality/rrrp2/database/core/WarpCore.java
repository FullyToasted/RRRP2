package net.re_renderreality.rrrp2.database.core;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.database.Database;

public class WarpCore {
	private int ID;
	private String warpname;
	private String creator;
	private String timecreated;
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	private double roll;
	
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
	public WarpCore(int ID, String warpname, String creator, String timecreated, String world, double x, double y, double z, double yaw, double pitch, double roll) {
		this.ID = ID;
		this.warpname = warpname;
		this.creator = creator;
		this.timecreated = timecreated;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}
	
	public WarpCore() {
		;
	}
	
	//Database Handlers
	/**
	 * Inserts Home into the Database
	 */
	public void insert() {
		Database.execute("INSERT INTO warps VALUES (" + ID + ", '" + warpname + "', '" + creator + "', '" + timecreated + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", " + roll + ");");
	}
	
	/**
	 * Updates home in the Database
	 */
	public void update() {
		Database.execute("UPDATE warps SET ID = " + ID + ", warpname = '" + warpname + "', creator = '" + creator + "', timecreated = '" + timecreated + "', world = '" + world + "', x = "
												+ x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + ", roll = " + roll + " WHERE ID = " + ID + ";");
	}
	
	/**
	 * Deletes Home in the Database
	 */
	public void delete() {
		Database.execute("DELETE FROM warps WHERE warpname = '" + warpname + "'");
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
	public void setWarpName(String warpname) { 
		this.warpname = warpname; 
	}

	/**
	 * @param name Username of the player who set the home
	 */
	public void setCreator(String creator) { 
		this.creator = creator; 
	}
	
	public void setTimeCreated(String time) {
		this.timecreated = time;
	}

	/**
	 * @param world world ID of the home
	 */
	public void setWorld(String world) { 
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
	
	public void setRoll(double roll) { 
		this.roll = roll; 
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
	public String getWarpName() { 
		return warpname; 
	}
	
	/**
	 * @return gets the name of the user who set the home
	 */
	public String getCreator() { 
		return creator; 
	}
	
	public String getTimeCreated() {
		return timecreated;
	}
	
	/**
	 * @return gets the world id of the home
	 */
	public String getWorld() { 
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
	
	/**
	 * @return gets the roll position of the home
	 */
	public double getRoll() { 
		return roll; 
	}
	
	public Text toText() {
		Text output = Text.of(TextColors.GRAY, "----------------------------------------------------\n",
							  TextColors.GOLD, "HomeID: ", TextColors.GRAY, ID + "\n",
							  TextColors.GOLD, "Warp Name: ", TextColors.GRAY, warpname + "\n",
							  TextColors.GOLD, "Creator's Name: ", TextColors.GRAY, creator + "\n",
							  TextColors.GOLD, "Time Created: ", TextColors.GRAY, timecreated + "\n",
							  TextColors.GOLD, "World: ", TextColors.GRAY, world + "\n",
							  TextColors.GOLD, "X: ", TextColors.GRAY, x + "\n",
							  TextColors.GOLD, "Y: ", TextColors.GRAY, y + "\n",
							  TextColors.GOLD, "Z: ", TextColors.GRAY, z + "\n",
							  TextColors.GOLD, "Pitch: ", TextColors.GRAY, pitch + "\n",
							  TextColors.GOLD, "Yaw: ", TextColors.GRAY, yaw + "\n",
							  TextColors.GOLD, "Roll: ", TextColors.GRAY, roll + "\n",
							  TextColors.GRAY, "----------------------------------------------------");
		return output;
	}
}
