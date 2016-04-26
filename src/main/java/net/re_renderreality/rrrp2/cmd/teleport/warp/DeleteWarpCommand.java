package net.re_renderreality.rrrp2.cmd.teleport.warp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.WarpCore;

public class DeleteWarpCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> warpName = ctx.<String> getOne("warpname");
		Player source = (Player) src;
		
		if(src instanceof Player) {
			if(warpName.isPresent()) {
				String warpname = warpName.get();
				if(Database.getWarpExist(warpname)) {
					WarpCore warpcore = Database.getWarp(warpname);
					warpcore.delete();
					source.sendMessage(Text.of(TextColors.GOLD, "Warp deleted successfully!"));
					return CommandResult.empty();
				} else {
					source.sendMessage(Text.of(TextColors.RED, "Warp not found!"));
					return CommandResult.empty();
				}
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Need to specify warp name"));
				return CommandResult.empty();
			}
		} else {
			source.sendMessage(Text.of(TextColors.RED, "If you want to delete a warp please use /managehomes"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "DeleteWarp", "Deletewarp", "deleteWarp", "deletewarp", "rmwarp", "RMwarp", "rmWarp", "RMWarp", "delwarp", "DelWarp", "Delwarp", "delWarp" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of("Removes Players Home"))
				.permission("rrr.admin.warp.delete")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("warpname")))))
				.executor(this).build();
	}
}
