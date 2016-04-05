package net.re_renderreality.rrrp2.utils;

import org.spongepowered.api.entity.living.player.Player;

public class TPInvitation {
	public boolean isTPAHere = false;
	public Player sender;
	public Player recipient;

	public TPInvitation(Player sender, Player recipient) {
		this.sender = sender;
		this.recipient = recipient;
	}

	public Player getSender() {
		return sender;
	}

	public Player getRecipient() {
		return recipient;
	}
}