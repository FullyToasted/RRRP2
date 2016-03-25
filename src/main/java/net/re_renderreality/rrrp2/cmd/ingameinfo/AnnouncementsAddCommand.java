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

public class AnnouncementsAddCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Optional<String> theAnnouncement = ctx.<String> getOne("Announcement");
		if(theAnnouncement.isPresent()) {
			String announcement = theAnnouncement.get();
			ReadConfigAnnouncements.addAnnouncement(announcement);
			src.sendMessage(Text.of(TextColors.GOLD, "Announcement Added Sucessfully!"));
		}
		return CommandResult.empty();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "AddAnnouncement", "addAnnouncement", "Addannouncement", "addannouncement"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Displays Server Rules"))
			.permission("rrr.admin.announcements.add")
			.arguments(GenericArguments.remainingJoinedStrings(Text.of("Announcement")))
			.executor(this)
			.build();
	}
	
}
