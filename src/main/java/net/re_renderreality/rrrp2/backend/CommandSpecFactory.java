package net.re_renderreality.rrrp2.backend;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandSpecFactory {

	//Change this number if you add a command
	private static final int numoftotalcmds = 17;
	
	/**
	 * @author EliteByte/Avarai
	 * @category CommandManagement
	 * @note Try not to touch this method unless you know what you're doing.
	 */
	public void buildCommandSpecs() {
		
		int numofcmd = -1;
		CommandSpec[] commandSpecs = new CommandSpec[numoftotalcmds];
		BaseCommand[] baseCommands = new BaseCommand[numoftotalcmds];
		String[][] aliases = new String[numoftotalcmds][];
		
		//EXAMPLE FOR A BASIC COMMAND || READ EACH PARAMETER DEFINITION IN "BaseCommand.Java"
		String[] helloAlias = Utilities.stringFormatter("Hello", "HelloWorld");
		numofcmd += 1;
		aliases[numofcmd] = helloAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("hello", "Hello Command", "rrrp2.hello", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		//END BASE COMMAND EXAMPLE
		
		String[] rrrpAlias = Utilities.stringFormatter("rrrp");
		numofcmd += 1;
		aliases[numofcmd] = rrrpAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("rrrp", "Re-RenderReality's Plugin Command List", "rrrp2.help", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] clearAlias = Utilities.stringFormatter("clearEntities");
		numofcmd += 1;
		aliases[numofcmd] = clearAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("clearEntities", "Clear Entities Command", "rrrp2.clearEntities", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.builder("Entity").toText())))
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] listAlias = Utilities.stringFormatter("listEntities");
		numofcmd += 1;
		aliases[numofcmd] = listAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("listEntities", "List Entities Command", "rrrp2.listEntities", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] tpsAlias = Utilities.stringFormatter("getTps");
		numofcmd += 1;
		aliases[numofcmd] = tpsAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("getTps", "Get TPS Command", "rrrp2.getTps", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] posAlias = Utilities.stringFormatter("getPos");
		numofcmd += 1;
		aliases[numofcmd] = posAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("getPos", "Get Position Command", "rrrp2.getPos", aliases[numofcmd], false);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] worldAlias = Utilities.stringFormatter("getWorld");
		numofcmd += 1;
		aliases[numofcmd] = worldAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("getWorld", "Get World Command", "rrrp2.getWorld", aliases[numofcmd], false);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] dimAlias = Utilities.stringFormatter("getDim");
		numofcmd += 1;
		aliases[numofcmd] = dimAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("getDim", "Get Dimension Command", "rrrp2.getDim", aliases[numofcmd], false);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] whoisAlias = Utilities.stringFormatter("whoIs");
		numofcmd += 1;
		aliases[numofcmd] = whoisAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("whoIs", "Who Is Command", "rrrp2.whoIs", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.builder("Player").toText())))
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] motdAlias = Utilities.stringFormatter("motd");
		numofcmd += 1;
		aliases[numofcmd] = motdAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("motd", "Motd Command", "rrrp2.motd", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] infoAlias = Utilities.stringFormatter("info");
		numofcmd += 1;
		aliases[numofcmd] = infoAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("info", "Get info Command", "rrrp2.info", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors( baseCommands[numofcmd])).build();
		
		String[] lastSeenAlias = Utilities.stringFormatter("seen");
		numofcmd += 1;
		aliases[numofcmd] = lastSeenAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("seen", "Last seen Command", "rrrp2.seen", aliases[numofcmd], true);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.builder("Player").toText())))
				.executor(new CommandExecutors( baseCommands[numofcmd])).build();
		
		String[] suicideAlias = Utilities.stringFormatter("suicide");
		numofcmd += 1;
		aliases[numofcmd] = suicideAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("suicide", "Commit suicide Command", "rrrp2.suicide", aliases[numofcmd], false);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] helpopAlias = Utilities.stringFormatter("helpop");
		numofcmd += 1;
		aliases[numofcmd] = helpopAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("helpop", "Helpop Command", "rrrp2.helpop", aliases[numofcmd], false);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.builder("Msg").toText())))
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] depthAlias = Utilities.stringFormatter("depth");
		numofcmd += 1;
		aliases[numofcmd] = depthAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("depth", "Get depth Command", "rrrp2.depth", aliases[numofcmd], false);
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors( baseCommands[numofcmd])).build();
		
		String[] rankperkAlias = Utilities.stringFormatter("rankperk");
		numofcmd += 1;
		aliases[numofcmd] = rankperkAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("rankperk", "Used for getting your rank perk", "rrrp2.rankperk", aliases[numofcmd], false, true, "Usage: </rankperk <RankName> > OR < /rankperk > (Gives you your current rank's perk).");
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		String[] rankperkallAlias = Utilities.stringFormatter("rankperkall");
		numofcmd += 1;
		aliases[numofcmd] = rankperkallAlias;
		baseCommands[numofcmd] = new BaseCommand();
		baseCommands[numofcmd].setInformation("rankperkall", "Used for giving everyone a rankperk", "rrrp2.rankperkall", aliases[numofcmd], true, true, "Usage: < /rankperkall <RankName> >");
		commandSpecs[numofcmd] = CommandSpec.builder()
				.description(Text.of(baseCommands[numofcmd].getDescription()))
				.permission(baseCommands[numofcmd].getPermission())
				.executor(new CommandExecutors(baseCommands[numofcmd])).build();
		
		// DO NOT TOUCH -- COMMAND REGISTRATION
		Utilities.baseCommands = baseCommands;
		for (int i = 0 ; i < numoftotalcmds; i++) {
				Utilities.getCommandManager().register(RRRP2.plugin, commandSpecs[i], aliases[i]);
				RRRP2.plugin.getLogger().info("[CommandFactory] The command: " + baseCommands[i].getName() + " is now registered.");
		}
	}
}