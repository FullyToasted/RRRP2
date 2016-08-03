package net.re_renderreality.rrrp2.utils;

import java.util.ArrayList;
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
	public HashMap<String, ArrayList<Text>> admin = new HashMap<String,ArrayList<Text>>();
	public HashMap<String, ArrayList<Text>> cheat = new HashMap<String,ArrayList<Text>>();
	public HashMap<String, ArrayList<Text>> general = new HashMap<String,ArrayList<Text>>();
	public HashMap<String, ArrayList<Text>> teleport = new HashMap<String,ArrayList<Text>>();
	public HashMap<String, ArrayList<Text>> misc = new HashMap<String,ArrayList<Text>>();
	public HashMap<String, ArrayList<Text>> all = new HashMap<String,ArrayList<Text>>();
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
				if(!admin.containsKey(perm)) {
					ArrayList<Text> temp = new ArrayList<Text>();
					temp.add(t);
					admin.put(perm, temp);
				} else {
					ArrayList<Text> temp = admin.get(perm);
					admin.remove(perm);
					temp.add(t);					
					admin.put(perm, temp);
				}
			} else if (cmd.getHelpCategory() == Registry.helpCategory.Cheater) {
				if(!cheat.containsKey(perm)) {
					ArrayList<Text> temp = new ArrayList<Text>();
					temp.add(t);
					cheat.put(perm, temp);
				} else {
					ArrayList<Text> temp = cheat.get(perm);
					cheat.remove(perm);
					temp.add(t);					
					cheat.put(perm, temp);
				}
			} else if (cmd.getHelpCategory() == Registry.helpCategory.General) {
				if(!general.containsKey(perm)) {
					ArrayList<Text> temp = new ArrayList<Text>();
					temp.add(t);
					general.put(perm, temp);
				} else {
					ArrayList<Text> temp = general.get(perm);
					general.remove(perm);
					temp.add(t);					
					general.put(perm, temp);
				}
			} else if (cmd.getHelpCategory() == Registry.helpCategory.Teleport) {
				if(!teleport.containsKey(perm)) {
					ArrayList<Text> temp = new ArrayList<Text>();
					temp.add(t);
					teleport.put(perm, temp);
				} else {
					ArrayList<Text> temp = teleport.get(perm);
					teleport.remove(perm);
					temp.add(t);					
					teleport.put(perm, temp);
				}
			} else if (cmd.getHelpCategory() == Registry.helpCategory.Misc) {
				if(!misc.containsKey(perm)) {
					ArrayList<Text> temp = new ArrayList<Text>();
					temp.add(t);
					misc.put(perm , temp);
				} else {
					ArrayList<Text> temp = misc.get(perm);
					misc.remove(perm);
					temp.add(t);					
					misc.put(perm, temp);
				}
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
