package net.re_renderreality.rrrp2.cmd.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
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
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	private Iterable<Text> admin;
	private Iterable<Text> cheat;
	private Iterable<Text> general;
	private Iterable<Text> teleport;
	private Iterable<Text> misc;
	private Iterable<Text> all;
	
	private HelpGenerator help = HelpGenerator.getHelp();
	
	protected void setLocalVariables() {
		name = "/rrr";
		description = "Displays the help menu you are viewing";
		perm = "rrr.general.help";
		useage = "/rrr (subdirectory)";
		notes = "options: General, Admin, Teleport, Cheats";
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
	
	/**
	 * Lists a Pagination Generated help page for either all commands or the specified subcategory @TODO NEEDS to be completely rewritten
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Logger l = Registry.getLogger();
		setLocalVariables();
		admin = admin(src);
		cheat = cheat(src);
		general = general(src);
		teleport = teleport(src);
		misc = misc(src);
		all = all(src);
		
		Optional<Integer> s = ctx.<Integer> getOne("SubDirectory");
		if (s.isPresent()) {
			if (s.get() == 1) { //admin
				Utilities.getPaginationService().builder()
		        	.title(Text.of(TextColors.AQUA, "RRR Commands"))
		        	.contents(admin)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 2) { //cheat
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.AQUA, "RRR Commands"))
		        	.contents(cheat)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 3) { //general
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.AQUA, "RRR Commands"))
		        	.contents(general)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 4) { //teleport
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.AQUA, "RRR Commands"))
		        	.contents(teleport)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else if (s.get() == 5) { //misc
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.AQUA, "RRR Commands"))
		        	.contents(misc)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			} else { //all
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.AQUA, "RRR Commands"))
		        	.contents(all)
		        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
		        	.sendTo(src);
				return CommandResult.success();
			}
		} else {
			Utilities.getPaginationService().builder()
	        	.title(Text.of(TextColors.AQUA, "RRR Commands"))
	        	.contents(all)
	        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
	        	.sendTo(src);
			return CommandResult.success();
		}
	}
	
	private ArrayList<Text> admin(CommandSource src) {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of(TextColors.GOLD, "                              Admin Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		for(String s : help.admin.keySet()) {
			if(src.hasPermission(s)) {
				array.add(help.admin.get(s));
			}
		}
		if(array.size() == 2) {
			ArrayList<Text> arrays = new ArrayList<Text>();
			return arrays;
		} else {
			return array;
		}
	}
	
	private ArrayList<Text> cheat(CommandSource src) {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of(TextColors.GOLD, "                            Cheater Commands =)"));
		array.add(Text.of("-----------------------------------------------------"));
		for(String s : help.cheat.keySet()) {
			if(src.hasPermission(s)) {
				array.add(help.cheat.get(s));
			}
		}
		if(array.size() == 2) {
			ArrayList<Text> arrays = new ArrayList<Text>();
			return arrays;
		} else {
			return array;
		}
	}
	
	private ArrayList<Text> general(CommandSource src) {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of(TextColors.GOLD, "                            General Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		for(String s : help.general.keySet()) {
			if(src.hasPermission(s)) {
				array.add(help.general.get(s));
			}
		}
		if(array.size() == 2) {
			ArrayList<Text> arrays = new ArrayList<Text>();
			return arrays;
		} else {
			return array;
		}
	}
	
	private ArrayList<Text> teleport(CommandSource src) {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of(TextColors.GOLD, "                             Teleport Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		for(String s : help.teleport.keySet()) {
			if(src.hasPermission(s)) {
				array.add(help.teleport.get(s));
			}
		}
		if(array.size() == 2) {
			ArrayList<Text> arrays = new ArrayList<Text>();
			return arrays;
		} else {
			return array;
		}
	}
	
	private ArrayList<Text> misc(CommandSource src) {
		ArrayList<Text> array = new ArrayList<Text>();
		array.add(Text.of("                              Other Commands"));
		array.add(Text.of("-----------------------------------------------------"));
		for(String s : help.misc.keySet()) {
			if(src.hasPermission(s)) {
				array.add(help.misc.get(s));
			}
		}
		if(array.size() == 2) {
			ArrayList<Text> arrays = new ArrayList<Text>();
			return arrays;
		} else {
			return array;
		}
	}
	
	private ArrayList<Text> all(CommandSource src) {
		ArrayList<Text> array = new ArrayList<Text>();
		array.addAll(admin(src));
		array.addAll(cheat(src));
		array.addAll(general(src));
		array.addAll(teleport(src));
		array.addAll(misc(src));
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
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("SubDirectory"), map)))
				.executor(this).build();
	}
}