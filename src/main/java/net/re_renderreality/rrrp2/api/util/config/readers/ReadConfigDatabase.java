package net.re_renderreality.rrrp2.api.util.config.readers;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
//import org.spongepowered.api.Sponge;
import net.re_renderreality.rrrp2.config.Config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigDatabase {
	private static Configurable mainConfig = Config.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
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
	
	public static boolean getShowBanned() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Messages", "showbanned");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setShowBanned(true);
		return true;
	}
	
	public static void setShowBanned(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"Messages", "showbanned"}, value);
	}
	
	public static boolean getTeleportCooldownEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Teleport", "cooldown", "enabled");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setTeleportCooldownEnabled(true);
		return true;
	}
	
	public static void setTeleportCooldownEnabled(boolean value) {
		Configs.setValue(mainConfig, new Object[] {"Teleport", "cooldown", "enabled"}, value);
	}
	
	public static long getTeleportCooldown()
	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("Teleport", "cooldown", "timer");
		if (configManager.getLong(node).isPresent())
			return node.getLong();
		setTeleportCooldown(10);
		return 10l;
	}
	
	public static void setTeleportCooldown(long value)
	{
		Configs.setValue(mainConfig, new Object[] { "Teleport", "cooldown", "timer" }, value);
	}


}
