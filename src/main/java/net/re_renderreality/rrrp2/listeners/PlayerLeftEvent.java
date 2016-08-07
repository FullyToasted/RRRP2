package net.re_renderreality.rrrp2.listeners;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.*;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class PlayerLeftEvent
{
	/**
	 * @param event client disconnection event
	 * 
	 * TODO: update playerCore, update Database, set lastSeen location
	 */
	@Listener
	public void onPlayerDisconnect(ClientConnectionEvent.Disconnect event)
	{
		Player player = event.getTargetEntity();
		String disconnectMessage = ReadConfigMesseges.getLeaveMsg();
		String lastloc = Utilities.convertLocation(player);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String todaysDate = dateFormat.format(cal.getTime());

		if (disconnectMessage != null && !disconnectMessage.equals(""))
		{
			disconnectMessage = disconnectMessage.replaceAll("%player", player.getName());
			Text newMessage = TextSerializers.formattingCode('&').deserialize(disconnectMessage);
			event.setMessage(newMessage);
		}
		PlayerCore playa = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
		
		if(playa.getInvisible()) {
			if(player.get(Keys.INVISIBLE).isPresent()) {
				player.offer(Keys.INVISIBLE, false);
				RRRP2.invisiblePlayers.remove(player);
				playa.setInvisibleUpdate(!playa.getInvisible());
			}
		}
		
		if(!(playa == null) && !(playa.getBanned())) {
			playa.setLastlocationUpdate(lastloc);
			playa.setLastseenUpdate(todaysDate);
			playa.setFlyUpdate(false);
			Registry.getOnlinePlayers().removePlayer(playa);
		}
		
		if (RRRP2.afkList.containsKey(playa.getID()))
		{
			RRRP2.afkList.remove(playa.getID());
		}
		//Utils.savePlayerInventory(player, player.getWorld().getUniqueId());
	}
}