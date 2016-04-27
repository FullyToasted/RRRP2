package net.re_renderreality.rrrp2.cmd.ingameinfo;

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

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigRules;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class RulesAddCommand extends CommandExecutorBase {
	
	//Add a rule to the list
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Optional<String> theRule = ctx.<String> getOne("Rule");
		if(theRule.isPresent()) {
			String rule = theRule.get();
			ReadConfigRules.addRule(rule);
			src.sendMessage(Text.of(TextColors.GOLD, "Rule Added Sucessfully!"));
		}
		return CommandResult.empty();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "AddRule", "addRule", "Addrule", "addrule"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Adds Server Rules"))
			.permission("rrr.admin.rules.add")
			.arguments(GenericArguments.remainingJoinedStrings(Text.of("Rule")))
			.executor(this)
			.build();
	}
	
}