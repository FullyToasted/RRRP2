package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class GetTpsCommand {
	
	private CommandSource source;
	
	public GetTpsCommand(CommandSource src) { source = src; }
	
	public void run() {
		source.sendMessage(Text.of("Current server TPS: " + Utilities.getTps()));
	}	
}