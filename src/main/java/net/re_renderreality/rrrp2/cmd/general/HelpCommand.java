package net.re_renderreality.rrrp2.cmd.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Server;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.HelpGenerator;
import net.re_renderreality.rrrp2.utils.Utilities;

public class HelpCommand extends CommandExecutorBase {
	
	/**
	 * Lists a Pagination Generated help page for either all commands or the specified subcategory @TODO NEEDS to be completely rewritten
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<Integer> s = ctx.<Integer> getOne("SubDirectory");
		if (s.isPresent()) {
			if (s.get() == 1) {
				Utilities.getPaginationService().builder()
		        	.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().admin)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 2) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().cheat)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 3) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().general)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 4) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().teleport)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 5) {
				Utilities.getPaginationService().builder()
					.title(Text.of("RRRP2 Commands"))
		        	.contents(HelpGenerator.getHelp().misc)
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
	
	private ArrayList<Text> admin() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of(TextColors.GOLD, "                              Admin Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		array.add(Text.of("/ClearEntities: Clears all the entities of the specified type"));
		array.add(Text.of("/ListEntities: List all entities currently loaded"));
		array.add(Text.of("/TPS: Show useful information about the server performance"));
		array.add(Text.of("/Whois: Gives all known information about player"));
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> cheat() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                            Cheater Commands =)"));
		array.add(Text.of("-----------------------------------------------------"));
		array.add(Text.of("/Heal: Heals self or can heal another player"));
		array.add(Text.of("/Fly: Enable fly mode for self or another player"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> general() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                            General Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		array.add(Text.of("/Help: Help Command"));
		
		
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> teleport() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                             Teleport Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	private ArrayList<Text> misc() {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                              Other Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		
		array.add(Text.of(" "));
		return array;
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "RRR", "rrr", "RRRcommands" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Admin", 1);
		map.put("Cheat", 2);
		map.put("General", 3);
		map.put("Teleport", 4);
		map.put("Various", 5);
		return CommandSpec.builder()
				.description(Text.of("Displays a list of commands for the user"))
				.permission("rrr.general.help")
				.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("SubDirectory"), map)))
				.executor(this).build();
	}
}