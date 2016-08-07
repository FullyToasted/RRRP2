package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class DamageListener {

	@Listener
	public void onPlayerDamage(DamageEntityEvent event) {
		if(event.getTargetEntity().getType() == EntityTypes.PLAYER) {
			Player play = (Player) event.getTargetEntity();
			PlayerCore player = Registry.getOnlinePlayers().getPlayerCorefromUsername(play.getName());
			if(player.getGod()) {
				event.setCancelled(true);
			}
		}
	}
}
