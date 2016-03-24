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

public class RulesRemoveCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Optional<Integer> theNum = ctx.<Integer> getOne("Rule Number");
		if(theNum.isPresent()) {
			int num = theNum.get(); //2
			int numberOfRules = ReadConfigRules.getNumRules(); //3
			for(int i = num; i < numberOfRules; i++) { //2 <= 3
				String temp = ReadConfigRules.getRule(i+1); //load 3
				ReadConfigRules.changeRule(i, temp); //set 3 value to 2
			}
			ReadConfigRules.removeRule(numberOfRules); // remove 3
			src.sendMessage(Text.of(TextColors.GOLD, "Rule Removed Sucessfully!"));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "RemoveRule", "removeRule", "Removerule", "removerule"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Displays Server Rules"))
			.permission("rrr.admin.rules.modify")
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("Rule Number"))))
			.executor(this)
			.build();
	}
	
}
