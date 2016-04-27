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

public class OnePunchCommand extends CommandExecutorBase
{
	/**
	 * Adds player to one punch user list
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if( src instanceof Player) {
			Player source = (Player) src;
			if(RRRP2.onePunch.contains(source)) {
				src.sendMessage(Text.of(TextColors.GOLD, "One Punch Mode Disabled!"));
				RRRP2.onePunch.remove(source);
			} else {
				src.sendMessage(Text.of(TextColors.GOLD, "One Punch Mode Enabled! (Note: need to rapid double punch)"));
				RRRP2.onePunch.add(source);
			}
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "OnePunch", "onepunch", "Onepunch", "onePunch"};
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("One Punch Command"))
				.permission("rrr.cheat.onepunch")
				.executor(this)
				.build();
	}
}
