package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigMesseges;


public class PlayerJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event) {
	
		Player player = event.getTargetEntity();
		String connectionMessage = ReadConfigMesseges.getJoinMsg();
		
		if (connectionMessage != null && !connectionMessage.equals(""))
		{
			connectionMessage = connectionMessage.replaceAll("%player", player.getName());
			Text newMessage = TextSerializers.formattingCode('&').deserialize(connectionMessage);
			event.setMessage(newMessage);
		}
	
		if (RRRP2.afkList.containsKey(player.getUniqueId()))
		{
			RRRP2.afkList.remove(player.getUniqueId());
		}
	}
}
