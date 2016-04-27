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

public class RulesModifyCommand extends CommandExecutorBase {
	
	//Modify an existing rule
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Optional<Integer> theID = ctx.<Integer> getOne("ID");
		Optional<String> theRule = ctx.<String> getOne("Rule");
		if(theRule.isPresent() && theID.isPresent()) {
			String rule = theRule.get();
			int id = theID.get();
			ReadConfigRules.changeRule(id, rule);
			src.sendMessage(Text.of(TextColors.GOLD, "Rule Modified Sucessfully!"));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ModifyRule", "modifyRule", "Modifyrule", "modifyrule"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Modifies Server Rules"))
			.permission("rrr.admin.rules.modify")
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("ID"))),GenericArguments.remainingJoinedStrings(Text.of("Rule")))
			.executor(this)
			.build();
	}
	
}
