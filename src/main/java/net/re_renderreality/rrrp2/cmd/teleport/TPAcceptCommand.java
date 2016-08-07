package net.re_renderreality.rrrp2.cmd.teleport;

import javax.annotation.Nonnull;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.events.TPAcceptEvent;
import net.re_renderreality.rrrp2.events.TPHereAcceptEvent;
import net.re_renderreality.rrrp2.utils.TPInvitation;

public class TPAcceptCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tpaccept";
		description = "Accept a teleport request";
		perm = "rrr.teleport.tp";
		useage = "/tpaccept";
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
	
	
	private Game game = RRRP2.getRRRP2().getGame();

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		setLocalVariables();
		if (src instanceof Player) {
			Player player = (Player) src;
			Player sender = null;
			boolean tpaHere = false;
			TPInvitation foundInvitation = null;

			for (TPInvitation invitation : RRRP2.pendingInvites) {
				if (!invitation.isTPAHere && invitation.recipient == player) {
					sender = invitation.sender;
					foundInvitation = invitation;
					break;
				}
				else if (invitation.isTPAHere && invitation.recipient == player) {
					tpaHere = true;
					sender = invitation.sender;
					foundInvitation = invitation;
					break;
				}
			}

			if (sender != null && !tpaHere) {
				game.getEventManager().post(new TPAcceptEvent(player, sender));
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.WHITE, "TP Request Accepted."));
			}
			else if (sender != null) {
				game.getEventManager().post(new TPHereAcceptEvent(player, sender));
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.WHITE, "TP Here Request Accepted."));
			}
			else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Pending TP request not found!"));
			}

			if (foundInvitation != null) {
				RRRP2.pendingInvites.remove(foundInvitation);
			}
		}
		else if (src instanceof ConsoleSource || src instanceof CommandBlockSource) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tpaccept!"));
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "tpaccept", "TPaccept", "TPAccept", "tpAccept" };
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
		return CommandSpec
				.builder()
				.description(Text.of(description))
				.permission(perm)
				.executor(this).build();
	}
}
