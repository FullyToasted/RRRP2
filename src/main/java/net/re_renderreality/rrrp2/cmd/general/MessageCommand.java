package net.re_renderreality.rrrp2.cmd.general;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.events.MessageEvent;

public class MessageCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/msg";
		description = "Send a private message to another player";
		perm = "rrr.general.msg";
		useage = "/msg <player> <msg>";
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
	
	/**
	 * Command to create a MailCore and send it to another player
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		setLocalVariables();
		Optional<Player> player = ctx.<Player> getOne("player");
		Optional<String> message = ctx.<String> getOne("Message");
		//Database Layout 
		if(src instanceof Player) {
			int targetID;
			PlayerCore target;
			if((player.isPresent()) && message.isPresent()) {
				if (player.isPresent()) {
					target = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.get().getName());
					targetID = target.getID();
				} else {
					targetID = 0;
					target = null;
				}
				
				if(!(targetID == 0)) {
					Registry.getGame().getEventManager().post(new MessageEvent(src, player.get(), message.get()));
					return CommandResult.success();
				} else {
					src.sendMessage(Text.of(TextColors.RED, " Name not found in Player database"));
					return CommandResult.empty();
				}
			}
			src.sendMessage(Text.of(TextColors.RED, "Error in Message Format. Correct Useage: ", TextColors.GRAY, "/Tell <player> <message>"));
		} else if(src instanceof ConsoleSource) {
			if(player.isPresent() && message.isPresent()) {
				int targetID = Registry.getOnlinePlayers().getIDfromUsername(player.get().getName());
				if(!(targetID == 0)) {
					Registry.getGame().getEventManager().post(new MessageEvent(src , player.get(), message.get()));
					return CommandResult.success();
				} else {
					src.sendMessage(Text.of(TextColors.RED, " Name not found in Player database"));
					return CommandResult.empty();
				}
			} else {
				src.sendMessage(Text.of(TextColors.RED, "Error in Message Format. Correct Useage: ", TextColors.GRAY, "/Tell <player> <message>"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "If you throw this error you are truely trying this plugin"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "Message", "message", "whisper", "Whisper", "msg", "Tell", "tell"};
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
						GenericArguments.remainingJoinedStrings(Text.of("Message")))
			.executor(this)
			.build();
	}
}
