package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class HatCommand extends CommandExecutorBase
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException 
	{
		if (src instanceof Player) {
			Player player = (Player) src;
			Optional<ItemStack> itemInHand = player.getItemInHand(HandTypes.MAIN_HAND);

			//places item in hand into head slot
			if (itemInHand.isPresent()) {
				if (itemInHand.get().getQuantity() > 1) {
					ItemStack stack = itemInHand.get();
					stack.setQuantity(itemInHand.get().getQuantity() - 1);
					player.setItemInHand(HandTypes.MAIN_HAND, stack);
					stack.setQuantity(1);
					player.setHelmet(stack);
					player.sendMessage(Text.of(TextColors.GOLD, "Hat Sucessfully Set"));
				} else {
					player.setHelmet(itemInHand.get());
					player.setItemInHand(HandTypes.MAIN_HAND, null);
					player.sendMessage(Text.of(TextColors.GOLD, "Hat Sucessfully Set"));
				}
			} else {
				player.sendMessage(Text.of(TextColors.RED, "No item selected in hotbar."));
				return CommandResult.empty();
			}
		}
		
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "hat", "Hat" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Hat Command"))
				.permission("rrr.cheat.hat")
				.executor(this)
				.build();
	}
}
