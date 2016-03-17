package net.re_renderreality.rrrp2.listeners;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.events.MailSendEvent;
import net.re_renderreality.rrrp2.utils.MailHandler;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class MailListener
{
	private Game game = RRRP2.getRRRP2().getGame();

	@Listener
	public void onMailSend(MailSendEvent event)
	{
		String recipientName = event.getRecipientName();

		if (game.getServer().getPlayer(recipientName).isPresent())
		{
			MailHandler.addMail(event.getSender().getName(), recipientName, event.getMessage());
			game.getServer()
				.getPlayer(recipientName)
				.get()
				.sendMessage(
					Text.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "You have received new mail from " + event.getSender().getName()
						+ " do ", TextColors.RED, "/listmail!"));
		}
		else
		{
			MailHandler.addMail(event.getSender().getName(), recipientName, event.getMessage());
		}
	}
}