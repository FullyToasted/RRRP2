package net.re_renderreality.rrrp2.cmd;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.annotation.Nonnull;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * @author Avarai/Poesidon2012
 * @note Plans: Add nickname support and formerly known as support.
 */
public class WhoisCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		PlayerRegistry register = RRRP2.plugin.getPlayerRegistry();
		Optional<Player> player = ctx.<Player> getOne("player");
		String name = player.get().getName();
		Text status = Text.of("");
		Text lastSeen = Text.of("");
		
		if (register.containsPlayer(name)) {
			if (Utilities.getPlayer(name).isPresent()) {
				status = Text.builder(Utilities.boolToString(true)).color(TextColors.GREEN).build();
				String time = LocalTime.now().toString();
				lastSeen = Text.builder(LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.'))).color(TextColors.BLUE).build();
			}
			else {
				status = Text.builder(Utilities.boolToString(false)).color(TextColors.RED).build();
				String time = register.getTime(register.getUuid(name));
				if (!time.isEmpty())
					lastSeen = Text.builder(time).color(TextColors.BLUE).build();
				else
					lastSeen = Text.builder("Not Seen").color(TextColors.BLUE).build();
			}
			src.sendMessage(Text.joinWith(Text.of(""), Text.of(name + " -- Online: "), status, Text.of(" Last seen: ", lastSeen)));
		}
		else {
			src.sendMessage(Text.of("That player has not been seen on the server."));
			return CommandResult.empty();
		}
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "whois", "whoIs", "WhoIs", "WhoIS" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("Gives detailed information about the requested player")).permission("rrrp2.admin.whois")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this).build();
	}
}