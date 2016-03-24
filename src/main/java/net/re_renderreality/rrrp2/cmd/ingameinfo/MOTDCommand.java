package net.re_renderreality.rrrp2.cmd.ingameinfo;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigMOTD;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
public class MOTDCommand extends CommandExecutorBase
{
	/**
	 * Explanation of what command does and if complicated how to do it
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String uniquePlayerCount = ReadConfigMOTD.getMOTD();
		uniquePlayerCount = uniquePlayerCount.replaceAll("%player", src.getName());
		Text newMessage = TextSerializers.formattingCode('&').deserialize(uniquePlayerCount);
		src.sendMessage(newMessage);
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "MOTD", "motd", "Motd" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Depth Command"))
			.permission("rrr.general.getworld")
			.executor(this)
			.build();
	}
}
