package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import net.re_renderreality.rrrp2.RRRP2;

public class RightClickEntityListener {
	@Listener
	public void onPlayerRightClick(InteractEntityEvent.Secondary event, @First Player player )
	{
		if(RRRP2.jockey.contains(player)) {
			Entity target = event.getTargetEntity();
			target.setVehicle(player);
		}		
	}
}