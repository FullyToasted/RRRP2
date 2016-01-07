package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.RRRP2;
import net.re_renderreality.rrrp2.main.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.args.GenericArguments;

public class CommandSpecFactory {

	RRRP2 plugin;
	
	public CommandSpecFactory() {
		this.plugin = Registry.getPlugin();
	}
	
	/**
	 * @author EliteByte/Avarai
	 * @category CommandManagement
	 * @note Try not to touch this method unless you know what you're doing.
	 */
	public void buildCommandSpecs() {
		
		//Change this number if you add a command
		final int numoftotalcmds = 3;
		
		int numofcmd = -1;
		CommandSpec[] commandSpecs = new CommandSpec[numoftotalcmds];
		BaseCommand[] baseCommands = new BaseCommand[numoftotalcmds];
		String[][] aliases = new String[numoftotalcmds][];
		
		//EXAMPLE FOR A BASIC COMMAND || READ EACH PARAMETER DEFINITION 
		numofcmd += 1;
		String[] helloAlias = Utilities.stringFormatter("Hello", "HelloWorld");
		aliases[numofcmd] = helloAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("Hello", "Hello Command", "rrrp2.hello", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(plugin, baseCommands[numofcmd])).build();
		
		String[] rrrpAlias = Utilities.stringFormatter("rrrp");
		numofcmd += 1;
		aliases[numofcmd] = rrrpAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("rrrp", "Re-RenderReality's Plugin Command List", "rrrp2.help", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(plugin, baseCommands[numofcmd])).build();
		
		String[] clearAlias = Utilities.stringFormatter("clearEntities");
		numofcmd += 1;
		aliases[numofcmd] = clearAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("clearEntities", "Clear Entities Command", "rrrp2.clearEntities", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.builder("Entity").toText())))
				.executor(new CommandExecutors(plugin, baseCommands[numofcmd])).build();
		
		
		// DO NOT TOUCH
		Utilities.baseCommands = baseCommands;
		for (int i = 0 ; i < numoftotalcmds; i++) {
				Utilities.getCommandManager().register(plugin, commandSpecs[i], aliases[i]);
				plugin.getLogger().info("[CommandFactory] The command: " + baseCommands[i].getName() + " is now registered.");
		}
	}
}
