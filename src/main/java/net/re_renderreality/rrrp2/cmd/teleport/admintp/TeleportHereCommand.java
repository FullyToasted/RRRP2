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
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

//force tp a player to src
public class TeleportHereCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tpohere";
		description = "Force a player to teleort to your location";
		perm = "rrr.admin.tpohere";
		useage = "/tphere <Player>";
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
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Teleport;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(this)
				.build();
	}
}
