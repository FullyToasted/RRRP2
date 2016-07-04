package net.re_renderreality.rrrp2.listeners;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigChat;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class ChatListener {
	
	private static boolean lastmessage = false;

	@Listener(order = Order.PRE)
	public void onMessage(MessageChannelEvent.Chat event)
	{
		Player player = event.getCause().first(Player.class).get();
		int id = Database.getIDFromDatabase(player.getUniqueId().toString());
		PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
		//String message = event.getMessage().toPlain();
		 
		StringBuilder original = new StringBuilder(event.getMessage().toPlain());
	
		Subject subject = player.getContainingCollection().get(player.getIdentifier());
		String prefix = "";
		String suffix = "";
		TextColor nameColor = TextColors.WHITE;
	
		prefix = subject.getOption("prefix").orElse("");
		suffix = subject.getOption("suffix").orElse("");
		nameColor = Sponge.getRegistry().getType(TextColor.class, subject.getOption("namecolor").orElse("")).orElse(TextColors.WHITE);
	
		String restOfOriginal = original.substring(original.indexOf(">") + 1, original.length());
	
		original = original.replace(0, 1, ("<" + prefix));
		String prefixInOriginal = original.substring(0, prefix.length() + 1);
	
		original = original.replace(original.indexOf(player.getName()) + player.getName().length(), original.indexOf(player.getName()) + player.getName().length() + 1, suffix + ">");
		String suffixInOriginal = original.substring(original.indexOf(player.getName()) + player.getName().length(), original.indexOf(restOfOriginal));
		
		String playerName = playercore.getName();
		if(!(playercore.getNick().equals(""))) {
			String nick = playercore.getNick();
			original = original.replace(original.indexOf(player.getName()) - 1, original.indexOf(player.getName()) + player.getName().length(), nick);
			playerName = original.substring(prefixInOriginal.length() - 1, original.indexOf(nick) + nick.length());
		}
	
		prefixInOriginal = prefixInOriginal.replaceFirst("<", ReadConfigChat.getFirstCharactar());
	
		if (suffixInOriginal.length() != 0)
			suffixInOriginal = suffixInOriginal.substring(0, suffixInOriginal.length() - 1) + ReadConfigChat.getLastCharactar();
	
		Logger l = Registry.getLogger();
		l.info(lastmessage + " " + restOfOriginal);
		if (!player.hasPermission("rrr.color.chat.use")) {
			if(lastmessage) {
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(playerName)).color(nameColor).build())
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.RESET))
					.append(Text.of(restOfOriginal))
					.onClick(event.getMessage().getClickAction().orElse(null))
					.style(event.getMessage().getStyle())
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			} else {
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(playerName)).color(nameColor).build())
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.GRAY))
					.append(Text.builder().append(Text.of(restOfOriginal)).color(TextColors.GRAY).build())
					.onClick(event.getMessage().getClickAction().orElse(null))
					.style(event.getMessage().getStyle())
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			}
		} else {
			if(lastmessage) {
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(playerName)).color(nameColor).build())
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.RESET))
					.append(TextSerializers.formattingCode('&').deserialize(restOfOriginal))
					.onClick(event.getMessage().getClickAction().orElse(null))
					.style(event.getMessage().getStyle())
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			} else {
				event.setMessage(Text.builder()
					.append(TextSerializers.formattingCode('&').deserialize(prefixInOriginal))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(playerName)).color(nameColor).build())
					.append(TextSerializers.formattingCode('&').deserialize(suffixInOriginal))
					.append(Text.of(TextColors.GRAY))
					.append(Text.builder().append(TextSerializers.formattingCode('&').deserialize(restOfOriginal)).color(TextColors.GRAY).build())
					.onClick(event.getMessage().getClickAction().orElse(null))
					.style(event.getMessage().getStyle())
					.onHover(event.getMessage().getHoverAction().orElse(null))
					.build());
			}
		}
		lastmessage = !lastmessage;
	}
}
