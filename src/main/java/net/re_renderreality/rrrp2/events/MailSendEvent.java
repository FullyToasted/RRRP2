package net.re_renderreality.rrrp2.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.impl.AbstractEvent;

public class MailSendEvent extends AbstractEvent implements Cancellable
{
	private boolean cancelled = false;

	private Player sender;
	private String recipientName;
	private String message;

	public Player getSender()
	{
		return sender;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public boolean isCancelled()
	{
		return cancelled;
	}

	public String getMessage()
	{
		return message;
	}

	public void setCancelled(boolean cancel)
	{
		cancelled = cancel;
	}

	public MailSendEvent(Player sender, String recipientName, String message)
	{
		this.sender = sender;
		this.recipientName = recipientName;
		this.message = message;
	}

	@Override
	public Cause getCause()
	{
		return Cause.of(NamedCause.source(this.sender));
	}
}
