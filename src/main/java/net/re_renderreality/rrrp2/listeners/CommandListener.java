package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

import net.re_renderreality.rrrp2.RRRP2;

public class CommandListener {
	@Listener
	public void onCommandSent(SendCommandEvent event, @First CommandSource src)
	{
		RRRP2.getRRRP2().getLogger().info("[" + src.getName() + "]" + " executed command " + event.getCommand() + " " + event.getArguments());
	}
}
