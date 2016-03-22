package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;

import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class DeathListener {
	
	@Listener
	public void onPlayerDamage(DestructEntityEvent.Death event) {
		if(event.getTargetEntity().getType() == EntityTypes.PLAYER) {
			Living player = event.getTargetEntity();
			Player playa = (Player) player;
			String uuid = player.getUniqueId().toString();
			int id = Database.getIDFromDatabase(uuid);
			PlayerCore players = Database.getPlayerCore(id);
			String lastloc = Utilities.convertLocation(playa);
			players.setLastdeathUpdate(lastloc);
			players.setLastlocation(lastloc);
		}
	}
}
