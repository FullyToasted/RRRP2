package net.re_renderreality.rrrp2.api.util.config.readers;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.config.WorldConfigTemplate;
import net.re_renderreality.rrrp2.utils.WorldHandler;

public class ReadConfigWorld {
	private static Configurable currentConfig;
	private static ConfigManager configManager = new ConfigManager();
	
	//START CONFIG HANDLING
	/**
	 * @param template Template of the current world being accessed
	 */
	private static void setCurrentConfig(WorldConfigTemplate template) {
		currentConfig = template;
	}
	
	/**
	 * @param world World to set the current template to
	 */
	public static void setWorld(String world) {
		setCurrentConfig(WorldHandler.getConfigs().get(world));
	}
	//END CONFIG GANDLING
	
	
	//START SPAWN HANDLER
	/**
	 * @param transform x,y,z, pitch, yaw encased in  Transform World
	 * @param worldID world ID of the world
	 * @note saves spawn location to the spawn.conf file
	 */
	public static void setSpawn(Transform<World> transform) {
		String worldName = Configs.getConfig(currentConfig).getNode("World_Info", "World_Name").getString();
		Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "X").setValue(transform.getLocation().getX());
		Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Y").setValue(transform.getLocation().getY());
		Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Z").setValue(transform.getLocation().getZ());
		Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "Pitch").setValue(transform.getRotation().getX());
		Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "Yaw").setValue(transform.getRotation().getY());
		Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "Roll").setValue(transform.getRotation().getZ());

		Configs.saveConfig(currentConfig);
	}
	
	/**
	 * @return the spawn location in the form of Transform World.
	 */
	public static Transform<World> getSpawn() {
		String worldName = Configs.getConfig(currentConfig).getNode("World_Info", "World_Name").getString();
		World worlds = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "X").getDouble();
		double y = Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Y").getDouble();
		double z = Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Z").getDouble();

		if (Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "pitch").getValue() == null) {
			if (worlds != null)
				return new Transform<>(new Location<>(worlds, x, y, z));
			else
				return null;
		} else {
			double pitch = Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "Pitch").getDouble();
			double yaw = Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "Yaw").getDouble();
			double roll = Configs.getConfig(currentConfig).getNode(worldName, "Spawn", "Transform", "Roll").getDouble();

			if (worldName != null)
				return new Transform<>(worlds, new Vector3d(x, y, z), new Vector3d(pitch, yaw, roll));
			else
				return null;
		}
	}
	//END SPAWN HANDLER
}
