package net.re_renderreality.rrrp2.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.utils.Log;

/**
 * Handles the config.conf file
 */
public class Config implements Configurable
{
	private static Config config = new Config();

	private Config()
	{
		;
	}

	public static Config getConfig()
	{
		return config;
	}

	private Path configFile = Paths.get(RRRP2.getRRRP2().getConfigDir() + "/config.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	@Override
	public void setup()
	{
		Log.test("FILE EXSIST? " + !Files.exists(configFile));
		if (!Files.exists(configFile))
		{
			try
			{
				Files.createFile(configFile);
				load();
				populate();
				save();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			load();
		}
	}

	@Override
	public void load()
	{
		try
		{
			configNode = configLoader.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void save()
	{
		try
		{
			configLoader.save(configNode);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void populate()
	{
		//Populates with General COnfig information. Anything specialized will be given dedicated .conf file
		get().getNode("Debug", "Logging Level").setValue("INFO").setComment("Level of info to spit out to the console Options:ALL, CONFIG, WARN, SEVERE, DEBUG, OFF, INFO");
		get().getNode("Debug", "Log Commands").setValue(true).setComment("Log commands");
		
		get().getNode("mysql").setComment("MySQL Options for RRRP2.");
		get().getNode("mysql", "use").setValue(false).setComment("Enables/Disables MySQL usage for EssentialCmds.");
		get().getNode("mysql", "port").setValue("8080").setComment("Port of MySQL Database.");
		get().getNode("mysql", "host").setValue("localhost").setComment("Address of MySQL Database.");
		get().getNode("mysql", "database").setValue("Minecraft").setComment("Name of MySQL Database.");
		get().getNode("mysql", "username").setValue("root").setComment("Username for MySQL Database.");
		get().getNode("mysql", "password").setValue("pass").setComment("Password for MySQL Database.");
		
		get().getNode("Messages", "showbanned").setValue(true).setComment("When Someone is banned everyone see it");
		
		get().getNode("Teleport", "cooldown", "enabled").setValue(true).setComment("Sets if TP cooldown is enabled");
		get().getNode("Teleport", "cooldown", "timer").setValue(10).setComment("Sets cooldown timer length");
		
		get().getNode("AFK", "kick", "enable").setValue(false);
		get().getNode("AFK", "kick", "time").setValue(3000000);
		get().getNode("AFK", "timer", "enable").setValue(true);
		get().getNode("AFK", "timer", "time").setValue(300000);
		
		get().getNode("Enchantments", "Allow Unsafe Enchantments").setValue(true);
		
		get().getNode("world", "weather", "locked").setValue(null);
		
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}