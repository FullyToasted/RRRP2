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
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class SeenCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/seen";
		description = "Check the last time a player was online";
		perm = "rrr.admin.seen";
		useage = "/seen <player>";
		notes = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPerm() {
		return this.perm;
	}
	
	public String getUseage() {
		return this.useage;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<String> player = ctx.<String> getOne("player name");
		Optional<Player> pPlayer = ctx.<Player> getOne("player");
		
		if(player.isPresent() || pPlayer.isPresent()) {
			int id = 0;
			String username = "";
			if(player.isPresent()) {
				id = Database.getPlayerIDfromUsername(player.get());
				username = player.get();
			} else if (pPlayer.isPresent()) {
				id = Registry.getOnlinePlayers().getIDfromUsername(pPlayer.get().getName());
				username = pPlayer.get().getName();
			}
			PlayerCore players = Registry.getOnlinePlayers().getPlayer(id);
			String lastSeen = players.getLastseen();
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
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
							GenericArguments.onlyOne(GenericArguments.string(Text.of("player name")))))
				.executor(this).build();
	}
}