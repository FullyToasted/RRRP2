package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.main.RRRP2;
import net.re_renderreality.rrrp2.main.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;

import org.slf4j.Logger;

import org.spongepowered.api.Server;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.text.Text;

public class CommandExecutors implements CommandExecutor {

	@SuppressWarnings("unused") private final Logger logger;
	@SuppressWarnings("unused") private final Server server;
	@SuppressWarnings("unused") private String[] arguments;
	private PlayerRegistry register;
	private BaseCommand bc;

	public CommandExecutors (RRRP2 plugin, BaseCommand bc) {
		logger = plugin.getLogger();
		server = plugin.getServer();
		register = plugin.getPlayerRegistry();
		this.bc = bc;
	}
	/**
	 * @author EliteByte/Avarai
	 * @param src Source of the command executor
	 * @param args Arguments of the command 
	 * @return Returns the CommandResult (Usually CommandResult.success())
	 */
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		/*
		 * If the Command boolean isUniversal is True it can be used by any CommandSource
		 */
		if (bc.isUniversal()) {
			switch (bc.getName()) {
				case "rrrp":
					@SuppressWarnings("unused")
					HelpCommand helpCommand = new HelpCommand(src);
					break;
				case "Hello":
					Utilities.broadcastMessage("HELLO BITCHES!");
					break;
				case "clearEntities":
					new ClearEntitiesCommand(src, args).run();
					break;
				case "listEntities":
					new ListEntitiesCommand(src).run();
					break;
				case "getTps":
					src.sendMessage(Text.of("Current server TPS: " + Utilities.getTps()));
					break;
				case "whoIs":
					try { new WhoisCommand(src, args).run(); } catch (Exception e) { e.printStackTrace(); }
					break;
				case "motd":
					src.sendMessage(Registry.getServer().getMotd());
					break;
				case "info":
					String info = "";
					try { BufferedReader reader = new BufferedReader(new FileReader("rrr.info")); 
					while (reader.ready()) { info+=reader.readLine(); } reader.close(); } catch (Exception e) { e.printStackTrace(); }
					src.sendMessage(Text.of(info));
					break;
				case "seen":
					String name = args.<String>getOne("Player").get();
					src.sendMessage(Text.of(name + " was last seen on server at: " + register.getTime(register.getUuid(name))));
					break;
			}
		} 
		
		/*
		 * Else if the command has a specific target specify it here
		 */
		else {
			if (Utilities.isPlayer(src)) {
				switch (bc.getName()) {
				case "getPos":
					src.sendMessage(Text.of(Utilities.getPlayer(src.getName()).get().getLocation().getBlockPosition().add(0, 0, 1).toString().substring(1, Utilities.getPlayer(src.getName()).get().getLocation().getBlockPosition().toString().length()-1)));
					break;
				case "getWorld":
					src.sendMessage(Text.of(Utilities.getPlayer(src.getName()).get().getWorld().getName()));
					break;
				case "getDim":
					src.sendMessage(Text.of(Utilities.getPlayer(src.getName()).get().getWorld().getDimension().getName()));
					break;
				case "suicide":
					MutableBoundedValue<Double> health = Utilities.getPlayer(src.getName()).get().getValue(Keys.HEALTH).get();
					health.set(health.getMinValue());
					Utilities.getPlayer(src.getName()).get().offer(health);
					Registry.getServer().getBroadcastChannel().send(Text.of(src.getName() + " committed suicide!"));
					break;
				}
			} else if (Utilities.isConsole(src)) {
				switch (bc.getName()) {
					//TO FILL IF SPECIFIC TARGET NEEDED
				}
			} else if (Utilities.isCommandBlock(src)) {
				switch (bc.getName()) {
					//TO FILL IF SPECIFIC TARGET NEEDED
				}
			}
		}
		return CommandResult.success();	
	}
}