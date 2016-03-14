package net.re_renderreality.rrrp2.cmd;

import java.util.ArrayList;
import java.util.List;
import net.re_renderreality.rrrp2.utils.Utilities;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class HelpCommand {

	
	private List<Text> contents;
	
	public HelpCommand(CommandSource src) {
		
		contents = new ArrayList<>(); //sets contents to be a an Array List of Text Objects
		
		BaseCommand[] baseCommands = Utilities.baseCommands; //copies Base COmmands here
		
		for (int i = 0 ; i < baseCommands.length ; i++) { //loops through every command
			contents.add(Text.of(baseCommands[i].getName() + " " + baseCommands[i].getDescription())); //adds the command name to list
		}
		
		Utilities.getPaginationService().builder() //uses pages to output commands adds them to sponges commands
        	.title(Text.of("RRRP2 Commands"))
        	.contents(contents)
        	.header(Text.of("Re-RenderRealityPlugin 2"))
        	.footer(Text.of("Thank you for choosing Re-RenderReality"))
        	.sendTo(src);
	}	
}