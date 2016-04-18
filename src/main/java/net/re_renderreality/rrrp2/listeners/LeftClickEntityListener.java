package net.re_renderreality.rrrp2.listeners;

import org.slf4j.Logger;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.flowpowered.math.vector.Vector3d;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.database.Registry;

public class LeftClickEntityListener {
	@Listener
	public void onPlayerLeftClick(InteractEntityEvent.Primary event, @First Player player )
	{
		Logger l = Registry.getLogger();
		if(RRRP2.onePunch.contains(player)) {
			Entity target = event.getTargetEntity();
			target.setVelocity(target.getVelocity().mul(25.0,5.0,25.0));
		}		
	}
}
