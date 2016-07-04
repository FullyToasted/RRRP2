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

public class RulesModifyCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/modifyrule";
		description = "Modify an existing server rule";
		perm = "rrr.admin.rules";
		useage = "/modifyrule <ID> <rule>";
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
	
	//Modify an existing rule
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
	
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
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("ID"))),GenericArguments.remainingJoinedStrings(Text.of("Rule")))
			.executor(this)
			.build();
	}
	
}
