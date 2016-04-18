package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.world.ChangeWorldWeatherEvent;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;

public class WeatherListener {
	@Listener
	public void onWeatherChanged(ChangeWorldWeatherEvent event)
	{
		if (ReadConfig.getLockedWeatherWorlds().contains(event.getTargetWorld().getUniqueId().toString()))
		{
			event.setCancelled(true);
		}
	}
}
