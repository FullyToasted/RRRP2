package net.re_renderreality.rrrp2.listeners;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.PluginInfo;
import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.events.TPAcceptEvent;
import net.re_renderreality.rrrp2.events.TPEvent;
import net.re_renderreality.rrrp2.events.TPHereAcceptEvent;
import net.re_renderreality.rrrp2.events.TPHereEvent;
import net.re_renderreality.rrrp2.utils.TPInvitation;
import net.re_renderreality.rrrp2.utils.Utilities;

public class TPListener {

	private Game game = RRRP2.getRRRP2().getGame();

	@Listener
	public void tpEventHandler(TPEvent event) {
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Text.of(TextColors.BLUE, "TPA Request From: ", TextColors.GOLD, senderName + ".", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final TPInvitation invite = new TPInvitation(event.getSender(), event.getRecipient());
		RRRP2.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		Scheduler scheduler = game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			if (RRRP2.pendingInvites.contains(invite)) {
				RRRP2.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("RRRP2 - Remove Pending Invite").submit(game.getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
	}

	@Listener
	public void tpAcceptEventHandler(TPAcceptEvent event) {
		String senderName = event.getSender().getName();
		Player p = event.getRecipient();
		PlayerCore player = Registry.getOnlinePlayers().getPlayerCorefromUsername(p.getName());
		
		if (ReadConfig.getTeleportCooldownEnabled() && !event.getRecipient().hasPermission("rrr.general.teleport.cooldownoverride")) {
			RRRP2.teleportingPlayers.add(player.getID());
			event.getRecipient().sendMessage(Text.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request. Please wait " + ReadConfig.getTeleportCooldown() + " seconds."));
			
			Sponge.getScheduler().createTaskBuilder().execute(() -> {
				if (RRRP2.teleportingPlayers.contains(event.getRecipient().getUniqueId())) {
					player.setLastlocationUpdate(Utilities.convertLocation(event.getRecipient()));
					event.getRecipient().setLocation(event.getSender().getLocation());
					RRRP2.teleportingPlayers.remove(event.getRecipient().getUniqueId());
				}
			}).delay(ReadConfig.getTeleportCooldown(), TimeUnit.SECONDS).name("EssentialCmds - TPA Timer").submit(game.getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
		}
		else {
			event.getRecipient().sendMessage(Text.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request."));
			player.setLastlocationUpdate(Utilities.convertLocation(event.getRecipient()));
			event.getRecipient().setLocation(event.getSender().getLocation());
		}
	}

	@Listener
	public void tpHereAcceptEventHandler(TPHereAcceptEvent event)
	{
		String recipientName = event.getRecipient().getName();
		Player p = event.getRecipient();
		PlayerCore player = Registry.getOnlinePlayers().getPlayerCorefromUsername(p.getName());

		if (ReadConfig.getTeleportCooldownEnabled() && !event.getSender().hasPermission("rrr.general.teleport.cooldownoverride")) {
			RRRP2.teleportingPlayers.add(player.getID());
			event.getSender().sendMessage(Text.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request. Please wait " + ReadConfig.getTeleportCooldown() + " seconds."));
			
			Sponge.getScheduler().createTaskBuilder().execute(() -> {
				if (RRRP2.teleportingPlayers.contains(event.getSender().getUniqueId())) {
					player.setLastlocationUpdate(Utilities.convertLocation(event.getRecipient()));
					event.getSender().setLocation(event.getRecipient().getLocation());
					RRRP2.teleportingPlayers.remove(event.getSender().getUniqueId());
				}
			}).delay(ReadConfig.getTeleportCooldown(), TimeUnit.SECONDS).name("EssentialCmds - TPA Timer").submit(game.getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
		}
		else {
			event.getSender().sendMessage(Text.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request."));
			player.setLastlocationUpdate(Utilities.convertLocation(event.getRecipient()));
			event.getSender().setLocation(event.getRecipient().getLocation());
		}
	}

	@Listener
	public void tpHereEventHandler(TPHereEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Text.of(TextColors.BLUE, senderName, TextColors.GOLD, " has requested for you to teleport to them.", TextColors.RED, " You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final TPInvitation invite = new TPInvitation(event.getSender(), event.getRecipient());
		invite.isTPAHere = true;
		RRRP2.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		Scheduler scheduler = game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			if (RRRP2.pendingInvites.contains(invite)) {
				RRRP2.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite").submit(game.getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
	}
}

