package net.re_renderreality.rrrp2.cmd.general;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

/**
 * @author alexh
 * @note TODO: deserialize nick so color codes work
 */
public class NickCommand  extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");
		String nick = ctx.<String>getOne("nick").get();
		
		if (!targetPlayer.isPresent() && src instanceof Player)
		{
			Player player = (Player) src;
			int id = Database.getID(player.getUniqueId().toString());
			PlayerCore playerz = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));
			Utilities.broadcastMessage("&6 " + playerz.getName() + " &7 is now known as: " + playerz.getNick());
			
			playerz.setNickUpdate(nick);
		} else if(src.hasPermission("rrr.general.nick.others") && targetPlayer.isPresent()) {
			int id = Database.getID(targetPlayer.get().getUniqueId().toString());
			PlayerCore players = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
			players.setNickUpdate(nick);
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));
			Utilities.broadcastMessage("&6 " + players.getName() + " &7 is now known as: " + players.getNick());
		}
		else if (src instanceof Player && !src.hasPermission("fly.others"))
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to make changes to other player's nicknames.!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "nick", "Nick" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Nick Command"))
			.permission("rrr.general.nick")
			.arguments(
				GenericArguments.seq(
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))),
					GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("nick")))))
			.executor(this).build();
	}
}
