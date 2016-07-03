package net.re_renderreality.rrrp2.cmd.cheats;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

import javax.annotation.Nonnull;

public class MoreCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/more";
		description = "Gives you a full stack of the item in your hand";
		perm = "rrr.cheat.more";
		useage = "/more";
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
		if (src instanceof Player) {
			Player player = (Player) src;

			if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
				ItemStack stack = player.getItemInHand(HandTypes.MAIN_HAND).get();
				stack.setQuantity(64);
				player.setItemInHand(HandTypes.MAIN_HAND, stack);
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Stacked the item in your hand!"));
			} else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You're not holding anything to stack!"));
			}
		} else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.RED, "Error! Must be an in-game player to use /more!"));
		} 
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "more", "stack", "More", "more" };
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
				.executor(this)
				.build();
	}
}
