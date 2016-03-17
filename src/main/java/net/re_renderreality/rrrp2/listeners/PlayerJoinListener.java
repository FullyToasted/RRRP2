package net.re_renderreality.rrrp2.listeners;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.*;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.utils.Mail;
import net.re_renderreality.rrrp2.utils.MailHandler;

import org.spongepowered.api.data.manipulator.mutable.entity.JoinData;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.World;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PlayerJoinListener
{
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join event)
	{
		Player player = event.getTargetEntity();

		if (player.get(JoinData.class).isPresent() && player.getJoinData().firstPlayed().get().equals(player.getJoinData().lastPlayed().get()))
		{
			Transform<World> spawn = ReadConfigSpawn.getSpawn();

			if (spawn != null)
			{
				if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId()))
				{
					player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
					player.setTransform(spawn);
				}
				else
				{
					player.setTransform(spawn);
				}
			}

			Text firstJoinMsg = TextSerializers.formattingCode('&').deserialize(ReadConfigMesseges.getFirstJoinMsg().replaceAll("@p", player.getName()));
			MessageChannel.TO_ALL.send(firstJoinMsg);
		}

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Database.setLastTimePlayerJoined(player, format.format(cal.getTime()));
		player.sendMessage(TextSerializers.formattingCode('&').deserialize(ReadConfigMesseges.getJoinMsg()));

		ArrayList<Mail> newMail = (ArrayList<Mail>) MailHandler.getMail().stream().filter(mail -> mail.getRecipientName().equals(player.getName())).collect(Collectors.toList());

		if (newMail.size() > 0)
		{
			player.sendMessage(Text.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "While you were away, you received new mail to view it do ", TextColors.RED, "/listmail"));
		}

		RRRP2.recentlyJoined.add(event.getTargetEntity());

		// Remove previous AFK, so player does not join as AFK.
		if (RRRP2.afkList.containsKey(player.getUniqueId()))
		{
			RRRP2.afkList.remove(player.getUniqueId());
		}

		String loginMessage = ReadConfigMOTD.getMOTD();

		if (loginMessage != null && !loginMessage.equals(""))
		{
			loginMessage = loginMessage.replaceAll("@p", player.getName());
			Text newMessage = TextSerializers.formattingCode('&').deserialize(loginMessage);
			event.setMessage(newMessage);
		}

		//Utils.savePlayerInventory(player, player.getWorld().getUniqueId());
	}
}
