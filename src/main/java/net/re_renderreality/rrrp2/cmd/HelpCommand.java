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
			if (s.get().equals("admin")) {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().admin)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get().equals("cheat")) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().cheat)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get().equals("general")) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().general)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get().equals("teleport")) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().teleport)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get().equals("various")) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().various)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().all)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			}
		} else {
			Utilities.getPaginationService().builder()
	        	.title(Text.of("RRRP2 Commands"))
	        	.contents(HelpGenerator.getHelp().all)
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
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("SubDirectory")))))
				.executor(this).build();
	}
}