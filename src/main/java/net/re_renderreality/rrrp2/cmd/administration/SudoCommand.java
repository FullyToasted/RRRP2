package net.re_renderreality.rrrp2.cmd.administration;

import javax.annotation.Nonnull;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class SudoCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Game game = Registry.getGame();
		Player p = ctx.<Player> getOne("player").get();
		String command = ctx.<String> getOne("command").get();
		if (src instanceof Player) {
			Player player = (Player) src;
			CommandManager cmdService = game.getCommandManager();
			if (!(p.hasPermission("rrr.admin.sudoexempt"))) {
				cmdService.process(p, command);
				player.sendMessage(Text.of(TextColors.GOLD, "Forcing " + p.getName() + " to run /" + command));
				p.sendMessage(Text.of(TextColors.GRAY, player.getName(), TextColors.GOLD, " has forced you to run /" + command));
			} else {
				player.sendMessage(Text.of(TextColors.RED, "ERROR! This player is exempt from sudo!"));
				return CommandResult.empty();
			}
		} else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.RED, "Error! Must be an in-game player to use /sudo!"));
			return CommandResult.empty();
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "sudo" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
				.builder()
				.description(Text.of("Sudo Command"))
				.permission("rrr.admin.sudo")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
							GenericArguments.remainingJoinedStrings(Text.of("command"))))
				.executor(this)
				.build();
	}
}
