package net.re_renderreality.rrrp2.cmd.home;

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
import net.re_renderreality.rrrp2.database.core.HomeCore;

public class DeleteHomeCommand extends CommandExecutorBase {
	//gets a HomeCore and deletes it
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		Optional<String> homeName = ctx.<String> getOne("homename");
		Player source = (Player) src;
		
		if(src instanceof Player) {
			if(homeName.isPresent()) {
				String homename = homeName.get();
				if(Database.getHomeExist(source, homename)) {
					HomeCore homecore = Database.getHome(source, homename);
					homecore.delete();
					source.sendMessage(Text.of(TextColors.GOLD, "Home deleted successfully!"));
					return CommandResult.empty();
				} else {
					source.sendMessage(Text.of(TextColors.RED, "Home not found!"));
					return CommandResult.empty();
				}
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Need to specify home name"));
				return CommandResult.empty();
			}
		} else {
			source.sendMessage(Text.of(TextColors.RED, "If you want to delete a home please use /managehomes"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "DeleteHome", "Deletehome", "deleteHome", "deletehome", "rmhome", "RMhome", "rmHome", "RMHome", "delhome", "DelHome", "Delhome", "delHome" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of("Removes Players Home"))
				.permission("rrr.general.home")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("homename")))))
				.executor(this)
				.build();
	}
}
