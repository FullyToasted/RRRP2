package net.re_renderreality.rrrp2.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import net.re_renderreality.rrrp2.cmd.CommandSpecFactory;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.inject.Inject;

@Plugin(id = "Re-RenderReality", name = "RRRP2", version = "0.1.0-ALPHA") //defines plugin information
public class RRRP2 {
	
	@Inject 
	public Game game;
	@Inject 
	private PluginContainer container;
	@Inject 
	private Logger logger;
	public static RRRP2 plugin;
	private Server server;
	private PlayerRegistry players;
	

	
	public RRRP2 () { //constructor
		System.out.println("Break point");
		Thread.dumpStack();
	}
	
	/**
	 * @param event Listener for GameStartingServerEvent.
	 * @note Initialization of Plugin and Registry.
	 */
	@Listener //listens for event
	public void gameStarting(GameStartingServerEvent event) { //As the Server is starting
		plugin = this;   //initialize variable plugin as this object
		server = game.getServer(); //initialize variable server as current server object
		Registry.setGame(getGame()); //in the registry set game to the current game
		Registry.setLogger(getLogger()); //in the registry set logger to current logger
		players = new PlayerRegistry();  //creates a new playerRegistry object
		new CommandSpecFactory().buildCommandSpecs(); //builds the commands with a a commandSpecFactory object
		
		//To be replaced with local SQL database usage instead of text file
		try {
			BufferedReader reader = new BufferedReader(new FileReader("players.rrr")); //reads config file with the list of players and their last seen values into a hash table in players object
			while (reader.ready()) { //read until buffer is empty
				String line = reader.readLine(); //keeps reading next line
				players.addPlayer(line.substring(line.indexOf('/', line.lastIndexOf('/'))), line.substring(0, line.indexOf('/'))); //Reads player name adds to hash table
				players.addLastSeen(line.substring(line.indexOf('/', line.lastIndexOf('/'))), line.substring(line.lastIndexOf('/')+1, line.length())); //reads last seen and adds to hash table
			}
			reader.close(); //closes .rrr document
		} catch (Exception e) { getLogger().info("[ERROR] Something went wrong with RRRP2."); } //if above code fails throw exception
		//END TO BE REPLACED SECTION //replace with MYSQL
		
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been initialized."); //adds fully initialized to server stream
	}
	
	/**
	 * @param event Listener for "GameStoppingServerEvent" event.
	 */
	@Listener 
	public void gameStopping(GameStoppingServerEvent event) { //As server is stopping
		
		//To be replaced with local SQL database usage instead of text file
		try {
			new java.io.File("players.rrr").delete(); //deletes the old .rrr file
			BufferedWriter writer = new BufferedWriter(new FileWriter("players.rrr")); //creates a new .rrr file
			for (String uuid:players.getUuidSet()) { //for each uuid in hash table players
				writer.write(players.getPlayer(uuid) + "/" + uuid + "/" + players.getTime(uuid)); ///write their Usenames UUID and LastPlayed time to text document
				writer.newLine(); //newline
			}
			writer.close(); //closes completed .rrr document
		} catch (IOException e) { e.printStackTrace(); } //if it fails throw exception
		//END TO BE REPLACED SECTION
		
		getLogger().info(container.getName() + " v" + container.getVersion() + " has successfully been un-initialized."); //adds that plugin has been unloaded to input stream
	}
	
	/**
	 * @param event Listener for "ClientConnectionEvent.Login" event.
	 */
	@Listener
	public void playerJoined(ClientConnectionEvent.Login event) { //If player Joins
		players.addPlayer(event.getTargetUser().getUniqueId().toString(), event.getTargetUser().getName()); //add them to hash table
		String time = LocalTime.now().toString(); //gets current time
		time = LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.')); //gets current date and adds to time
		players.addLastSeen(event.getTargetUser().getUniqueId().toString(), time); //adds their joined time to hash table
	}
	
	/**
	 * @param event Listener for "ClientConnectionEvent.Disconnect" event.
	 */
	@Listener
	public void playerLeft(ClientConnectionEvent.Disconnect event) { //player disconnecets
		String time = LocalTime.now().toString(); //gets current time
		time = LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.')); //adds time to date
		players.addLastSeen(event.getTargetEntity().getUniqueId().toString(), time); //updates their hashtable value	
	}
	
	public String goml() { //Does Nothing?
		return ""; 
	}
	
	/**
	 * @return Logger for logging status messages.
	 */
	public Logger getLogger() { return logger; } //getter for the Logger Object

	/**
	 * @return current Server Object.
	 */
	public Server getServer() { return server; }//getter for the Server Object
	
	/**
	 * @return current Game Object.
	 */
	public Game getGame() { return game; }//getter for the Game Object
	
	/**
	 * @return get PlayerRegistry Object.
	 */
	public PlayerRegistry getPlayerRegistry() { return players; }//getter for the PlayerRegiistry Object
}