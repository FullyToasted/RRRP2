package net.re_renderreality.rrrp2.cmd.teleport.admintp;

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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class TeleportAllCommand extends CommandExecutorBase {
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<Player> target = ctx.<Player> getOne("player");

		if (src instanceof Player) {
			if (target.isPresent() && src.hasPermission("rrr.admin.tpall.others")) {
				Player source = target.get();
				source.sendMessage(Text.of(TextColors.GOLD, "Teleported everyone to you!"));
				tp(source);
				return CommandResult.success();
			} else {
				Player source = (Player) src;
				source.sendMessage(Text.of(TextColors.GOLD, "Teleported everyone to you!"));
				tp(source);
				return CommandResult.success();
			}
		} else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			if (target.isPresent()) {
				Player source = target.get();
				source.sendMessage(Text.of(TextColors.GOLD, "Teleported everyone to you!"));
				tp(source);
				return CommandResult.success();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Command Failed Post Error on Project Github"));
			return CommandResult.empty();
		}
		src.sendMessage(Text.of(TextColors.RED, "Command Failed Post Error on Project Github"));
		return CommandResult.empty();
	}
	
	public void tp(Player source) {
		for(Player p:Registry.getServer().getOnlinePlayers()) {
			if(!(p == source)) {
				PlayerCore player = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(p.getName());
				player.setLastlocationUpdate(Utilities.convertLocation(p));
				p.sendMessage(Text.of(TextColors.GOLD, "You have been teleported to: ", TextColors.GRAY, source.getName()));
				p.setLocation(source.getLocation());
			}
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "TPall", "TPAll", "tpAll", "tpall" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("TP All Operator Command"))
				.permission("rrr.admin.tpall.self")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this)
				.build();
	}
}
