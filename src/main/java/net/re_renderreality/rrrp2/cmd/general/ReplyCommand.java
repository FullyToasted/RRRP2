package net.re_renderreality.rrrp2.cmd.general;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
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

public class ReplyCommand extends CommandExecutorBase
{
	/**
	 * Command to Reply to a recent message
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> messages = ctx.<String> getOne("Message");
		
		Logger l = Registry.getLogger();
		if (messages.isPresent()) {
			String message = messages.get();
			if(RRRP2.recentlyMessaged.containsKey(src.getName())) {
				CommandSource target = RRRP2.recentlyMessaged.get(src.getName());
				target.sendMessage(Text.of(TextColors.GRAY, src.getName() + " whsipers to you: " + message));
				src.sendMessage(Text.of(TextColors.GRAY, "You whisper to " + target.getName() + ": "+ message));

				RRRP2.recentlyMessaged.put(target.getName(), target);
				
				l.info(target.getName() + " whsipers to " + target.getName() + ": " + message);
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
	public CommandSpec getSpec()
	{
		
		return CommandSpec.builder()
			.description(Text.of("A message to another player"))
			.permission("rrr.general.message")
			.arguments(GenericArguments.remainingJoinedStrings(Text.of("Message")))
			.executor(this)
			.build();
	}
}
