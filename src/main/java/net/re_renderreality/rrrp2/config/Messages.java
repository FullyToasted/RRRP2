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

public class Messages implements Configurable {
	
	
	private static Messages messages = new Messages();

	private Messages()
	{
		;
	}
	
	public static Messages getConfig()
	{
		return messages;
	}
	
	private Path configFile = Paths.get(RRRP2.getRRRP2().getConfigDir().resolve("files") + "/Messages.conf");
	private ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
	private CommentedConfigurationNode configNode;
	
	@Override
	public void setup()
	{
		if (!Files.exists(configFile))
		{
			try
			{
				File folder = new File("config/rrr.commands/files");
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
		get().getNode("events", "join", "enable").setValue(true);
		get().getNode("events", "join", "message").setValue("&e%player &7has joined.");
		
		get().getNode("events", "leave", "enable").setValue(true);
		get().getNode("events", "leave", "message").setValue("&e%player &7has left.");
			
		get().getNode("events", "firstjoin", "enable").setValue(true);
		get().getNode("events", "firstjoin", "message").setValue("&e%player &7has joined for the first time!");
		get().getNode("events", "firstjoin", "uniqueplayers", "show").setValue(true);
		get().getNode("events", "firstjoin", "uniqueplayers", "message").setValue("&e%players &7unique players already joined.");
		
		get().getNode("version").setValue(1);			
	}
	
	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}
	
