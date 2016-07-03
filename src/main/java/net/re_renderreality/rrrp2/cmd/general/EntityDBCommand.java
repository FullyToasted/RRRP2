package net.re_renderreality.rrrp2.cmd.general;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class EntityDBCommand extends CommandExecutorBase {
	//shows information about the entity being looked on
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
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
				Entity entityFound = null;

				for (Entity entity : player.getWorld().getEntities()) {
					if (entity.getLocation().getBlockPosition().equals(finalHitRay.getBlockPosition().add(new Vector3i(0, 1, 0)))) {
						entityFound = entity;
						break;
					}
				}
				
				if (entityFound != null) {
					player.sendMessage(Text.of(TextColors.GOLD, "The name of the entity you're looking at is: ", TextColors.GRAY, entityFound.getType().getName()));
				} else {
					player.sendMessage(Text.of(TextColors.RED, "Error! No entity found!"));
				}
			} else {
				player.sendMessage(Text.of(TextColors.RED, "Error! You're not looking at any block within range."));
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! You must be an in-game player to use this command."));
		}

		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "entityinfo" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("EntityInfo Command"))
				.permission("rrr.general.entityinfo")
				.executor(this).build();
	}
}