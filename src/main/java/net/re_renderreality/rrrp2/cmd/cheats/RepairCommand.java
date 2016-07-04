package net.re_renderreality.rrrp2.cmd.cheats;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

import javax.annotation.Nonnull;

public class RepairCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/repair";
		description = "Repairs the item in your hand";
		perm = "rrr.cheat.repair";
		useage = "/repair";
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
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Game game = Registry.getGame();
		ItemStack.Builder itemStackBuilder = game.getRegistry().createBuilder(ItemStack.Builder.class);

		if (src instanceof Player) {
			Player player = (Player) src;

			//repairs by creating a new item with same specifications
			if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()) {
				ItemStack itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get();
				ItemType itemType = itemInHand.getItem();
				int quantity = itemInHand.getQuantity();
				ItemStack newItemStack = itemStackBuilder.quantity(quantity).itemType(itemType).build();
				player.setItemInHand(HandTypes.MAIN_HAND, null);
				player.setItemInHand(HandTypes.MAIN_HAND, newItemStack);
				player.sendMessage(Text.of(TextColors.GOLD, "Repaired item(s) in your hand."));
			} else {
				src.sendMessage(Text.of(TextColors.RED, "ERROR! You must be holding something to repair!"));
			}
		} else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.RED, "ERROR! Must be an in-game player to use /repair!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "repair", "Repair" };
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
