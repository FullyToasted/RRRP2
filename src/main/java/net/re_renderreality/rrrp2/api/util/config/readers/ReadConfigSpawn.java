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
	
	/**
	 * @param transform x,y,z, pitch, yaw encased in  Transform World
	 * @param worldID world ID of the world
	 * @note saves spawn location to the spawn.conf file
	 */
	public static void setSpawn(String world, Transform<World> transform, String worldName) {
		Configs.getConfig(spawn).getNode(world, "spawn", "X").setValue(transform.getLocation().getX());
		Configs.getConfig(spawn).getNode(world, "spawn", "Y").setValue(transform.getLocation().getY());
		Configs.getConfig(spawn).getNode(world, "spawn", "Z").setValue(transform.getLocation().getZ());
		Configs.getConfig(spawn).getNode(world, "spawn", "world").setValue(worldName);
		Configs.getConfig(spawn).getNode(world, "spawn", "transform", "pitch").setValue(transform.getRotation().getX());
		Configs.getConfig(spawn).getNode(world, "spawn", "transform", "yaw").setValue(transform.getRotation().getY());
		Configs.getConfig(spawn).getNode(world, "spawn", "transform", "roll").setValue(transform.getRotation().getZ());

		Configs.saveConfig(spawn);
	}
	
	/**
	 * @return returns if there is a spawn location in the config file
	 */
	public static boolean isSpawnInConfig(String world) {
		return Configs.getConfig(spawn).getNode(world, "spawn", "X").getValue() != null;
	}
	
	/**
	 * @return the spawn location in the form of Transform World.
	 */
	public static Transform<World> getSpawn(String world) {
		String worldName = Configs.getConfig(spawn).getNode(world, "spawn", "world").getString();
		World worlds = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = Configs.getConfig(spawn).getNode(world, "spawn", "X").getDouble();
		double y = Configs.getConfig(spawn).getNode(world, "spawn", "Y").getDouble();
		double z = Configs.getConfig(spawn).getNode(world, "spawn", "Z").getDouble();

		if (Configs.getConfig(warps).getNode(world, "spawn", "transform", "pitch").getValue() == null) {
			if (worlds != null)
				return new Transform<>(new Location<>(worlds, x, y, z));
			else
				return null;
		} else {
			double pitch = Configs.getConfig(spawn).getNode(world, "spawn", "transform", "pitch").getDouble();
			double yaw = Configs.getConfig(spawn).getNode(world, "spawn", "transform", "yaw").getDouble();
			double roll = Configs.getConfig(spawn).getNode(world, "spawn", "transform", "roll").getDouble();

			if (world != null)
				return new Transform<>(worlds, new Vector3d(x, y, z), new Vector3d(pitch, yaw, roll));
			else
				return null;
		}
	}
}
