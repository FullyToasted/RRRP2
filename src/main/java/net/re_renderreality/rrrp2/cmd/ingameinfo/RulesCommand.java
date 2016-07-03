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
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

public class RulesCommand extends CommandExecutorBase{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/rules";
		description = "Displays sever rules";
		perm = "rrr.general.rules";
		useage = "/rules";
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
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.executor(this)
			.build();
	}
	
}
