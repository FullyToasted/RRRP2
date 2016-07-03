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
import net.re_renderreality.rrrp2.database.Registry;

public class FeedCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/feed";
		description = "Fills the hunger bar to full";
		perm = "rrr.cheat.feed.self";
		useage = "/feed (player)";
		notes = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPerm() {
		return this.perm;
	}
	
	public String getUseage() {
		return this.useage;
	}
	
	public String getNotes() {
		return this.notes;
	}
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> p = ctx.<Player> getOne("player");

		if (src instanceof Player) {
			Player player = (Player) src;

			//checks to feed others
			if (player.hasPermission("rrr.cheat.feed.others") && p.isPresent()) {
				Player recipient = p.get();
				Optional<FoodData> foodData = recipient.getOrCreate(FoodData.class);

				if (foodData.isPresent()) {
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					//feeds player
					recipient.offer(newData);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Fed " + recipient.getName()));
					recipient.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "You have been fed by " + player.getName()));
				}
				else {
					src.sendMessage(Text.of(TextColors.RED, "Error! Player " + player.getName() + " does not have FoodData!"));
				}
			} else if (p.isPresent()) {
				player.sendMessage(Text.of(TextColors.RED, "Error! You do not have permission to feed other players!"));
				
			} else {
				Optional<FoodData> foodData = player.getOrCreate(FoodData.class);

				if (foodData.isPresent()) {
					FoodData newData = foodData.get().set(Keys.FOOD_LEVEL, 20);
					//feeds player
					player.offer(newData);
					player.sendMessage(Text.of(TextColors.GOLD, "You have been fed."));
				} else {
					src.sendMessage(Text.of(TextColors.RED, "Error! Player " + player.getName() + " does not have FoodData!"));
				}
			}
		} else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.RED, "ERROR! Must be an in-game player to use /feed!"));
		}
		

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "feed", "Feed" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Cheater;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this).build();
	}
}
