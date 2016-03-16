package net.re_renderreality.rrrp2.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class HelpCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		/*
		contents = new ArrayList<>();
		
		BaseCommand[] baseCommands = Utilities.baseCommands;
		
		for (int i = 0 ; i < baseCommands.length ; i++) {
			contents.add(Text.of(baseCommands[i].getName() + ": " + baseCommands[i].getDescription()));
		}
		
		Utilities.getPaginationService().builder()
        	.title(Text.of("RRRP2 Commands"))
        	.contents(contents)
        	.header(Text.of("Re-RenderRealityPlugin 2"))
        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
        	.padding(Text.of(""))
        	.linesPerPage(7)
        	.sendTo(src);
        	*/
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "RRR", "rrr", "RRRcommands" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Help Command"))
			.permission("rrr.basic.help")
			.executor(this)
			.build();
	}
}