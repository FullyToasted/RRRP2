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
import net.re_renderreality.rrrp2.database.Registry;

public class BroadcastCommand extends AsyncCommandExecutorBase
{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/broadcast";
		description = "Broadcast a message to everyone on the server";
		perm = "rrr.admin.broadcast";
		useage = "/broadcast <message>";
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
	
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx) {
		setLocalVariables();
		String message = ctx.<String> getOne("message").get();
		Text msg = TextSerializers.formattingCode('&').deserialize(message);
		Text broadcast = Text.of(TextColors.DARK_GRAY, "[", TextColors.DARK_RED, "Broadcast", TextColors.DARK_GRAY, "]", TextColors.GREEN, " ");
		Text finalBroadcast = Text.builder().append(broadcast).append(msg).build();
		//sends a message to everyone online
		MessageChannel.TO_ALL.send(finalBroadcast);
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "broadcast", "bc", "Broadcast", "BC" };
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
				.arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
				.executor(this)
				.build();
	}
}
