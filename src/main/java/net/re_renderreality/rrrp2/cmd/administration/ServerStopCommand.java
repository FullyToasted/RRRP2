package net.re_renderreality.rrrp2.cmd.administration;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class ServerStopCommand extends CommandExecutorBase
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> stopMessage = ctx.<String> getOne("reason");

		if (stopMessage.isPresent()) {
			Sponge.getServer().shutdown(TextSerializers.FORMATTING_CODE.deserialize(stopMessage.get()));
		} else {
			Sponge.getServer().shutdown();
		}

		src.sendMessage(Text.of(TextColors.GOLD, "Server Stopping."));
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "stop", "shutdown" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Stop Command"))
			.permission("rrr.admin.stop")
			.arguments(GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("reason")))))
			.executor(this)
			.build();
	}
}
