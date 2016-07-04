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
import net.re_renderreality.rrrp2.database.Registry;

public class KickCommand extends CommandExecutorBase
{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/kick";
		description = "Kick a player temporarily from the server";
		perm = "rrr.admin.kick";
		useage = "/kick <player> <reason>";
		notes = "Must provide a kick reason!";
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
		setLocalVariables();
		Optional<Player> player = ctx.<Player> getOne("player");
		//if reason is not there use default reason
		Optional<String> oReason = ctx.<String> getOne("reason");
		
		if(oReason.isPresent()) {
			String reason = oReason.get();
			if(player.isPresent()) {
				Player players = player.get();
				players.getPlayer().get().kick(Text.builder()
					.append(Text.of(TextColors.DARK_RED, "You have been tempbanned!\n", TextColors.RED, "Reason: "))
					.append(TextSerializers.formattingCode('&').deserialize(reason), Text.of("\n"))
					.build());
				return CommandResult.success();
			}
			src.sendMessage(Text.of(TextColors.RED, "Need to specify a player. Correct useage: /kick <player> <reason>"));
			return CommandResult.empty();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Need to specify a Reason. Correct useage: /kick <player> <reason>"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "Kick", "kick" };
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
		return CommandSpec
			.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("reason")))))
			.executor(this).build();
	}
}
