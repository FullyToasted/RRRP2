package net.re_renderreality.rrrp2.cmd.administration;

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

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;

public class SeenCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> player = ctx.<String> getOne("player name");
		Optional<Player> pPlayer = ctx.<Player> getOne("player");
		
		if(player.isPresent() || pPlayer.isPresent()) {
			int id = 0;
			String username = "";
			if(player.isPresent()) {
				id = Database.getPlayerIDfromUsername(player.get());
				username = player.get();
			} else if (pPlayer.isPresent()) {
				id = Database.getPlayerIDfromUsername(pPlayer.get().getName());
				username = pPlayer.get().getName();
			}
			String lastSeen = Database.getLastSeen(id);
			if(id == 0) {
				src.sendMessage(Text.of(TextColors.RED, "This player has never joined this server"));
				return CommandResult.empty();
			}
			src.sendMessage(Text.of(TextColors.GOLD, "Player ", TextColors.GRAY, username, TextColors.GOLD, " Was last seen on: ", TextColors.GRAY, lastSeen));
			return CommandResult.success();
		}
		src.sendMessage(Text.of(TextColors.GOLD, "Need to supply a playername"));
		return CommandResult.empty();
	}


	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] {"seen", "Seen" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Gives information about the requested player's last date online"))
				.permission("rrr.admin.seen")
				.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
							GenericArguments.onlyOne(GenericArguments.string(Text.of("player name")))))
				.executor(this).build();
	}
}