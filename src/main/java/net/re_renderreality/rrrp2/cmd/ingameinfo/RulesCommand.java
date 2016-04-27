package net.re_renderreality.rrrp2.cmd.ingameinfo;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigRules;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.utils.Utilities;

public class RulesCommand extends CommandExecutorBase{
	
	//Sends a list of the server rules to the player
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Utilities.getPaginationService().builder()
	    	.title(ReadConfigRules.getHeader())
	    	.contents(ReadConfigRules.getRules())
	    	.footer(ReadConfigRules.getFooter())
	    	.sendTo(src);
		return CommandResult.success();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "Rules", "rules", "getRules", "GetRules", "Getrules" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Displays Server Rules"))
			.permission("rrr.general.rules")
			.executor(this)
			.build();
	}
	
}
