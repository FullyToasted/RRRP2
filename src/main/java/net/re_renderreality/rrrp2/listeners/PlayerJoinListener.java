package net.re_renderreality.rrrp2.listeners;

import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigMesseges;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;
import net.re_renderreality.rrrp2.database.OnlinePlayers;


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
		String uuid = player.getUniqueId().toString();
		int id = Database.getIDFromDatabase(uuid);
		if ( id == 0) {
			int newID = Database.findNextID();
			
			PlayerCore thePlayer = new PlayerCore(newID,player.getUniqueId().toString(),player.getName(),"", "default", 5.0, false, false, false, false, 0.0, "LastLocation", "LastDeath", "FirstSeen", "LastSeen" );
			Database.addUUID(uuid, newID);
			thePlayer.insert();

			if (ReadConfigMesseges.getFirstJoinMsgEnabled()); {
				String connectionMessage = ReadConfigMesseges.getFirstJoinMsg();
				connectionMessage = connectionMessage.replaceAll("%player", player.getName());
				Text newMessage = TextSerializers.formattingCode('&').deserialize(connectionMessage);
				event.setMessage(newMessage);
				
			}
			if (ReadConfigMesseges.getUniqueMsgShow()) {
				String uniquePlayerCount = ReadConfigMesseges.getUniqueMsg();
				uniquePlayerCount = uniquePlayerCount.replaceAll("%players", String.valueOf(newID));
				Text newMessage = TextSerializers.formattingCode('&').deserialize(uniquePlayerCount);
				Utilities.broadcastMessage(newMessage);
			}
		} else {
		
			String connectionMessage = ReadConfigMesseges.getJoinMsg();
			Database.addUUID(uuid, id);
			if (ReadConfigMesseges.getJoinMsgEnabled())
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
		OnlinePlayers OP = RRRP2.getRRRP2().getOnlinePlayer();
		PlayerCore players = Database.getPlayerCore(id);
		Logger l = RRRP2.getRRRP2().getLogger();
		if(OP == null)
			l.info("null");
		OP.addPlayer(players);
	}
}
