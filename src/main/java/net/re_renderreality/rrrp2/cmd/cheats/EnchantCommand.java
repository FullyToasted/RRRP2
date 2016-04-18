package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class EnchantCommand extends CommandExecutorBase {
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<Player> target = ctx.<Player> getOne("target");
		String enchantmentName = ctx.<String> getOne("enchantment").get();
		int level = ctx.<Integer> getOne("level").get();

		Enchantment enchantment = Sponge.getRegistry().getType(Enchantment.class, enchantmentName).orElse(null);
		
		if (enchantment == null) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment specified not found!"));
			return CommandResult.success();
		}

		if (!ReadConfig.getUnsafeEnchantmentStatus()) {
			
			if (enchantment.getMaximumLevel() < level) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment level too high!"));
				return CommandResult.success();
			}
		}

		if (!target.isPresent()) {
			if (src instanceof Player) {
				Player player = (Player) src;

				if (player.getItemInHand().isPresent())	{
					ItemStack itemInHand = player.getItemInHand().get();

					if (!enchantment.canBeAppliedToStack(itemInHand)) {
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment cannot be applied to this item!"));
						return CommandResult.success();
					}

					EnchantmentData enchantmentData = itemInHand.getOrCreate(EnchantmentData.class).get();
					ItemEnchantment itemEnchantment = new ItemEnchantment(enchantment, level);
					ItemEnchantment sameEnchantment = null;

					for (ItemEnchantment ench : enchantmentData.enchantments()) {
						if (ench.getEnchantment().getId().equals(enchantment.getId())) {
							sameEnchantment = ench;
							break;
						}
					}

					if (sameEnchantment == null) {
						enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
					}
					else {
						enchantmentData.set(enchantmentData.enchantments().remove(sameEnchantment));
						enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
					}

					itemInHand.offer(enchantmentData);
					player.setItemInHand(itemInHand);
					player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Enchanted item(s) in your hand."));
				}
				else {
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to enchant!"));
				}
			}
			else if (src instanceof ConsoleSource) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /enchant!"));
			}
			else if (src instanceof CommandBlockSource) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /enchant!"));
			}
		}
		else if (target.isPresent() && src.hasPermission("rrr.cheat.enchantothers")) {
			Player player = target.get();

			if (player.getItemInHand().isPresent()) {
				ItemStack itemInHand = player.getItemInHand().get();

				if (!enchantment.canBeAppliedToStack(itemInHand)) {
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Enchantment cannot be applied to this item!"));
					return CommandResult.success();
				}

				EnchantmentData enchantmentData = itemInHand.getOrCreate(EnchantmentData.class).get();
				ItemEnchantment itemEnchantment = new ItemEnchantment(enchantment, level);
				ItemEnchantment sameEnchantment = null;

				for (ItemEnchantment ench : enchantmentData.enchantments()) {
					if (ench.getEnchantment().getId().equals(enchantment.getId())) {
						sameEnchantment = ench;
						break;
					}
				}

				if (sameEnchantment == null) {
					enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
				}
				else {
					enchantmentData.set(enchantmentData.enchantments().remove(sameEnchantment));
					enchantmentData.set(enchantmentData.enchantments().add(itemEnchantment));
				}

				itemInHand.offer(enchantmentData);
				player.setItemInHand(itemInHand);
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Enchanted item(s) in your hand."));
			}
			else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be holding something to enchant!"));
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "enchant", "ench" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Enchant Command"))
			.permission("rrr.cheat.enchant")
			.arguments(GenericArguments.seq(GenericArguments.optional(GenericArguments.player(Text.of("target"))), 
											GenericArguments.onlyOne(GenericArguments.integer(Text.of("level")))), 
											GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("enchantment"))))
			.executor(this)
			.build();
	}
}
