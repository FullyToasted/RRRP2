package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigMesseges;
import net.re_renderreality.rrrp2.database.Players;
import net.re_renderreality.rrrp2.database.core.PlayerCore;


public class PlayerJoinListener
{
	/**
	 * @param event client connection event
	 * 
	 * TODO: Show Mail Notification, Show Player Join message, player first join message, load Playercore for new users, find unclaimed ID, check Username for changes, find id for returning users
	 */
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event) {
	
		Player player = event.getTargetEntity();
		String connectionMessage = ReadConfigMesseges.getJoinMsg();
		PlayerCore thePlayer = new PlayerCore(0,"UUID","Poesidon2012","", "default", 0.0, false, false, true, false, 0.0, "", "", "", "" );
		Players.addPlayer(0, thePlayer);
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
