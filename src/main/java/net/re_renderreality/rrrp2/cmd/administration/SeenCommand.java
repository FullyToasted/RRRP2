package net.re_renderreality.rrrp2.cmd.administration;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;

public class SeenCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> p = ctx.<String> getOne("player");
		
		if(p.isPresent()) {
			String username = p.get();
			int id = Database.getPlayerIDfromUsername(username);
			String lastSeen = Database.getLastSeen(id);
			
			src.sendMessage(Text.of(TextColors.GOLD, "Player ", TextColors.GRAY, username, TextColors.GOLD, " Was last seen on: ", TextColors.GRAY, lastSeen));
			return CommandResult.success();
		}
		src.sendMessage(Text.of(TextColors.GOLD, "Need to supply a playername"));
		return CommandResult.empty();
	}


	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] {"seen", "Seen" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Gives detailed information about the requested player"))
				.permission("rrr.admin.seen")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("player")))))
				.executor(this).build();
	}
}