package net.re_renderreality.rrrp2.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.Configurable;

/**
 * Handles the config.conf file
 */
public class MOTD implements Configurable
{
	private static MOTD motd = new MOTD();

	private MOTD()
	{
		;
	}

	public static MOTD getConfig()
	{
		return motd;
	}

	private Path configFile = Paths.get(RRRP2.getRRRP2().getConfigDir().resolve("files") + "/MOTD.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	@Override
	public void setup()
	{
		if (!Files.exists(configFile))
		{
			try
			{
				File file = new File("config/rrr.commands/files");
				if(!file.exists()) 
		    		file.mkdir();
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
		//Any Messages of the Day go here. Can add more later and have it randomly pick one
		get().getNode("motd").setValue("&6This is the default MOTD").setComment("Contains The Message of the day.");
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}