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

public class AnnouncementsRemoveCommand extends CommandExecutorBase {
	
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
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Displays Server Rules"))
			.permission("rrr.admin.announcements.remove")
			.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("Announcement Number"))))
			.executor(this)
			.build();
	}
	
}