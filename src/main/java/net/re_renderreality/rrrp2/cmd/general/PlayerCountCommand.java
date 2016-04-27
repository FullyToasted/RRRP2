package net.re_renderreality.rrrp2.cmd.general;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;

public class PlayerCountCommand extends CommandExecutorBase {
	/**
	 * Gives a count of how many players are currently online
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		int id = Database.findNextID("players") - 1;
		src.sendMessage(Text.of("There have been " + id + " unique players on this server"));
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "PlayerCount", "Unique", "playercount" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Lists the Amount of Unique Players on the Server"))
			.permission("rrr.general.playercount")
			.executor(this)
			.build();
	}
}
