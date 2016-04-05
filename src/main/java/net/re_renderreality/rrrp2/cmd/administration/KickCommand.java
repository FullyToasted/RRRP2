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
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class KickCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Player> player = ctx.<Player> getOne("player");
		String reason = ctx.<String> getOne("reason").orElse("You have been kicked from the server!");
		
		if(player.isPresent()) {
			Player players = player.get();
			players.getPlayer().get().kick(Text.builder()
				.append(Text.of(TextColors.DARK_RED, "You have been tempbanned!\n", TextColors.RED, "Reason: "))
				.append(TextSerializers.formattingCode('&').deserialize(reason), Text.of("\n"))
				.build());
			return CommandResult.success();
		}
		src.sendMessage(Text.of(TextColors.RED, "Need to specify a player. Correct useage: /kick <player> (reason)"));
		return CommandResult.empty();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "Kick", "kick" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of("Kick Command"))
			.permission("rrr.admin.kick")
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("reason")))))
			.executor(this).build();
	}
}
