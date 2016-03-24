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

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class HelpOPCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<String> themsg = ctx.<String> getOne("Msg");
		if(src instanceof Player) {
			if(themsg.isPresent()) {
				String msg = themsg.get();
				for (Player p:Registry.getServer().getOnlinePlayers()) {
					if (p.hasPermission("rrr.general.helpop.recieve")) {
						p.sendMessage(Text.of("Player, " + src.getName() + ", requests help: " + msg)); //sends them the message
						src.sendMessage(Text.of(TextColors.GREEN, "Message has been sent successfully!"));
						return CommandResult.success();
					}
				}
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Only a Player can Execute this command."));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "HelpOP", "helpOP", "helpop", "Helpop"};
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Request help from Staff"))
				.permission("rrr.general.helpop.send")
				.arguments(GenericArguments.remainingJoinedStrings(Text.of("Msg")))
				.executor(this).build();
	}
}
