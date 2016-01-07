package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.RRRP2;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.slf4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CommandExecutors implements CommandExecutor {

	@SuppressWarnings("unused") private final Logger logger;
	@SuppressWarnings("unused") private final Server server;
	@SuppressWarnings("unused") private String[] arguments;
	BaseCommand bc;

	public CommandExecutors (RRRP2 plugin, BaseCommand bc) {
		logger = plugin.getLogger();
		server = plugin.getServer();
		this.bc = bc;
	}
	/**
	 * @author EliteByte
	 * @param src Source of the command executor
	 * @param args Arguements of the command 
	 * @return Returns the CommandResult (Usually CommandResult.success())
	 */
	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
	
	throws CommandException {
		
		/*
		 * If the Command isUniversal it can be used by any CommandSource
		 */
		if (bc.isUniversal()) {
			switch (bc.getName()) {
			
				case "rrrp":
					HelpCommand helpCommand = new HelpCommand(src);
					src.sendMessage(Text.of(helpCommand));
					break;
			
				case "Hello":
					Utilities.broadcastMessage("HELLO BITCHES!");
					break;
				
			
			}
		} 
		/*
		 * Else if the command has a specific target specify it here
		 */
		else {
			if (src instanceof Player) {
				switch (bc.getName()) {
					
				}
			} else if (src instanceof ConsoleSource) {
				switch (bc.getName()) {
				
				}
			} else if (src instanceof CommandBlockSource) {
				switch (bc.getName()) {
				
				}
			}
		}
		return CommandResult.success();
	}
}


