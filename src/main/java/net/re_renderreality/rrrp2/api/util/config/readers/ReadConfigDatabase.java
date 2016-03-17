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
	
	//Start SQL
	public static String getMySQLPort() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "port");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLPort("8080");
		return "8080";
	}
	
	public static void setSQLPort(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "port" }, value);
	}
	
	public static String getMySQLDatabase()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "database");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLDatabase("Mutes");
		return "Mutes";
	}
	
	public static void setSQLDatabase(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "database" }, value);
	}
	
	public static String getMySQLPassword()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "password");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLPass("password");
		return "cat";
	}
	
	public static void setSQLPass(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "password" }, value);
	}
	
	public static String getMySQLUsername()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "username");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLUsername("username");
		return "username";
	}
	
	public static void setSQLUsername(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "username" }, value);
	}
	
	public static String getMySQLHost()	{
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "host");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setSQLHost("host");
		return "host";
	}
	
	public static void setSQLHost(String value)	{
		Configs.setValue(mainConfig, new Object[] { "mysql", "host" }, value);
	}
	
	public static boolean useMySQL() {
		CommentedConfigurationNode node = Configs.getConfig(mainConfig).getNode("mysql", "use");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setUseMySQL(false);
		return false;
	}
	
	public static void setUseMySQL(boolean value) {
		Configs.setValue(mainConfig, new Object[] { "mysql", "use" }, value);
	}
	//End MYSQL	
}
