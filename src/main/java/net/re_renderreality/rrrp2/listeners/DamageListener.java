package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class DamageListener {

	@Listener
	public void onPlayerDamage(DamageEntityEvent event) {
		if(event.getTargetEntity().getType() == EntityTypes.PLAYER) {
			Entity play = event.getTargetEntity();
			int id = Database.getID(play.getUniqueId().toString());
			PlayerCore player = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
			if(player.getGod()) {
				event.setCancelled(true);
			}
		}
	}
}
