package net.re_renderreality.rrrp2.cmd.general;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class DepthCommand extends CommandExecutorBase
{
	/**
	 * Explanation of what command does and if complicated how to do it
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if(src instanceof Player) {
			int depth = ((Player) src).getLocation().getBlockY() - 63;
			String relative = (depth > 0) ? "above" : "below";
			depth = (depth > 0) ? depth : -depth;
			src.sendMessage(Text.of(TextColors.GOLD,"Your current depth ", TextColors.GRAY, relative, TextColors.GOLD + " sea level is: ", TextColors.GRAY, depth));
			
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Only a Player can Execute this command."));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "depth", "Depth" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Depth Command"))
			.permission("rrr.general.depth")
			.executor(this)
			.build();
	}

}
