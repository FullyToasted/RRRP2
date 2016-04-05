package net.re_renderreality.rrrp2.cmd.administration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

//import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.ban.Ban;
import org.spongepowered.api.util.ban.BanTypes;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigDatabase;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.BanCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class TempBanCommand extends CommandExecutorBase {
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		//Logger l = RRRP2.getRRRP2().getLogger();
		Game game = RRRP2.getRRRP2().getGame();
		Optional<Player> player = ctx.<Player> getOne("player");
		Optional<String> sPlayer = ctx.<String> getOne("player name");
		String time = ctx.<String> getOne("dd:hh:mm:ss").get();
		String reason = ctx.<String> getOne("reason").orElse("The BanHammer has spoken!");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String todaysDate = dateFormat.format(cal.getTime());
		
		PlayerCore playercore = null;
		User players = null;
		int id = 0;
		
		if(player.isPresent()) {
			id = Database.getPlayerIDfromUsername(player.get().getName());
			playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
			players = player.get();
		} else if(sPlayer.isPresent()) {
			id = Database.getPlayerIDfromUsername(sPlayer.get());
			playercore = Database.getPlayerCore(id);
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
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Command Failed!"));
		}

		BanService srv = game.getServiceManager().provide(BanService.class).get();
		if (srv.isBanned(players.getProfile()))
		{
			src.sendMessage(Text.of(TextColors.RED, "That player has already been banned."));
			return CommandResult.empty();
		}

		srv.addBan(Ban.builder()
			.type(BanTypes.PROFILE)
			.source(src).profile(players.getProfile())
			.expirationDate(getInstantFromString(time))
			.reason(TextSerializers.formattingCode('&').deserialize(reason))
			.build());
		BanCore ban = new BanCore(id, playercore.getName(), players.getUniqueId().toString(), src.getName(), reason, todaysDate, getFormattedString(time));
		ban.insert();
		playercore.setBannedUpdate(true);

		if (player.isPresent()) {
			if (players.isOnline()) {
				players.getPlayer().get().kick(Text.builder()
					.append(Text.of(TextColors.DARK_RED, "You have been tempbanned!\n", TextColors.RED, "Reason: "))
					.append(TextSerializers.formattingCode('&').deserialize(reason), Text.of("\n"))
					.append(Text.of(TextColors.GOLD, "Time: ", TextColors.GRAY, getFormattedString(time)))
					.build());
			}
		}
		
		if(ReadConfigDatabase.getShowBanned()) {
			Utilities.broadcastMessage(Text.of(TextColors.GRAY, ban.getbannedName(), TextColors.GOLD, " Was banned for: ", TextColors.RED, ban.getReason()));
		}

		src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, playercore.getName() + " has been banned."));
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "Tempban", "TempBan", "tempban", "tempBan"};
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("TempBan Command"))
			.permission("rrr.admin.tempban")
			.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
					GenericArguments.onlyOne(GenericArguments.string(Text.of("player name")))), 
					GenericArguments.onlyOne(GenericArguments.string(Text.of("dd:hh:mm:ss"))), 
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("reason")))))
			.executor(this)
			.build();
	}

	public Instant getInstantFromString(String time)
	{
		if (time.contains(":"))
		{
			String[] tokens = time.split(":");
			int days = Integer.parseInt(tokens[0]);
			int hours = Integer.parseInt(tokens[1]);
			int minutes = Integer.parseInt(tokens[2]);
			int seconds = Integer.parseInt(tokens[3]);
			int durationInSec = 3600 * 24 * days + 3600 * hours + 60 * minutes + seconds;
			return Instant.ofEpochSecond((System.currentTimeMillis()) / 1000L + durationInSec);
		}
		else
		{
			return Instant.ofEpochSecond((System.currentTimeMillis()) / 1000L + Integer.parseInt(time));
		}
	}
	
	public String getFormattedString(String time)
	{
		if (time.contains(":"))
		{
			String[] tokens = time.split(":");
			int days = Integer.parseInt(tokens[0]);
			int hours = Integer.parseInt(tokens[1]);
			int minutes = Integer.parseInt(tokens[2]);
			int seconds = Integer.parseInt(tokens[3]);
			return (days + " days, " + hours + " hours, " + minutes + " minutes, and " + seconds + " seconds.");
		}
		else
		{
			return (Integer.parseInt(time) + " seconds.");
		}
	}
}
