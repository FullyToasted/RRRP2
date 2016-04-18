package net.re_renderreality.rrrp2.cmd.general;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;

import javax.annotation.Nonnull;

public class CompassCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			// Credit to kenzierocks for the following line. =D
			String direction = Direction.getClosest(player.getTransform().getRotationAsQuaternion().getDirection()).toString();
			player.sendMessage(Text.of(TextColors.GOLD, "You are facing: ", TextColors.GRAY, direction));
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! You must be an in-game player to use /direction!"));
		}
		
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "direction", "compass" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Direction Command"))
				.permission("rrr.general.compass")
				.executor(this)
				.build();
	}
}
