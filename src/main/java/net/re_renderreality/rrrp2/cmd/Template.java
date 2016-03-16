package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Template extends CommandExecutorBase
{

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "alias" };
	}
	//oneARG
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("#########")).permission("rrrp2.####")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
				.executor(this).build();
	}
	/*                  Multiple Arguments
	 *  @Nonnull
	 *	@Override
	 *	public CommandSpec getSpec()
	 *	{
	 *		return CommandSpec
	 *			.builder()
	 *			.description(Text.of("Enchant Command"))
	 *			.permission("rrrp2.enchant.use")
	 *			.arguments(GenericArguments.seq(GenericArguments.optional(GenericArguments.player(Text.of("Arg1"))), GenericArguments.onlyOne(GenericArguments.integer(Text.of("Arg2")))), GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("Arg3"))))
	 *			.executor(this).build();
	 *	}
	 */
	
	/*             No Arguments
	 *  @Nonnull
		@Override
		public CommandSpec getSpec()
		{
			return CommandSpec.builder()
				.description(Text.of("AFK Command"))
				.permission("essentialcmds.afk.use")
				.executor(this)
				.build();
		}
	 * 
	 * 
	 */
}

