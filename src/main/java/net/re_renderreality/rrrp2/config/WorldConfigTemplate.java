package net.re_renderreality.rrrp2.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.Configurable;

/**
 * Handles the spawn.conf file
 */
public class WorldConfigTemplate implements Configurable
{
	public WorldConfigTemplate(World world)
	{
		this.world = world;
	}

	private World world;
	private Path configFile;
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;
	private CommentedConfigurationNode configNode;

	@Override
	public void setup()
	{
		 configFile = Paths.get(RRRP2.getRRRP2().getConfigDir().resolve("worlds") + "/" + world.getName() + ".conf");
		 configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
		if (!Files.exists(configFile))
		{
			try
			{
				File folder = new File("config/rrr.commands/worlds");
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
		String theWorldName = "World_Info";
		//Spawn Information goes here. Will be auto-populated to begin with but then can be overridden
		get().getNode("_Default World").setValue(false);
		get().getNode(theWorldName).setComment("World Information");
		get().getNode(theWorldName, "World_Name").setValue(theWorldName);
		get().getNode(theWorldName, "Dimension").setValue(world.getDimension().getType().getName());
		get().getNode(theWorldName, "World_UUID").setValue(world.getUniqueId().toString());
		get().getNode(theWorldName, "Spawn", "X").setValue(world.getSpawnLocation().getX());
		get().getNode(theWorldName, "Spawn", "Y").setValue(world.getSpawnLocation().getY());
		get().getNode(theWorldName, "Spawn", "Z").setValue(world.getSpawnLocation().getZ());
		get().getNode(theWorldName, "Spawn", "Transform", "Pitch").setValue(0);
		get().getNode(theWorldName, "Spawn", "Transform", "Yaw").setValue(0);
		get().getNode(theWorldName, "Spawn", "Transform", "Roll").setValue(0);
		//get().getNode("World", "Warps").setComment("Contains spawn data.");
		//get().getNode("World", "World Name").setComment("Contains spawn data.");
	}

	@Override
	public CommentedConfigurationNode get()
	{
		return configNode;
	}
}