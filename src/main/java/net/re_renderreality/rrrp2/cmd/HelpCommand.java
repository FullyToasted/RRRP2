package net.re_renderreality.rrrp2.cmd;

import java.util.ArrayList;
import java.util.List;

import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class HelpCommand {

	
	CommandSource src;
	Utilities utils = new Utilities();
	List<Text> contents = new ArrayList<>();
	
	public HelpCommand(CommandSource src) {
		this.src = src;
		BaseCommand[] baseCommands = Utilities.baseCommands;
		
		for (int i = 0 ; i <= baseCommands.length ; i++) {
		contents.add(Text.of(baseCommands[i].getName() + " " + baseCommands[i].getDescription()));
		}
	}
	
	
	
}
