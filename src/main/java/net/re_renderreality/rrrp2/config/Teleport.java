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

public class Teleport implements Configurable {
	
	
	private static Teleport teleport = new Teleport();

	private Teleport()
	{
		;
	}
	
	public static Teleport getConfig()
	{
		return teleport;
	}
	
	private Path configFile = Paths.get(RRRP2.getRRRP2().getConfigDir() + "/Teleport.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;
	
	@Override
	public void setup()
	{
		if (!Files.exists(configFile))
		{
			try
			{
				File folder = new File("config/rrr.commands/");
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
		//Any generic Messages that are broadcasted server-wide go here
		get().getNode("teleport", "cooldown", "enable").setValue(true);
		get().getNode("teleport", "cooldown", "timer").setValue(30000);			
	}
	
	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}