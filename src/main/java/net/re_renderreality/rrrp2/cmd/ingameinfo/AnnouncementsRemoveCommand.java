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

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigAnnouncements;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class AnnouncementsRemoveCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/removeannouncement";
		description = "Request a player to teleort to your location";
		perm = "rrr.admin.announcement";
		useage = "/removeannouncement <ID>";
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
	
	//remove an exsisting announcement
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Optional<Integer> theNum = ctx.<Integer> getOne("Announcement Number");
		if(theNum.isPresent()) {
			int num = theNum.get(); 
			int numberOfAnnouncements = ReadConfigAnnouncements.getNumAnnouncements(); 
			for(int i = num; i < numberOfAnnouncements; i++) {
				String temp = ReadConfigAnnouncements.getAnnouncement(i+1); 
				ReadConfigAnnouncements.changeAnnouncement(i, temp);
			}
			ReadConfigAnnouncements.removeAnnouncement(numberOfAnnouncements);
			src.sendMessage(Text.of(TextColors.GOLD, "Rule Removed Sucessfully!"));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "RemoveAnnouncement", "removeAnnouncement", "Removeannouncement", "removeannouncement"};
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
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("Announcement Number"))))
			.executor(this)
			.build();
	}
	
}