package net.re_renderreality.rrrp2.main;

import net.re_renderreality.rrrp2.cmd.BaseCommand;
import net.re_renderreality.rrrp2.cmd.CommandExecutors;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

@Plugin(id = "Re-RenderReality", name = "RRRP2", version = "0.1.0-ALPHA") 
public class RRRP2{
	
	@Inject 
	public Game game;
	@Inject 
	private PluginContainer container;
	@Inject 
	private Logger logger;
	public RRRP2 plugin;
	private Server server;
	
	/**
	 * @param event Listener for GameStartingServerEvent.
	 */
	@Listener 
	public void gameStarting(GameStartingServerEvent event) {
		plugin = this;
		server = game.getServer();
		registry.game = game;
		registry.logger = getLogger();
		commandSpecFactory();
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been initialized.");
	}
	
	/**
	 * @param event Listener for GameStoppingServerEvent.
	 */
	@Listener 
	public void gameStopping(GameStoppingServerEvent event) {
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been un-initialized.");
	}
	
	/**
	 * @author EliteByte/Avarai
	 * @category CommandManagement
	 */
	public void commandSpecFactory() {
		int numofcmds = 1;
		CommandSpec[] commandSpecs = new CommandSpec[numofcmds];
		
		String[] aliases = Utilities.stringFormatter("Hello", "HelloWorld");
		BaseCommand hello = new BaseCommand();
		hello.setInformation("Hello", "Hello Command", "rrrp2.hello", aliases);
		
		commandSpecs[0] = CommandSpec.builder().description(Text.of(hello.description)).permission(hello.permission).executor(new CommandExecutors(plugin, hello)).build();

		for (CommandSpec spec: commandSpecs) {
			game.getCommandManager().register(plugin, spec, aliases);
		}
	}
	
	/**
	 * @return Logger for logging status messages.
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return current Server Object.
	 */
	public Server getServer() {
		return server; 
	}
	
	/**
	 * @return current Game Object.
	 */
	public Game getGame() { 
		return game;
	}
}