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

	/**
	 * @param event MailSendEvent
	 * 
	 * @notes adds mail to database
	 * @TODO add way for players to recieve their mail, get the ID of the recipient
	 */
	@Listener 
	public void onMailSend(MailSendEvent event)
	{
		String recipientName = event.getRecipient();

		if (game.getServer().getPlayer(recipientName).isPresent())
		{
			//MailHandler.addMail(event.getSender().getName(), recipientName, event.getMessage());
			MailHandler.addMail(0, 0, event.getMessage());
			game.getServer()
				.getPlayer(recipientName)
				.get()
				.sendMessage(
					Text.of(TextColors.GOLD, "[Mail]: ", TextColors.GRAY, "You have received new mail from " + event.getSender().getName()
						+ " do ", TextColors.RED, "/listmail!"));
		}
		else
		{
			MailHandler.addMail(0, 0, event.getMessage());
		}
	}
}