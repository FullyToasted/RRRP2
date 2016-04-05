package net.re_renderreality.rrrp2.cmd.teleport;

import javax.annotation.Nonnull;

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
import net.re_renderreality.rrrp2.utils.TPInvitation;

public class TPDenyCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			Player sender = null;

			TPInvitation cancel = null;

			for (TPInvitation invitation : RRRP2.pendingInvites) {
				if (invitation.recipient == player)
				{
					sender = invitation.sender;
					cancel = invitation;
					break;
				}
			}

			if (cancel != null && sender != null)
			{
				sender.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Your TP Request was Denied by " + player.getName() + "!"));
				RRRP2.pendingInvites.remove(cancel);
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.WHITE, "TP Request Denied."));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Pending TP request not found!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tpdeny!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /tpdeny!"));
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "tpdeny", "TPDeny", "tpDeny", "TPdeny" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
				.builder()
				.description(Text.of("TP Deny Command"))
				.permission("rrr.general.tp.deny")
				.executor(this)
				.build();
	}
}