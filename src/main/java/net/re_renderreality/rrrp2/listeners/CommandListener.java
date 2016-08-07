package net.re_renderreality.rrrp2.listeners;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

import net.re_renderreality.rrrp2.utils.Log;

public class CommandListener {
	@Listener
	public void onCommandSent(SendCommandEvent event, @First CommandSource src)
	{
		Log.command("[" + src.getName() + "]" + " executed command " + event.getCommand() + " " + event.getArguments());
	}
}
