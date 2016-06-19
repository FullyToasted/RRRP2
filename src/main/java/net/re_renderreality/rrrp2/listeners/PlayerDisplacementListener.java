package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class PlayerDisplacementListener {
	@Listener
	public void onPlayerMove(MoveEntityEvent event, @First Player player )
	{
		PlayerCore playercore = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
		
		if (RRRP2.frozenPlayers.contains(playercore.getID())) {
			double x,z;
			Transform<World> before = event.getFromTransform();
			x = Math.floor(before.getLocation().getBlockX());
			z = Math.floor(before.getLocation().getBlockZ());
			double X,Z;
			Transform<World> after = event.getToTransform();
			X = Math.floor(after.getLocation().getBlockX());
			Z = Math.floor(after.getLocation().getBlockZ());
			if(!(X == x && z == Z)) {
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot move while frozen."));
				event.setCancelled(true);
				return;
			}
		}
	}
}
