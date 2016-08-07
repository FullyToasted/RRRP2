package net.re_renderreality.rrrp2.api.util.config.readers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.RRRP2.DebugLevel;
import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
//import org.spongepowered.api.Sponge;
import net.re_renderreality.rrrp2.config.Config;
import net.re_renderreality.rrrp2.utils.Log;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfig {
	private static Configurable mainConfig = Config.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	public static RRRP2.DebugLevel getDebugLevel() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Debug", "Logging Level");
		if (configManager.getString(node).isPresent()) {
			String debug = configManager.getString(node).get();
			if(debug.equals("ALL")) {
				return DebugLevel.ALL;
			} else if(debug.equals("CONFIG")) {
				return DebugLevel.CONFIG;
			} else if(debug.equals("DEBUG")) {
				return DebugLevel.DEBUG;
			} else if(debug.equals("INFO")) {
				return DebugLevel.INFO;
			} else if(debug.equals("OFF")) {
				return DebugLevel.OFF;
			} else if(debug.equals("SEVERE")) {
				return DebugLevel.SEVERE;
			} else if(debug.equals("WARNING")) {
				return DebugLevel.WARNING;
			} 
		}
		Log.error("Error getting DEBUG level from Config. Resetting to default");
		setSQLPort("INFO");
		return DebugLevel.INFO;
	}
	
	/**
	 * @param value value to set Debug Level to. Must be one of the 6 possible options(ALL, CONFIG, INFO, OFF, SEVERE, WARNING)
	 */
	public static void setDebugLevel(String value)	{
		if(value.equals("ALL") || value.equals("CONFIG") || value.equals("INFO") || value.equals("OFF") || value.equals("SEVERE") || value.equals("WARNING")) {
			Configs.setValue(mainConfig, new Object[] {"Debug", "Logging Level" }, value);
		} else {
			Configs.setValue(mainConfig, new Object[] {"Debug", "Logging Level" }, "INFO");
		}
	}
	
	/**
	 * @return the choice of whether or not to Log Commands. default is true
	 */
	public static boolean getLogCommands() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Debug", "Log Commands");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setLogCommands(true);
		return false;
	}
	
	/**
	 * @param value choice of using MySQL True or False
	 */
	public static void setLogCommands(boolean value) {
		Configs.setValue(mainConfig, new Object[] { "Debug", "Log Commands" }, value);
	}

	/**
	 * @return either the default port or the port set in the config
	 */
	public static String getMySQLPort() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "port");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLPort("8080");
		return "8080";
	}
	
	/**
	 * @param value value to set the SQL port to. Must be in "number" format
	 */
	public static void setSQLPort(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "port" }, value);
	}
	
	/**
	 * @return either the default database name or the database set in the config
	 */
	public static String getMySQLDatabase()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "database");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLDatabase("Minecraft");
		return "Minecraft";
	}
	
	/**
	 * @param value value to set the Database name too.
	 */
	public static void setSQLDatabase(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "database" }, value);
	}
	
	/**
	 * @return either the default password or the password set in the config
	 */
	public static String getMySQLPassword()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "password");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLPass("password");
		return "password";
	}
	
	/**
	 * @param value value to set the MySQL password to.
	 */
	public static void setSQLPass(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "password" }, value);
	}
	
	/**
	 * @return either the default username or the username set in the config
	 */
	public static String getMySQLUsername()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "username");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLUsername("username");
		return "username";
	}
	
	/**
	 * @param value value to set the MySQL username to.
	 */
	public static void setSQLUsername(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "username" }, value);
	}
	
	/**
	 * @return either the default Host or the Host set in the config
	 */
	public static String getMySQLHost()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "host");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLHost("host");
		return "host";
	}
	
	/**
	 * @param value value to set the MySQL username to.
	 */
	public static void setSQLHost(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "host" }, value);
	}
	
	/**
	 * @return the choice of whether or not to use MySQL. default is False
	 */
	public static boolean useMySQL() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "use");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setUseMySQL(false);
		return false;
	}
	
	/**
	 * @param value choice of using MySQL True or False
	 */
	public static void setUseMySQL(boolean value) {
		Configs.setValue(mainConfig, new Object[] { "mysql", "use" }, value);
	}
	//End MYSQL	
	
	 /**
	  * @return If ban is broadcasted 
	  */
	public static boolean getShowBanned() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Messages", "showbanned");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setShowBanned(true);
		return true;
	}
	
	/**
	 * @param value set if bans are broadcasted 
	 */
	public static void setShowBanned(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"Messages", "showbanned"}, value);
	}
	
	/**
	 * @return if teleport cooldown is enabled
	 */
	public static boolean getTeleportCooldownEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Teleport", "cooldown", "enabled");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setTeleportCooldownEnabled(true);
		return true;
	}
	
	/**
	 * @param value sets if TP cooldown is enabled
	 */
	public static void setTeleportCooldownEnabled(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"Teleport", "cooldown", "enabled"}, value);
	}
	
	/**
	 * @return gets teleport cooldown
	 */
	public static long getTeleportCooldown()
	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Teleport", "cooldown", "timer");
		if (configManager.getLong(node).isPresent())
			return node.getLong();
		setTeleportCooldown(10);
		return 10l;
	}
	
	/**
	 * @param value set teleport cooldown
	 */
	public static void setTeleportCooldown(long value)
	{
		Configs.setValue(mainConfig, new Object[] { "Teleport", "cooldown", "timer" }, value);
	}
	
	/**
	 * @return returns if AFK kick is enabled
	 */
	public static boolean getAFKKickEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("AFK", "kick", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setTeleportCooldownEnabled(true);
		return true;
	}
	
	/**
	 * @param value set if AFK kick is enabled
	 */
	public static void setAFKKickEnabled(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"AFK", "kick", "enable"}, value);
	}
	
	/**
	 * @return get AFK timer is enabled
	 */
	public static boolean getAFKTimerEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("AFK", "timer", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setTeleportCooldownEnabled(true);
		return true;
	}
	
	/**
	 * @param value set if AFK timer is enabled
	 */
	public static void setAFKTimerEnabled(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"AFK", "timer", "enable"}, value);
	}
	
	/**
	 * @return get AFK kick timer
	 */
	public static double getAFKKickTime()
	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("AFK", "kick", "time");
		if (configManager.getLong(node).isPresent())
			return node.getDouble();
		setTeleportCooldown(3000000);
		return 3000000;
	}
	
	/**
	 * @param value set afk kick time
	 */
	public static void setAFKKickTime(double value)
	{
		Configs.setValue(mainConfig, new Object[] { "Teleport", "cooldown", "timer" }, value);
	}
	
	/**
	 * @return get AFK time
	 */
	public static double getAFKTime()
	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("AFK", "timer", "time");
		if (configManager.getLong(node).isPresent())
			return node.getDouble();
		setTeleportCooldown(300000);
		return 300000;
	}
	
	/**
	 * @param value set AFK time
	 */
	public static void setAFKTime(double value)
	{
		Configs.setValue(mainConfig, new Object[] {"AFK", "timer", "time"}, value);
	}

	/**
	 * @return unsafe enchantment enabled
	 */
	public static boolean getUnsafeEnchantmentStatus() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Enchantments", "Allow Unsafe Enchantments");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setTeleportCooldownEnabled(true);
		return true;
	}
	
	/**
	 * @param value set unsafe enchantment enabled
	 */
	public static void setUnsafeEnchantmentStatus(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"Enchantments", "Allow Unsafe Enchantments"}, value);
	}
	
	/** 
	 * @return get worlds where the weather is locked
	 */
	public static List<String> getLockedWeatherWorlds() {
		CommentedConfigurationNode valueNode = Configs.getConfig(mainConfig).getNode("world", "weather", "locked");

		if (valueNode.getValue() != null) {
			List<String> worlds = Arrays.asList(valueNode.getString().split("\\s*,\\s*"));
			return worlds;
		} else {
			return Lists.newArrayList();
		}
	}

	/**
	 * @param worldUuid add a world to locked list
	 */
	public static void addLockedWeatherWorld(UUID worldUuid) {
		CommentedConfigurationNode valueNode = Configs.getConfig(mainConfig).getNode("world", "weather", "locked");

		if (valueNode.getValue() != null) {
			Configs.setValue(mainConfig, valueNode.getPath(), valueNode.getString() + ", " + worldUuid.toString());
		} else {
			Configs.setValue(mainConfig, valueNode.getPath(), worldUuid.toString());
		}
	}

	/**
	 * @param worldUuid remove a world from locked list
	 */
	public static void removeLockedWeatherWorld(UUID worldUuid) {
		CommentedConfigurationNode valueNode = Configs.getConfig(mainConfig).getNode("world", "weather", "locked");

		if (valueNode.getValue() != null) {
			if (valueNode.getString().contains(worldUuid.toString() + ", ")) {
				Configs.setValue(mainConfig, valueNode.getPath(), valueNode.getString().replace(worldUuid.toString() + ", ", ""));
			} else {
				Configs.setValue(mainConfig, valueNode.getPath(), valueNode.getString().replace(worldUuid.toString(), ""));
			}
		}
	}
}
