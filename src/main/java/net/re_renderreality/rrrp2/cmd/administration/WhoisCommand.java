package net.re_renderreality.rrrp2.cmd.administration;

import java.util.Optional;

import javax.annotation.Nonnull;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * @author Avarai/Poesidon2012
 * @note Plans: Write Complete Command with refrences to the Database.
 */
public class WhoisCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> player = ctx.<String> getOne("player name");
		Optional<Player> pPlayer = ctx.<Player> getOne("player");
		
		PlayerCore playercore = null;
		if(player.isPresent()) {
			playercore = Database.getPlayerCore(Database.getPlayerIDfromUsername(player.get()));
		} else if(pPlayer.isPresent()) {
			playercore = Registry.getOnlinePlayers().getPlayerCorefromUsername(pPlayer.get().getName());
		}
		if( playercore.getID() == 0 ) {
			src.sendMessage(Text.of(TextColors.RED, "Player does not Exsist!"));
			return CommandResult.empty();
		}
		
		if(src instanceof Player) {
			Player playerSRC = (Player) src;
			//checks if player can check others' whois
			if(playerSRC.hasPermission("rrr.admin.whois.others") && (player.isPresent() || pPlayer.isPresent())) {
					sendWhois(src, playercore);
					return CommandResult.success();
			} else if(!player.isPresent() && !pPlayer.isPresent()) {
				String uuid = ((Player) src).getUniqueId().toString();
				int ids = Database.getID(uuid);
				PlayerCore self = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(ids);
				sendWhois(src, self);
				return CommandResult.success();
			}
			
		} else if((player.isPresent() || pPlayer.isPresent()) && src instanceof ConsoleSource) {
				sendWhois(src, playercore);
				return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to check Whois"));
			return CommandResult.empty();
		}
		return CommandResult.success();
	}
	
	private void sendWhois(CommandSource src, PlayerCore offlinePlayer) {
		src.sendMessage(Text.of(TextColors.GREEN, "------------------------------------------------------"));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Username: ", TextColors.GREEN, offlinePlayer.getName()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's ID Number: ", TextColors.GRAY, offlinePlayer.getID()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's UUID: ", TextColors.GRAY, offlinePlayer.getUUID()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's IP: ", TextColors.GRAY, offlinePlayer.getIP()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Nickname: ", TextColors.GRAY, offlinePlayer.getNick()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Chat Channel : ", TextColors.GRAY, offlinePlayer.getChannel()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Money: ", TextColors.GRAY, offlinePlayer.getMoney()));
		if(offlinePlayer.getBanned()) {
			src.sendMessage(Text.of(TextColors.GOLD, "Banned: ", TextColors.GREEN, offlinePlayer.getBanned()));
		} else {
			src.sendMessage(Text.of(TextColors.GOLD, "Banned: ", TextColors.RED, offlinePlayer.getBanned()));
		}
		if(offlinePlayer.getGod()) {
			src.sendMessage(Text.of(TextColors.GOLD, "Godmode: ", TextColors.GREEN, offlinePlayer.getGod()));
		} else {
			src.sendMessage(Text.of(TextColors.GOLD, "Godmode: ", TextColors.RED, offlinePlayer.getGod()));
		}
		if(offlinePlayer.getFly()) {
			src.sendMessage(Text.of(TextColors.GOLD, "Fly: ", TextColors.GREEN, offlinePlayer.getFly()));
		} else {
			src.sendMessage(Text.of(TextColors.GOLD, "Fly: ", TextColors.RED, offlinePlayer.getFly()));
		}
		if(offlinePlayer.getTPToggle()) {
			src.sendMessage(Text.of(TextColors.GOLD, "TpToggle: ", TextColors.GREEN, offlinePlayer.getTPToggle()));
		} else {
			src.sendMessage(Text.of(TextColors.GOLD, "TpToggle: ", TextColors.RED, offlinePlayer.getTPToggle()));
		}
		if(offlinePlayer.getInvisible()) {
			src.sendMessage(Text.of(TextColors.GOLD, "Invisible: ", TextColors.GREEN, offlinePlayer.getInvisible()));
		} else {
			src.sendMessage(Text.of(TextColors.GOLD, "Invisible: ", TextColors.RED, offlinePlayer.getInvisible()));
		}
		src.sendMessage(Text.of(TextColors.GOLD, "Play Time: ", TextColors.GRAY, offlinePlayer.getOnlinetime()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Last Location: ", TextColors.GRAY, offlinePlayer.getLastlocation()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Last Death: ", TextColors.GRAY, offlinePlayer.getLastdeath()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player's Join Date: ", TextColors.GRAY, offlinePlayer.getFirstseen()));
		src.sendMessage(Text.of(TextColors.GOLD, "Player Was Last Seen: ", TextColors.GRAY, offlinePlayer.getLastseen()));
		src.sendMessage(Text.of(TextColors.GREEN, "------------------------------------------------------"));
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "whois", "whoIs", "WhoIs", "WhoIS" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Gives detailed information about the requested player"))
				.permission("rrr.general.whois.self")
				.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.onlyOne(GenericArguments.string(Text.of("player name")))))
				.executor(this).build();
	}
}