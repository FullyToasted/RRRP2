package net.re_renderreality.rrrp2.cmd.teleport.admintp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class TeleportHereCommand extends CommandExecutorBase {
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Player recipient = ctx.<Player> getOne("player").get();

		if (src instanceof Player) {
			Player player = (Player) src;
			if (recipient == player) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport to yourself!"));
			}
			else {
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.WHITE, recipient.getName() + " has been teleported to your location!"));
				recipient.sendMessage(Text.of(TextColors.GREEN, player.getName(), TextColors.WHITE, " has teleported you to their location!"));
				PlayerCore r = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(recipient.getName());
				r.setLastlocationUpdate(Utilities.convertLocation(recipient));
				recipient.setLocation(player.getLocation());
			}

		}
		else if (src instanceof ConsoleSource) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tphere!"));
		}
		else if (src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tphere!"));
		}
		
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "TPOhere", "TPOHere", "tpoHere", "tpohere" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("TP Here Operator Command"))
				.permission("rrr.admin.tphere")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(this)
				.build();
	}
}
