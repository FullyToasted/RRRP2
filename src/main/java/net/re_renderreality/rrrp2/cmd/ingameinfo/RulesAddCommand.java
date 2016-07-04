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

public class RulesAddCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/addrule";
		description = "Add a server rule";
		perm = "rrr.admin.rules";
		useage = "/addrule <rule>";
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
	
	//Add a rule to the list
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
	
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
			.arguments(GenericArguments.remainingJoinedStrings(Text.of("Rule")))
			.executor(this)
			.build();
	}
	
}