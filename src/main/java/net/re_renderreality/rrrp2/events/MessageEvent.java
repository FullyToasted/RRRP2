package net.re_renderreality.rrrp2.events;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.impl.AbstractEvent;

public class MessageEvent extends AbstractEvent implements Cancellable
{
	private boolean cancelled = false;

	private CommandSource sender;
	private Player target;
	private int senderID;
	private int receiverID;
	private String recipientName;
	private String message;
	
	//Getters
	/**
	 * @return sender of the mail
	 */
	public CommandSource getSender() {
		return sender;
	}

	/**
	 * @return name of the recepient of the mail
	 */
	public String getRecipient() {
		return recipientName;
	}
	
	/**
	 * @return the ID of the sender
	 */
	public int getSenderID() {
		return senderID;
	}
	
	/**
	 * @return the ID of the receiver
	 */
	public int getreceiverID() {
		return receiverID;
	}
	
	/**
	 * @return returns if the mail is cancelled
	 */
	public boolean isCancelled()
	{
		return cancelled;
	}

	/**
	 * @return returns the content of the email
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * @return PlayerCore of the target
	 */
	public Player getTarget() {
		return this.target;
	}

	//Setters
	/**
	 * @param cancel set if the mail has been cancelled T or F
	 */
	public void setCancelled(boolean cancel)
	{
		cancelled = cancel;
	}

	/**
	 * @param sender sender of the mail
	 * @param recipientName
	 * @param message
	 * 
	 * @TODO: Implement IDs
	 */
	public MessageEvent(CommandSource sender, Player target, String message)
	{
		this.sender = sender;
		this.target = target;
		this.recipientName = target.getName();
		this.message = message;
	}

	/**
	 * @return returns the cause of the mail
	 */
	@Override
	public Cause getCause()
	{
		return Cause.of(NamedCause.source(this.sender));
	}
}
