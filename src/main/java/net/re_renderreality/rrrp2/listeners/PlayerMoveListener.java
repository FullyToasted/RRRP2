package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigTeleport;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.AFK;

public class PlayerMoveListener {
	@Listener
	public void onPlayerMove(MoveEntityEvent event, @First Player player)
	{
		PlayerCore playercore = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
		if (ReadConfigTeleport.isTeleportCooldownEnabled() && RRRP2.teleportingPlayers.contains(playercore.getID())) {
			RRRP2.teleportingPlayers.remove(playercore.getID());
			player.sendMessage(Text.of(TextColors.RED, "Teleportation canceled due to movement."));
		}

		if (RRRP2.recentlyJoined.contains(player)) {
			RRRP2.recentlyJoined.remove(player);

			if (RRRP2.afkList.containsKey(playercore.getID())) {
				RRRP2.afkList.remove(playercore.getID());
			}
		} else {
			if (RRRP2.afkList.containsKey(playercore.getID())) {
				AFK removeAFK = RRRP2.afkList.get(playercore.getID());

				if (removeAFK.getAFK()) {
					for (Player p : Registry.getGame().getServer().getOnlinePlayers()) {
						p.sendMessage(Text.of(TextColors.BLUE, player.getName(), TextColors.GOLD, " is no longer AFK."));
					}
				}

				RRRP2.afkList.remove(removeAFK);
			}

			AFK afk = new AFK(System.currentTimeMillis());
			RRRP2.afkList.put(playercore.getID(), afk);
		}

		if (!event.getFromTransform().getExtent().getUniqueId().equals(event.getToTransform().getExtent().getUniqueId())) {
			//World oldWorld = event.getFromTransform().getExtent();
			World newWorld = event.getToTransform().getExtent();

			//Utils.savePlayerInventory(player, oldWorld.getUniqueId());

			//if (!Utils.doShareInventories(oldWorld.getName(), newWorld.getName()))
			//{
			//	Utils.updatePlayerInventory(player, newWorld.getUniqueId());
			//}

			player.offer(Keys.GAME_MODE, newWorld.getProperties().getGameMode());
		}
	}
}
