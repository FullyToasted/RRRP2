package net.re_renderreality.rrrp2.cmd.administration;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.backend.AsyncCommandExecutorBase;

public class BroadcastCommand extends AsyncCommandExecutorBase
{
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx) {
		String message = ctx.<String> getOne("message").get();
		Text msg = TextSerializers.formattingCode('&').deserialize(message);
		Text broadcast = Text.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " ");
		Text finalBroadcast = Text.builder().append(broadcast).append(msg).build();
		MessageChannel.TO_ALL.send(finalBroadcast);
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "broadcast", "bc" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Broadcast Command"))
				.permission("rrr.admin.broadcast")
				.arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
				.executor(this)
				.build();
	}
}
