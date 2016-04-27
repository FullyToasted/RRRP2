package net.re_renderreality.rrrp2.cmd.general;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class BlockDBCommand extends CommandExecutorBase {
	//Gives block information to the player
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;

			BlockRay<World> playerBlockRay = BlockRay.from(player).blockLimit(5).build();
			BlockRayHit<World> finalHitRay = null;

			while (playerBlockRay.hasNext()) {
				BlockRayHit<World> currentHitRay = playerBlockRay.next();

				if (!player.getWorld().getBlockType(currentHitRay.getBlockPosition()).equals(BlockTypes.AIR)) {
					finalHitRay = currentHitRay;
					break;
				}
			}

			if (finalHitRay != null) {
				player.sendMessage(Text.of(TextColors.GOLD, "The ID of the block you're looking at is: ", TextColors.GRAY, finalHitRay.getLocation().getBlock().getType().getName()));
				Optional<Object> metaDataQuery = finalHitRay.getLocation().getBlock().toContainer().get(DataQuery.of("UnsafeMeta"));
				player.sendMessage(Text.of(TextColors.GOLD, "The meta of the block you're looking at is: ", TextColors.GRAY, metaDataQuery.isPresent() ? metaDataQuery.get().toString() : 0));
			} else {
				player.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You're not looking at any block within range."));
			}
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You must be an in-game player to use this command."));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "blockinfo" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("BlockInfo Command"))
			.permission("rrr.general.blockinfo")
			.executor(this).build();
	}
}
