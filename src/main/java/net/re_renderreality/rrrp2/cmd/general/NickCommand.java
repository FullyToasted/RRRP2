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
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

/**
 * Sets a player's nickname
 */
public class NickCommand  extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> player = ctx.<String> getOne("player name");
		Optional<Player> pPlayer = ctx.<Player> getOne("player");
		String nick = ctx.<String>getOne("nick").get();
		
		if (src.hasPermission("rrr.general.nick.others") && (player.isPresent() || pPlayer.isPresent())) {
			PlayerCore players = null;
			if(player.isPresent()) {
				int targetID = Database.getPlayerIDfromUsername(player.get());
				players = Database.getPlayerCore(targetID);
				if(targetID == 0) {
					src.sendMessage(Text.of(TextColors.RED, "Player does not exsist!"));
					return CommandResult.empty();
				}
			} else if(pPlayer.isPresent()) {
				int targetID = Database.getPlayerIDfromUsername(pPlayer.get().getName());
				players = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(targetID);
				if(targetID == 0) {
					src.sendMessage(Text.of(TextColors.RED, "Player does not exsist!"));
					return CommandResult.empty();
				}
			}
			if(nick.equals("off")) {
				players.setNickUpdate("");
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully removed!"));
				return CommandResult.success();
			}
			players.setNickUpdate(nick);
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Nick successfully set!"));
			String name = players.getNick();
			Text newName = TextSerializers.formattingCode('&').deserialize(name);
			Utilities.broadcastMessage(Text.of(TextColors.GRAY, players.getName(), TextColors.GOLD, " is now known as: ").concat(newName));
		} else if (src instanceof Player && !src.hasPermission("rrr.general.nick.others")) {
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
			.description(Text.of("Nickname a player"))
			.permission("rrr.general.nick")
			.arguments(
					GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
					GenericArguments.onlyOne(GenericArguments.string(Text.of("player name")))),
					GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("nick"))))
			.executor(this).build();
	}
}
