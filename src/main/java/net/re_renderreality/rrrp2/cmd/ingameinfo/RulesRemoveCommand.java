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
import net.re_renderreality.rrrp2.database.Registry;

public class RulesRemoveCommand extends CommandExecutorBase{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/removerule";
		description = "Removes a server rule";
		perm = "rrr.admin.rules";
		useage = "/removerule <ID>";
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
	
	//remove an existing rule
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
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("Rule Number"))))
			.executor(this)
			.build();
	}
	
}
