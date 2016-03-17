package net.re_renderreality.rrrp2.config;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class config {
	
	public static File file = new File("config/rrr.commands/config.conf");
	public static ConfigurationLoader<CommentedConfigurationNode> manager = HoconConfigurationLoader.builder().setFile(file).build();
	public static CommentedConfigurationNode config = manager.createEmptyNode(ConfigurationOptions.defaults());

	public static void setup() {

		try {
			
			if (!file.exists()) {
				
				file.createNewFile();
				
				config.getNode("mysql", "use").setValue(false);
				config.getNode("mysql", "host").setValue("localhost");
				config.getNode("mysql", "port").setValue(3306);
				config.getNode("mysql", "username").setValue("root");
				config.getNode("mysql", "password").setValue("password");
				config.getNode("mysql", "database").setValue("minecraft");
				
				config.getNode("version").setValue(7);
				
		        manager.save(config);
				
			}
			
	        config = manager.load();
		     
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
	public static boolean MYSQL_USE() { return config.getNode("mysql", "use").getBoolean(); }
	public static String MYSQL_HOST() { return config.getNode("mysql", "host").getString(); }
	public static int MYSQL_PORT() { return config.getNode("mysql", "port").getInt(); }
	public static String MYSQL_USERNAME() { return config.getNode("mysql", "username").getString(); }
	public static String MYSQL_PASSWORD() { return config.getNode("mysql", "password").getString(); }
	public static String MYSQL_DATABASE() { return config.getNode("mysql", "database").getString(); }
}