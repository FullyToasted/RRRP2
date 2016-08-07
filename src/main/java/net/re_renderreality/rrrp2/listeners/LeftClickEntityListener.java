package net.re_renderreality.rrrp2.listeners;

import java.util.Random;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import com.flowpowered.math.vector.Vector3d;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.utils.Log;

public class LeftClickEntityListener {
	@Listener
	public void onPlayerLeftClick(InteractEntityEvent.Primary event, @First Player player )
	{
		Random random = new Random();
		if(RRRP2.onePunch.contains(player)) {
			Entity target = event.getTargetEntity();
			Log.debug(target.getVelocity().toString());
			Vector3d v = null;
			if(target.getVelocity().getFloorX() < 0) {
				if(target.getVelocity().getFloorY() < 0) {
					v = new Vector3d(random.nextDouble() * -50.0 - 2.5, random.nextDouble() * -50 - 2.5, random.nextDouble() * 100.0);
				} else {
					v = new Vector3d(random.nextDouble() * -50.0 - 2.5, random.nextDouble() * 50 - 2.5, random.nextDouble() * 100.0);
				}
			} else if(target.getVelocity().getFloorY() < 0) {
				v = new Vector3d(random.nextDouble() * 50.0 - 2.5, random.nextDouble() * -50 - 2.5, random.nextDouble() * 100.0);
			} else {
				v = new Vector3d(random.nextDouble() * 50.0 - 2.5, random.nextDouble() * 50 - 2.5, random.nextDouble() * 100.0);
			}
			
			v = new Vector3d(0,200,0);
				 
			Log.debug(v.toString());
			target.offer(Keys.VELOCITY, v);
		}		
	}
}
