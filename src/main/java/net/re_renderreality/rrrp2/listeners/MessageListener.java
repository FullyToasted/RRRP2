package net.re_renderreality.rrrp2.listeners;

import org.slf4j.Logger;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.events.MessageEvent;

public class MessageListener {
	@Listener
	public void onMessage(MessageEvent event, @First Player player )
	{
		Logger l = Registry.getLogger();
		Player target = event.getTarget();
		CommandSource source = event.getSender();
		String message = event.getMessage();
		
		target.sendMessage(Text.of(TextColors.GRAY, source.getName() + " whsipers to you: " + message));
		source.sendMessage(Text.of(TextColors.GRAY, "You whisper to " + target.getName() + message));

		RRRP2.recentlyMessaged.put(target.getName(), source);
		
		l.info(target.getName() + " whsipers to " + target.getName() + ": " + message);
		for(Player p : RRRP2.socialSpy) {
			p.sendMessage(Text.of(TextColors.GRAY, source.getName() + " whsipers to " + target.getName() + ": " + message));
		}
	}
}
