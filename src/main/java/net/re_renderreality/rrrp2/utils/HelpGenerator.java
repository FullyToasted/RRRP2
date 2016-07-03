package net.re_renderreality.rrrp2.utils;

import java.util.HashMap;
import java.util.HashSet;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.backend.CommandLoader;
import net.re_renderreality.rrrp2.database.Registry;

public class HelpGenerator {
	public HashMap<String, Text> admin;
	public HashMap<String, Text> cheat;
	public HashMap<String, Text> general;
	public HashMap<String, Text> teleport;
	public HashMap<String, Text> misc;
	public HashMap<String, Text> all;
	//creates a HelpGenerator instance
	private static HelpGenerator help = new HelpGenerator();
	
	private static HashSet<? extends CommandExecutorBase> commands = CommandLoader.getLoadedCommands();
	
	private HelpGenerator()	{
		;
	}
	//Dont Change Spacing on first two Text lines of each function
	//Keep space-Text at end of command list
	
	
	/**
	 * populates the help generator object
	 */
	public void populate() {
		commands.forEach(cmd -> {
			String name = cmd.getName();
			String perm = cmd.getPerm();
			String useage = cmd.getUseage();
			String description = cmd.getDescription();
			String note = cmd.getNotes();
			
			Text t = Text.builder().append(Text.of(TextColors.GOLD, name)).onHover(TextActions.showText(Text.of(TextColors.DARK_GRAY, description))).onClick(TextActions.executeCallback( c -> { 
				if(note == null) {
					c.sendMessage(Text.of(TextColors.GOLD, "Command Name: ", TextColors.DARK_GRAY, name,
										TextColors.GOLD, "\nDescription: ", TextColors.DARK_GRAY, description,
										TextColors.GOLD, "\nPermission: ", TextColors.DARK_GRAY, perm,
										TextColors.GOLD, "\nUseage: ", TextColors.DARK_GRAY, useage,
										TextColors.GOLD, "\nCommand Name: ", TextColors.DARK_GRAY, name));
				} else {
					c.sendMessage(Text.of(TextColors.GOLD, "Command Name: ", TextColors.DARK_GRAY, name,
							TextColors.GOLD, "\nDescription: ", TextColors.DARK_GRAY, description,
							TextColors.GOLD, "\nPermission: ", TextColors.DARK_GRAY, perm,
							TextColors.GOLD, "\nUseage: ", TextColors.DARK_GRAY, useage,
							TextColors.GOLD, "\nCommand Name: ", TextColors.DARK_GRAY, name,
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
