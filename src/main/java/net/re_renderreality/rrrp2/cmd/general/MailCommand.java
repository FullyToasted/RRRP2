package net.re_renderreality.rrrp2.cmd.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.MailCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class MailCommand extends CommandExecutorBase
{
	/**
	 * Explanation of what command does and if complicated how to do it
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> player = ctx.<String> getOne("Player");
		Optional<String> message = ctx.<String> getOne("Message");
		//Database Layout 
		if(src instanceof Player) {
			if(player.isPresent() && message.isPresent()) {
				int targetID = Database.getPlayerIDfromUsername(player.get());
				if(!(targetID == 0)) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String todaysDate = dateFormat.format(cal.getTime());
					
					PlayerCore target = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(targetID);
					Player p = (Player) src;
					int cPlayerID = Database.getIDFromDatabase(p.getUniqueId().toString());
					PlayerCore cPlayer = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(cPlayerID);
					MailCore mail = new MailCore(target.getID(), target.getName(), Database.findNextID("mail"), cPlayer.getID(), cPlayer.getName(), todaysDate, message.get(), false);
					mail.insert();
					
					src.sendMessage(Text.of(TextColors.GOLD, "Message successfully sent to: ", TextColors.GRAY, target.getName()));
					return CommandResult.success();
				} else {
					src.sendMessage(Text.of(TextColors.RED, " Name not found in Player database"));
					return CommandResult.empty();
				}
			}
			src.sendMessage(Text.of(TextColors.RED, "Error in Mail Format. Correct Useage: ", TextColors.GRAY, "/mail <player> <message>"));
		} else if(src instanceof ConsoleSource) {
			if(player.isPresent() && message.isPresent()) {
				int targetID = Database.getPlayerIDfromUsername(player.get());
				if(!(targetID == 0)) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String todaysDate = dateFormat.format(cal.getTime());
					
					PlayerCore target = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(targetID);
					MailCore mail = new MailCore(target.getID(), target.getName(), Database.findNextID("mail"), 0, "Console", todaysDate, message.get(), false);
					mail.insert();
					
					src.sendMessage(Text.of(TextColors.GOLD, "Message successfully sent to: ", TextColors.GRAY, target.getName()));
					return CommandResult.success();
				} else {
					src.sendMessage(Text.of(TextColors.RED, " Name not found in Player database"));
					return CommandResult.empty();
				}
			} else {
				src.sendMessage(Text.of(TextColors.RED, "Error in Mail Format. Correct Useage: ", TextColors.GRAY, "/mail <player> <message>"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "If you throw this error you are trly trying this plugin"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "Mail", "mail"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Send Mail to another player"))
			.permission("rrr.general.mail.send")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("Player"))),
						GenericArguments.remainingJoinedStrings(Text.of("Message")))
			.executor(this)
			.build();
	}
}