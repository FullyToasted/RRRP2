package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class SkullCommand extends CommandExecutorBase
{
	private Game game = Registry.getGame();

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> optionalTarget = ctx.<Player> getOne("player");

		if (!optionalTarget.isPresent()) {
			if (src instanceof Player) {
				Player player = (Player) src;

				// Create the Skull
				ItemStack skullStack = ItemStack.builder().itemType(ItemTypes.SKULL).quantity(1).build();

				// Set it to player skull type
				skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

				// Set the owner to the command executor
				skullStack.offer(Keys.REPRESENTED_PLAYER, player.getProfile());

				// Put it in inventory
				player.getInventory().offer(skullStack);

				player.sendMessage(Text.of(TextColors.GOLD, "Created skull of your head. Enjoy!"));
			} else {
				src.sendMessage(Text.of(TextColors.RED, "ERROR! Must be an in-game player to use /skull!"));
			}
		} else {
			if (src instanceof Player) {
				Player source = (Player) src;
				Player player = optionalTarget.get();

				// Create the Skull
				ItemStack.Builder builder = game.getRegistry().createBuilder(ItemStack.Builder.class);
				ItemStack skullStack = builder.itemType(ItemTypes.SKULL).quantity(1).build();

				// Set it to player skull type
				skullStack.offer(Keys.SKULL_TYPE, SkullTypes.PLAYER);

				// Set the owner to the specified player
				skullStack.offer(Keys.REPRESENTED_PLAYER, player.getProfile());

				// Put it in inventory
				source.getInventory().offer(skullStack);

				source.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Created skull of player's head. Enjoy!"));
			} else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /skull!"));
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "skull", "playerskull", "head" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Skull Command"))
			.permission("rrr.cheat.skull")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
			.executor(this)
			.build();
	}
}
