package net.re_renderreality.rrrp2.cmd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;

import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.main.RRRP2;
import net.re_renderreality.rrrp2.main.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.slf4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.text.Text;

public class CommandExecutors implements CommandExecutor {

	@SuppressWarnings("unused")
	private final Logger logger;
	@SuppressWarnings("unused")
	private final Server server;
    @SuppressWarnings("unused")
	private String[] arguments;
	private final PlayerRegistry register;
	private final BaseCommand bc;

	public CommandExecutors ( BaseCommand bc) { //constructor
		logger = RRRP2.plugin.getLogger(); //sets logger
		server = RRRP2.plugin.getServer(); //sets current server obj
		register = RRRP2.plugin.getPlayerRegistry(); //sets current player registry
		this.bc = bc; //sets this command object to the base command
	}
	/**
	 * @author EliteByte/Avarai
	 * @param src Source of the command executor
	 * @param args Arguments of the command 
	 * @return Returns the CommandResult (Usually CommandResult.success())
	 */
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException { //called when a command is executed
		
		/*
		 * If the Command boolean isUniversal is True it can be used by any CommandSource
		 */
		if (bc.isUniversal()) { //command can come from anywhere
			switch (bc.getName()) {
				case "rrrp":
					@SuppressWarnings("unused")
					HelpCommand helpCommand = new HelpCommand(src); //creates a helpCommand obj and passes it the cmd source
					break;
				case "Hello":
					Utilities.broadcastMessage("HELLO BITCHES!"); //Broadcasts "HELLO BITCHES!" to server
					break;
				case "clearEntities":
					new ClearEntitiesCommand(src, args).run(); //Creates ClearEntities Obj passes args and src then executes
					break;
				case "listEntities":
					new ListEntitiesCommand(src).run();//Creates ListEntities Obj passes args and src then executes
					break;
				case "getTps":
					src.sendMessage(Text.of("Current server TPS: " + Utilities.getTps()));//sends tps numbers to the src
					break;
				case "whoIs":
					try { new WhoisCommand(src, args).run(); } catch (Exception e) { e.printStackTrace(); } //creates whois obj passes args and src cathches any errors
				case "motd":
					src.sendMessage(Registry.getServer().getMotd()); //sends motd to src
					break;
				case "info":
					String info = ""; 
					try { BufferedReader reader = new BufferedReader(new FileReader("rrr.info")); //prints rrr.info to src
					while (reader.ready()) { info+=reader.readLine(); } reader.close(); } catch (Exception e) { e.printStackTrace(); }
					src.sendMessage(Text.of(info));
					break;
				case "seen": //sends last seen time of target to src
					String name = args.<String>getOne("Player").get();
					src.sendMessage(Text.of(name + " was last seen on server at: " + register.getTime(register.getUuid(name))));
					break;	
				case "rankperkall": //gives a rank perk to everyone
					if (!args.toString().isEmpty())
						Utilities.broadcastMessage(args.toString() + " RANKPERKALL !!!!!!!"); //currently does nothing
					break;
			}
		} 
		
		/*
		 * Else if the command has a specific target specify it here
		 */
		else {
			if (Utilities.isPlayer(src)) { //command can only come from player
				switch (bc.getName()) {
				case "getPos": //gets users current position and sends it to them
					src.sendMessage(Text.of(Utilities.getPlayer(src.getName()).get().getLocation().getBlockPosition().add(0, 0, 1).toString().substring(1, Utilities.getPlayer(src.getName()).get().getLocation().getBlockPosition().toString().length()-1)));
					break;
				case "getWorld": //gets the world the user is in and sends it to them
					src.sendMessage(Text.of(Utilities.getPlayer(src.getName()).get().getWorld().getName()));
					break;
				case "getDim": //gets dimmension name that the user is in and sends it to them
					src.sendMessage(Text.of(Utilities.getPlayer(src.getName()).get().getWorld().getDimension().getName()));
					break;
				case "suicide": //user commits suicide
					MutableBoundedValue<Double> health = Utilities.getPlayer(src.getName()).get().getValue(Keys.HEALTH).get(); //gets users current health variable bounds
					health.set(health.getMinValue()); //sets it to its lower bound
					Utilities.getPlayer(src.getName()).get().offer(health); //offeres health and since its in bounds sets to 0
					Registry.getServer().getBroadcastChannel().send(Text.of(src.getName() + " committed suicide!")); //broadcasts to server
					break;
				case "helpop": //sends help request to administrators
					String msg = args.<String>getOne("Msg").get(); //Takes the command the src uses's argument and converts it to string
					Set<Context> contexts = null; //manually altars permissions
					for (Player p:Registry.getServer().getOnlinePlayers()) //checks who on the server has the permission "rrrp2.helpop"
						if (p.hasPermission(contexts, "rrrp2.helpop"))
							p.sendMessage(Text.of("Player, " + src.getName() + ", requests help via /helpop, players message: " + msg)); //sends them the message
					break;
				case "depth": //depth compared to water level
					int depth = Utilities.getPlayer(src.getName()).get().getLocation().getBlockY() - 63; //calculates depth of src.
					String relative = (depth > 0) ? "above" : "below";
					depth = (depth > 0) ? depth : -depth;
					src.sendMessage(Text.of("Your current depth " + relative + " sea level is: " + depth)); //prints current depth
					break;
				}
				
				
			} else if (Utilities.isConsole(src)) { //console user
				switch (bc.getName()) {
				
				}
							
			} else if (Utilities.isCommandBlock(src)) { //command block src
				switch (bc.getName()) {
					//TO FILL IF SPECIFIC TARGET NEEDED
				}
			}
		}
		return CommandResult.success(); //command was a sucess!!
	}
}