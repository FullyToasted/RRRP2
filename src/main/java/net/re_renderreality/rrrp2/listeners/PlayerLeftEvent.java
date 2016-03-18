package net.re_renderreality.rrrp2.listeners;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.*;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerLeftEvent
{
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event)
	{
		Player player = event.getTargetEntity();
		String disconnectMessage = ReadConfigMesseges.getLeaveMsg();

		if (disconnectMessage != null && !disconnectMessage.equals(""))
		{
			disconnectMessage = disconnectMessage.replaceAll("%player", player.getName());
			Text newMessage = TextSerializers.formattingCode('&').deserialize(disconnectMessage);
			event.setMessage(newMessage);
		}

		if (RRRP2.afkList.containsKey(player.getUniqueId()))
		{
			RRRP2.afkList.remove(player.getUniqueId());
		}

		//Utils.savePlayerInventory(player, player.getWorld().getUniqueId());
	}
}