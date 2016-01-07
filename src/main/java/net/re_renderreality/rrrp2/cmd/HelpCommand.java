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
		
		Utilities.getPaginationService().builder()
        .title(Text.of("RRRP2 Commands"))
        .contents(contents)
        .header(Text.of("Re-RenderRealityPlugin 2"))
        .footer(Text.of("Thank you for choosing Re-RenderReality"))
        .paddingString("-")
        .sendTo(src);
	}
	
	
	
}
