package net.re_renderreality.rrrp2.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Announcements implements Configurable
{
	private static Announcements announcements = new Announcements();

	private Announcements()
	{
		;
	}

	public static Announcements getConfig()
	{
		return announcements;
	}

	private Path configFile = Paths.get(RRRP2.getRRRP2().getConfigDir().resolve("chat") + "/announcements.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;

	@Override
	public void setup()
	{
		if (!Files.exists(configFile))
		{
			try
			{
				File folder = new File("config/rrr.commands/chat");
				if(!folder.exists()) 
					folder.mkdir();
				
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
		//Spawn Information goes here. Will be auto-populated to begin with but then can be overridden
		get().getNode("Announcement Count").setValue(0).setComment("Keep this current with the number of rules");
		get().getNode("Header").setValue("&6Announcements For Re-RenderReality").setComment("Contains announcement header.");
		get().getNode("Footer").setValue("Footer(Change this in production envionment)").setComment("Contains announcement footer");
		get().getNode("Announcements").setComment("Rules to be added manually or by /addannouncement");
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}
