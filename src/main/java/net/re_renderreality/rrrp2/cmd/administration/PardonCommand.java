package net.re_renderreality.rrrp2.cmd.administration;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.BanCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class PardonCommand extends CommandExecutorBase {

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Logger l = RRRP2.getRRRP2().getLogger();
		Game game = RRRP2.getRRRP2().getGame();
		Optional<String> sPlayer = ctx.<String> getOne("player name");

		PlayerCore playercore = null;
		BanCore ban = null;
		User players = null;
		int id = 0;
		if(sPlayer.isPresent()) {
			id = Database.getPlayerIDfromUsername(sPlayer.get());
			ban = Database.getOneBan(id);
			playercore = Database.getPlayerCore(id);
			l.info(playercore.getName() + " " + UUID.fromString(playercore.getUUID()).toString());
			
			UserStorageService uss = Sponge.getGame().getServiceManager().provide(UserStorageService.class).get();
			if(playercore.getUUID().equals("uuid")) {
				src.sendMessage(Text.of(TextColors.RED, "This Player has never joined the server"));
				return CommandResult.empty();
			}
			Optional<User> ogp = uss.get(UUID.fromString(playercore.getUUID()));
			if (ogp.isPresent())
			{
				players = ogp.get();
			}
		
			BanService srv = game.getServiceManager().provide(BanService.class).get();
			if (!playercore.getBanned()) {
				src.sendMessage(Text.of(TextColors.RED, "That player is not currently banned."));
				return CommandResult.empty();
			}
			
			srv.removeBan(srv.getBanFor(players.getProfile()).get());
			ban.delete();
			playercore.setBannedUpdate(false);
			Database.execute("DELETE FROM bans WHERE ID = " + id + ";");
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, playercore.getName() + " has been unbanned."));
			return CommandResult.success();
		}
		src.sendMessage(Text.of(TextColors.RED, "Need to specify player to unban"));
		return CommandResult.empty();
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "unban", "UnBan", "unBan", "Unban", "pardon", "Pardon" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Unban Command"))
			.permission("rrr.admin.unban")
			.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("player name"))))
			.executor(this).build();
	}
}
