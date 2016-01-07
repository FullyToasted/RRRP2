package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.RRRP2;
import net.re_renderreality.rrrp2.main.registry;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandSpecFactory {

	RRRP2 plugin;
	
	public CommandSpecFactory(RRRP2 plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * @author EliteByte/Avarai
	 * @category CommandManagement
	 * @note Try to to touch this method unless you know what you're doing.
	 */
	public void commandSpecFactory() {
		int numofcmds = 1;
		CommandSpec[] commandSpecs = new CommandSpec[numofcmds];
		BaseCommand[] baseCommands = new BaseCommand[numofcmds];
		
		//EXAMPLE FOR A BASIC COMMAND || READ EACH PARAMETER DEFINITION 
		String[] aliases = Utilities.stringFormatter("Hello", "HelloWorld");
		baseCommands[0] = new BaseCommand();
		baseCommands[0].setInformation("Hello", "Hello Command", "rrrp2.hello", aliases, true);
		commandSpecs[0] = CommandSpec.builder()
				.description(Text.of(baseCommands[0].getDescription()))
				.permission(baseCommands[0].getPermission())
				.executor(new CommandExecutors(plugin, baseCommands[0])).build();
		
		Utilities.baseCommands = baseCommands;
		for (CommandSpec spec : commandSpecs) {
			registry.getGame().getCommandManager().register(plugin, spec, aliases);
		}
	}
}
