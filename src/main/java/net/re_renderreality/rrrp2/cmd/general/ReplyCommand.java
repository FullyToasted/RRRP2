package net.re_renderreality.rrrp2.cmd.general;

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

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.Log;

public class ReplyCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/reply";
		description = "Reply to the most recent message recieved";
		perm = "rrr.general.msg";
		useage = "/r <msg>";
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
	 * Command to Reply to a recent message
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<String> messages = ctx.<String> getOne("Message");
		if (messages.isPresent()) {
			String message = messages.get();
			if(RRRP2.recentlyMessaged.containsKey(src.getName())) {
				CommandSource target = RRRP2.recentlyMessaged.get(src.getName());
				target.sendMessage(Text.of(TextColors.GRAY, src.getName() + " whsipers to you: " + message));
				src.sendMessage(Text.of(TextColors.GRAY, "You whisper to " + target.getName() + ": "+ message));

				RRRP2.recentlyMessaged.put(target.getName(), target);
				
				Log.info(target.getName() + " whsipers to " + target.getName() + ": " + message);
				for(Player p : RRRP2.socialSpy) {
					if(!(p.getName().equals(src.getName()))) {
						p.sendMessage(Text.of(TextColors.GRAY, target.getName() + " whsipers to " + target.getName() + ": " + message));
					}
				}
				return CommandResult.success();
			} else {
				src.sendMessage(Text.of(TextColors.RED, "You have no recent Messages to reply to"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "You messed up report what you typed to mod author"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "Reply", "reply", "r", "R"};
		
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
			.arguments(GenericArguments.remainingJoinedStrings(Text.of("Message")))
			.executor(this)
			.build();
	}
}
