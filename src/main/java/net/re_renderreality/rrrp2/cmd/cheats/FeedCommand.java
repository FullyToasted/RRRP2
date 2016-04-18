package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class FeedCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> p = ctx.<Player> getOne("player");

		if (src instanceof Player) {
			Player player = (Player) src;

			if (player.hasPermission("rrr.cheat.feed.others") && p.isPresent()) {
				Player recipient = p.get();
				Optional<FoodData> foodData = recipient.getOrCreate(FoodData.class);

				if (foodData.isPresent()) {
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					recipient.offer(newData);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Fed " + recipient.getName()));
					recipient.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You have been fed by " + player.getName()));
				}
				else {
					src.sendMessage(Text.of(TextColors.RED, "Error! Player " + player.getName() + " does not have FoodData!"));
				}
			}
			else if (p.isPresent()) {
				
				player.sendMessage(Text.of(TextColors.RED, "Error! You do not have permission to feed other players!"));
				
			} else {
				Optional<FoodData> foodData = player.getOrCreate(FoodData.class);

				if (foodData.isPresent()) {
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					player.offer(newData);
					player.sendMessage(Text.of(TextColors.GOLD, "You have been fed."));
				}
				else {
					src.sendMessage(Text.of(TextColors.RED, "Error! Player " + player.getName() + " does not have FoodData!"));
				}
			}
		}
		else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.RED, "ERROR! Must be an in-game player to use /feed!"));
		}
		

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "feed" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Feed Command"))
				.permission("rrr.cheat.feed.self")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this).build();
	}
}
