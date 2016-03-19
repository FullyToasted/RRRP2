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
		return CommandSpec.builder()
				.description(Text.of("Gives detailed information about the requested player"))
				.permission("rrrp2.admin.whois")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this).build();
	}
}