package net.re_renderreality.rrrp2.api.util.config.readers;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

//import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;

import net.re_renderreality.rrrp2.config.Spawn;
import net.re_renderreality.rrrp2.config.Warps;

public class ReadConfigSpawn {
	private static Configurable spawn = Spawn.getConfig();
	private static Configurable warps = Warps.getConfig();
	
	//private static ConfigManager configManager = new ConfigManager();
	
	public static void setSpawn(Transform<World> transform, String worldName) {
		{
			Configs.getConfig(spawn).getNode("spawn", "X").setValue(transform.getLocation().getX());
			Configs.getConfig(spawn).getNode("spawn", "Y").setValue(transform.getLocation().getY());
			Configs.getConfig(spawn).getNode("spawn", "Z").setValue(transform.getLocation().getZ());
			Configs.getConfig(spawn).getNode("spawn", "world").setValue(worldName);
			Configs.getConfig(spawn).getNode("spawn", "transform", "pitch").setValue(transform.getRotation().getX());
			Configs.getConfig(spawn).getNode("spawn", "transform", "yaw").setValue(transform.getRotation().getY());
			Configs.getConfig(spawn).getNode("spawn", "transform", "roll").setValue(transform.getRotation().getZ());

			Configs.saveConfig(spawn);
		}
	}
	
	public static boolean isSpawnInConfig()
	{
		return Configs.getConfig(spawn).getNode("spawn", "X").getValue() != null;
	}

	public static Transform<World> getSpawn()
	{
		String worldName = Configs.getConfig(spawn).getNode("spawn", "world").getString();
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = Configs.getConfig(spawn).getNode("spawn", "X").getDouble();
		double y = Configs.getConfig(spawn).getNode("spawn", "Y").getDouble();
		double z = Configs.getConfig(spawn).getNode("spawn", "Z").getDouble();

		if (Configs.getConfig(warps).getNode("spawn", "transform", "pitch").getValue() == null)
		{
			if (world != null)
				return new Transform<>(new Location<>(world, x, y, z));
			else
				return null;
		}
		else
		{
			double pitch = Configs.getConfig(spawn).getNode("spawn", "transform", "pitch").getDouble();
			double yaw = Configs.getConfig(spawn).getNode("spawn", "transform", "yaw").getDouble();
			double roll = Configs.getConfig(spawn).getNode("spawn", "transform", "roll").getDouble();

			if (world != null)
				return new Transform<>(world, new Vector3d(x, y, z), new Vector3d(pitch, yaw, roll));
			else
				return null;
		}
	}
}
