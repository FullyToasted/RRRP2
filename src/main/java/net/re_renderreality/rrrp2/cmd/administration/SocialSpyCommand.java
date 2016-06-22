package net.re_renderreality.rrrp2.cmd.administration;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
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
import net.re_renderreality.rrrp2.database.Registry;

public class SocialSpyCommand extends CommandExecutorBase
{
	/**
	 * Command to Reply to a recent message
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Logger l = Registry.getLogger();
		if(src instanceof Player) {
			Player player = (Player) src;
			l.info(RRRP2.socialSpy.toString());
			if(!RRRP2.socialSpy.contains(player)) {
				RRRP2.socialSpy.add(player);
				src.sendMessage(Text.of(TextColors.GOLD, "Social Spy: ", TextColors.GREEN, "ENABLED"));
			} else {
				RRRP2.socialSpy.remove(player);
				src.sendMessage(Text.of(TextColors.GOLD, "Social Spy: ", TextColors.RED, "DISABLED"));
			}
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Only a Player can use this command!"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "SocialSpy", "socialspy"};
		
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		
		return CommandSpec.builder()
			.description(Text.of("A message to another player"))
			.permission("rrr.admin.socialspy")
			.executor(this)
			.build();
	}
}
