package net.re_renderreality.rrrp2.cmd.cheats;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;

public class HealCommand extends CommandExecutorBase
{
	/**
	 * Heals other players or self.
	 * 
	 * TODO: Set up a permission to heal self
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> p = ctx.<Player> getOne("player");

		if (src instanceof Player)
		{
			Player player = (Player) src;

			if (player.hasPermission("rrr.cheat.heal.others") && p.isPresent())
			{
				Player recipient = p.get();
				recipient.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				recipient.sendMessage(Text.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've been healed by " + player.getName()));
				src.sendMessage(Text.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've healed " + recipient.getName()));
			}
			else if (p.isPresent())
			{
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to heal other players!"));
			}
			else
			{
				player.offer(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
				src.sendMessage(Text.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "You've been healed."));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /heal!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /heal!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "heal", "MotherSaveMe"};
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Heal Command"))
				.permission("rrr.cheat.heal.self")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this).build();
	}
}
