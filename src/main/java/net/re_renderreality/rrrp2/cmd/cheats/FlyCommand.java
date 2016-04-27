package net.re_renderreality.rrrp2.cmd.cheats;

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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class FlyCommand extends CommandExecutorBase {
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");
		
		if(!targetPlayer.isPresent() && src.hasPermission("rrr.cheat.fly.self")) {
			if( src instanceof Player) {
				Player player = (Player) src;
				PlayerCore playerz = Registry.getOnlinePlayers().getPlayerCorefromUsername(targetPlayer.get().getName());
				
				//toggles fly mode
				if(player.get(Keys.CAN_FLY).isPresent()) {
					boolean canFly = player.get(Keys.CAN_FLY).get();
					player.offer(Keys.CAN_FLY, !canFly);
					if (canFly) {
						playerz.setFlyUpdate(false);
						player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "off."));
					} else {
						player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "on."));
						playerz.setFlyUpdate(true);
					}
				}
			}else if (src instanceof ConsoleSource) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
			}else if (src instanceof CommandBlockSource) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /fly!"));
			}
		//set fly for other players
		} else if (src.hasPermission("rrr.cheat.fly.others")) {
			Player player = targetPlayer.get();
			PlayerCore playerz = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
			if (player.get(Keys.CAN_FLY).isPresent()) {
				boolean canFly = player.get(Keys.CAN_FLY).get();
				player.offer(Keys.CAN_FLY, !canFly);

				if (canFly) {
					src.sendMessage(Text.of(TextColors.GOLD, "Toggled flying for " + player.getName() + ": ", TextColors.GRAY, "off."));
					player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "off."));
					playerz.setFlyUpdate(false);
				} else {
					src.sendMessage(Text.of(TextColors.GOLD, "Toggled flying for " + player.getName() + ": ", TextColors.GRAY, "on."));
					player.sendMessage(Text.of(TextColors.GOLD, "Toggled flying: ", TextColors.GRAY, "on."));
					playerz.setFlyUpdate(false);
				}
			}
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to change others' ability to fly."));
		}

		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "fly", "FLY", "Fly" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Fly Command"))
			.permission("rrrp2.cheat.fly.self")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
			.executor(this)
			.build();
	}
}