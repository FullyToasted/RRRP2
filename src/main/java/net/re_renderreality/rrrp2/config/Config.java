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
		get().getNode("mysql").setComment("MySQL Options for EssentialCmds.");
		get().getNode("mysql", "use").setValue(false).setComment("Enables/Disables MySQL usage for EssentialCmds.");
		get().getNode("mysql", "port").setValue("8080").setComment("Port of MySQL Database.");
		get().getNode("mysql", "host").setValue("localhost").setComment("Address of MySQL Database.");
		get().getNode("mysql", "database").setValue("Minecraft").setComment("Name of MySQL Database.");
		get().getNode("mysql", "username").setValue("root").setComment("Username for MySQL Database.");
		get().getNode("mysql", "password").setValue("pass").setComment("Password for MySQL Database.");
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}