package net.re_renderreality.rrrp2.cmd;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.utils.HelpGenerator;
import net.re_renderreality.rrrp2.utils.Utilities;

public class HelpCommand extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<String> s = ctx.<String> getOne("SubDirectory");
		if (s.isPresent()) {
			if (s.get() == "admin") {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getAdmin())
		        	.header(Text.of("Re-RenderRealityPlugin 2"))
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.padding(Text.of(""))
		        	.linesPerPage(7)
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == "cheat") {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getCheat())
		        	.header(Text.of("Re-RenderRealityPlugin 2"))
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.padding(Text.of(""))
		        	.linesPerPage(7)
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == "general") {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getGeneral())
		        	.header(Text.of("Re-RenderRealityPlugin 2"))
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.padding(Text.of(""))
		        	.linesPerPage(7)
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == "teleport") {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getTeleport())
		        	.header(Text.of("Re-RenderRealityPlugin 2"))
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.padding(Text.of(""))
		        	.linesPerPage(7)
	        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == "various") {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getVarious())
		        	.header(Text.of("Re-RenderRealityPlugin 2"))
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.padding(Text.of(""))
		        	.linesPerPage(7)
		        	.sendTo(src);
				return CommandResult.success();
			} else {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getAll())
		        	.header(Text.of("Re-RenderRealityPlugin 2"))
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.padding(Text.of(""))
		        	.linesPerPage(7)
		        	.sendTo(src);
				return CommandResult.success();
			}
		} else {
			Utilities.getPaginationService().builder()
	        	.title(Text.of("RRRP2 Commands"))
	        	.contents(HelpGenerator.getAll())
	        	.header(Text.of("Re-RenderRealityPlugin 2"))
	        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
	        	.sendTo(src);
			return CommandResult.success();
		}
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "RRR", "rrr", "RRRcommands" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder().description(Text.of("Displays a list of commands for the user")).permission("rrrp2.general.help")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("SubDirectory")))))
				.executor(this).build();
	}
}