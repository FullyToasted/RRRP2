package net.re_renderreality.rrrp2.utils;

import java.util.HashMap;

import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.config.WorldConfigTemplate;
import net.re_renderreality.rrrp2.database.Registry;

public class WorldHandler {
	
	private static HashMap<String, WorldConfigTemplate> allWorlds = new HashMap<String, WorldConfigTemplate>();
	
	public static HashMap<String, WorldConfigTemplate> setup() {
		Log.debug(Registry.getServer().getWorlds().toString());
		for(World w : Registry.getServer().getWorlds()) {
			WorldConfigTemplate config = new WorldConfigTemplate(w);
			config.setup();
			allWorlds.put(w.getName(), config);
		}
		
		return allWorlds;
	}
	
	public static HashMap<String, WorldConfigTemplate> getConfigs() {
		return allWorlds;
	}
}
