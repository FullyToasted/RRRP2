package net.re_renderreality.rrrp2.utils;

import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.backend.CommandLoader;
import net.re_renderreality.rrrp2.database.Registry;

public class HelpGenerator {
	public HashMap<String, Text> admin = new HashMap<String,Text>();
	public HashMap<String, Text> cheat = new HashMap<String,Text>();
	public HashMap<String, Text> general = new HashMap<String,Text>();
	public HashMap<String, Text> teleport = new HashMap<String,Text>();
	public HashMap<String, Text> misc = new HashMap<String,Text>();
	public HashMap<String, Text> all = new HashMap<String,Text>();
	//creates a HelpGenerator instance
	private static HelpGenerator help = new HelpGenerator();
	
	private static HashSet<? extends CommandExecutorBase> commands;
	
	private HelpGenerator()	{
		;
	}
	
	/**
	 * populates the help generator object
	 */
	public void populate() {
		Logger l = Registry.getLogger();
		commands = CommandLoader.getLoadedCommands();
		commands.forEach(cmd -> {
			String name = cmd.getName();
			String perm = cmd.getPerm();
			String useage = cmd.getUseage();
			String description = cmd.getDescription();
			String note = cmd.getNotes();
			
			Text t = Text.builder().append(Text.of(TextColors.GREEN, name)).onHover(TextActions.showText(Text.of(TextColors.GRAY, description))).onClick(TextActions.executeCallback( c -> { 
				if(note == null) {
					c.sendMessage(Text.of(TextColors.GOLD, "Command Name: ", TextColors.GRAY, name,
										TextColors.GOLD, "\nDescription: ", TextColors.GRAY, description,
										TextColors.GOLD, "\nPermission: ", TextColors.GRAY, perm,
										TextColors.GOLD, "\nUseage: ", TextColors.GRAY, useage,
										TextColors.GOLD, "\nCommand Name: ", TextColors.GRAY, name));
				} else {
					c.sendMessage(Text.of(TextColors.GOLD, "Command Name: ", TextColors.GRAY, name,
							TextColors.GOLD, "\nDescription: ", TextColors.GRAY, description,
							TextColors.GOLD, "\nPermission: ", TextColors.GRAY, perm,
							TextColors.GOLD, "\nUseage: ", TextColors.GRAY, useage,
							TextColors.GOLD, "\nCommand Name: ", TextColors.GRAY, name,
							TextColors.RED, "\nNOTE: " + note));
				}
			})).build();
			
			if(cmd.getHelpCategory() == Registry.helpCategory.Admin) {
				admin.put(perm, t);
			} else if (cmd.getHelpCategory() == Registry.helpCategory.Cheater) {
				cheat.put(perm, t);
			} else if (cmd.getHelpCategory() == Registry.helpCategory.General) {
				general.put(perm, t);
			} else if (cmd.getHelpCategory() == Registry.helpCategory.Teleport) {
				teleport.put(perm, t);
			} else if (cmd.getHelpCategory() == Registry.helpCategory.Misc) {
				misc.put(perm, t);
			} 		
		});
	}
	
	/**
	 * @return returns the populated helpGenerator object
	 */
	public static HelpGenerator getHelp() {
		return help;
	}
}
