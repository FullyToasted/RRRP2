package net.re_renderreality.rrrp2.cmd.cheats;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class JockeyCommand extends CommandExecutorBase
{
	/**
	 * Allows players to jockey other entities. This just adds a user to the active jockeyer list
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if( src instanceof Player) {
			Player source = (Player) src;
			if(RRRP2.jockey.contains(source)) {
				src.sendMessage(Text.of(TextColors.GOLD, "Jockey Mode Disabled!"));
				RRRP2.jockey.remove(source);
			} else {
				src.sendMessage(Text.of(TextColors.GOLD, "Jockey Mode Enabled!"));
				RRRP2.jockey.add(source);
			}
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "jockey", "Jockey"};
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Jockey Command"))
				.permission("rrr.cheat.jockey")
				.executor(this)
				.build();
	}
}