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
import net.re_renderreality.rrrp2.database.Registry;

public class ServerStopCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/stop";
		description = "Stops the sever";
		perm = "rrr.admin.stop";
		useage = "/stop";
		notes = "Will need console access to turn server back on";
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
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		//optional stop message to show players
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
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("reason")))))
			.executor(this)
			.build();
	}
}
