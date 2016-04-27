package net.re_renderreality.rrrp2.cmd.ingameinfo;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigAnnouncements;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.utils.Utilities;

public class AnnouncementsCommand extends CommandExecutorBase{
	
	//send announcements to player
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
	
		Utilities.getPaginationService().builder()
	    	.title(ReadConfigAnnouncements.getHeader())
	    	.contents(ReadConfigAnnouncements.getAnnouncements())
	    	.footer(ReadConfigAnnouncements.getFooter())
	    	.sendTo(src);
		return CommandResult.success();
	}
		
		
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "Announcements", "announcements"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Displays Server Announcements"))
			.permission("rrr.general.announcements")
			.executor(this)
			.build();
	}
	
}
