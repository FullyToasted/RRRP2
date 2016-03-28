package net.re_renderreality.rrrp2.database;


import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.service.sql.SqlService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.utils.Utilities;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigDatabase;
import net.re_renderreality.rrrp2.database.core.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

public class Database {
	
	public static SqlService sql;
	public static DataSource datasource;
	
	public static List<String> queue = new ArrayList<String>();
	
	public static void setup(Game game) {

		try {
			
			sql = game.getServiceManager().provide(SqlService.class).get();
			
			//creates a .db file if MySQL file is not provieded
			if(!ReadConfigDatabase.useMySQL()) {
				
		    	File folder = new File("config/rrr.commands/data");
		    	if(!folder.exists()) 
		    		folder.mkdir();
		    	try {
			    	File db = new File("config/rrr.commands/data/RRR.db");	
			    	if(db.exists())
			    		db.createNewFile();
		    	} catch (IOException e)	{
		    		e.printStackTrace();
		    	}
		    	datasource = sql.getDataSource("jdbc:sqlite:config/rrr.commands/data/RRR.db");
				
			}
			else {
				//Gets MySQL data from the congig file
				String host = ReadConfigDatabase.getMySQLHost();
				String port = String.valueOf(ReadConfigDatabase.getMySQLPort());
				String username = ReadConfigDatabase.getMySQLUsername();
				String password = ReadConfigDatabase.getMySQLPassword();
				String database = ReadConfigDatabase.getMySQLDatabase();
				
				datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);
				
			}
			
			DatabaseMetaData metadata = datasource.getConnection().getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			//Creates all the tables in the selected database
			List<String> tables = new ArrayList<String>();		
			while(resultset.next()) {
				tables.add(resultset.getString(3));
			}

			if(!tables.contains("bans")) {
				execute("CREATE TABLE bans (ID INT, uuid VARCHAR, sender VARCHAR, reason TEXT, time DOUBLE, duration DOUBLE)");
			}
			
			if(!tables.contains("helpop")) {
				execute("CREATE TABLE helpop (ID INT, submitter TEXT, message TEXT, resolved Bool)");
			}
			
			if(!tables.contains("homes")) {
				execute("CREATE TABLE homes (ID INT, uuid VARCHAR, name TEXT, world INT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE)");
			}
			
			//Database Layout <RecepientID> <RecepientName> <MailID> <senderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
			if(!tables.contains("mail")) {
				execute("CREATE TABLE mail (RecepientID INT, RecepientName VARCHAR(16), MailID INT, SenderID INT, SenderName VARCHAR(16), Message TEXT, Sent Text, Read BOOL)");
			}
			
			if(!tables.contains("mutes")) {
				execute("CREATE TABLE mutes (ID INT, uuid VARCHAR, duration DOUBLE, reason TEXT)");
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (ID INT, uuid VARCHAR(36), name TEXT, IP VARCHAR(45), nick TEXT, channel TEXT, money DOUBLE, banned BOOL, god BOOL, fly BOOL, tptoggle BOOL, invisible BOOL, onlinetime DOUBLE, lastlocation TEXT, lastdeath TEXT, firstseen TEXT, lastseen TEXT)");
				execute("INSERT INTO players VALUES (0, '" + "uuid" + "', '" + "name" + "', '" + "192.168.1.1" + "', '" + "nick" + "', '" + "channel" + "', 123.0, 0,  1, 0, 1, 0, 123.0, '" + "LastLocation" + "', '" + "LastDeath" + "', '" + "FirstSeen" + "', '" + "LastSeen" + "');");
			}
				
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	//REPLACE EVERYTHING FROM HERE
	public static void load(Game game) {
		//Imports the entire BanList into HashMap
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM bans");
			while(rs.next()) {
				BanCore ban = new BanCore(rs.getInt("ID"),rs.getString("uuid"), rs.getString("sender"), rs.getString("reason"), rs.getDouble("time"), rs.getDouble("duration"));
				Bans.addBan(ban.getID(), ban);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Imports the entire MuteList into HashMap
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM mutes");
			while(rs.next()) {
				MuteCore mute = new MuteCore(rs.getInt("ID"), rs.getString("uuid"), rs.getDouble("duration"), rs.getString("reason"));
				Mutes.addMute(mute.getID(), mute);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Imports the entire PlayerList into hashmap
		
		
		//Imports the entire HomeList into HashMap
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM homes");
			while(rs.next()) {
				HomeCore home = new HomeCore(rs.getInt("ID"), rs.getString("uuid"), rs.getString("name"), rs.getInt("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"));
				Homes.addHome(home.getID(), home);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//TO HERE
	
	/**
	 * @param execute string MySQL command to execute
	 */
	public static void execute(String execute) {	
		try {
			Logger l = RRRP2.getRRRP2().getLogger();
			l.info(execute);
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param execute string MySQL command to execute
	 * @return int to use
	 */
	public static int findNextID(String table) {	
		int x = 0;
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT ID FROM " + table + " ORDER BY id DESC LIMIT 1;");
			if(rs.next()) {
				x = rs.getInt("ID");
			}
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return x + 1;
	}
	
	/**
	 * 
	 * @param execute string MySQL command to execute
	 * @return int to use
	 */
	public static int findNextMailID() {	
		int x = 0;
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT MailID FROM mail ORDER BY MailID DESC LIMIT 1;");
			if(rs.next()) {
				x = rs.getInt("MailID");
			}
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return x + 1;
	}
	
	/**
	 * @param execute executes a list of MySQL commands in the order they were provided
	 */
	public static void execute(List<String> execute) {	
		try {
		
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			for(String e : execute) statement.execute(e);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Executes the queue of MySQL commands
	 */
	public static void commit() {
		
		if(queue.isEmpty()) return;
		execute(queue);
		queue.clear();
		return;
		
	}
	
	/**
	 * @param queue command to add to the MySQL command queue
	 */
	public static void queue(String queue) { 
		Database.queue.add(queue); 
	}	
	
	//Start UUID This whole hashmap is used to populate people uuid and ID as they join so they can be tracked. Flushed on server shutdown.
	private static HashMap<String, Integer> uuids = new HashMap<String, Integer>();
	
	//adds a UUID/ID value pair to table
	public static void addUUID(String uuid, int ID) { 
		uuids.put(uuid, ID); 
	}
	
	//removes a player upon disconnect
	public static void removeUUID(String uuid) { 
		if(uuids.containsKey(uuid)) 
			uuids.remove(uuid); 
		}
	
	public static int getIDFromDatabase(String uuid){
		
		int x = 0;
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select ID from players where uuid LIKE '%" + uuid + "%';");
			if(rs.next()) {
				x = rs.getInt("ID");
			}
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return x;
	}
	/**
	 * @param uuid 
	 * @return ID 
	 * 
	 * @note Checks what the ID is of a player given their uuid
	 */
	public static int getID(String uuid) { return uuids.containsKey(uuid) ? uuids.get(uuid) : -1; }	
	//End UUID
	
	public static PlayerCore getPlayerCore(int ID) {
		PlayerCore player = new PlayerCore();
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM players WHERE ID = " + ID + ";");
			while(rs.next()) {
				player.setID(rs.getInt("ID"));
				player.setUUID(rs.getString("uuid")); 
				player.setName(rs.getString("name"));
				player.setNick(rs.getString("nick"));
				player.setChannel(rs.getString("channel"));
				player.setMoney(rs.getDouble("money"));
				player.setGod(rs.getBoolean("god"));
				player.setFly(rs.getBoolean("fly"));
				player.setTPToggle(rs.getBoolean("tptoggle"));
				player.setInvisible(rs.getBoolean("invisible"));
				player.setOnlinetime(rs.getDouble("onlinetime"));
				player.setLastlocation(rs.getString("lastlocation"));
				player.setLastdeath(rs.getString("lastdeath"));
				player.setFirstseen(rs.getString("firstseen"));
				player.setLastseen(rs.getString("lastseen"));
				rs.close();
			}	
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}
	
	public static int getPlayerIDfromUsername(String Username) {
		int id = 0;
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT ID FROM players WHERE name = '" + Username + "';");
			while(rs.next()) {
				id = rs.getInt("ID");
			}
			rs.close();
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static ArrayList<Text> getHelpOp(int ID) {
		ArrayList<Text> completedString = new ArrayList<Text>();
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("Select * from helpop WHERE ID = " + ID + ";");
			while(rs.next()) {
				completedString.add(Text.of(TextColors.GOLD, "TicketID: ", TextColors.GRAY, rs.getInt("ID")));
				completedString.add(Text.of(TextColors.GOLD, "Submitter: ", TextColors.GRAY, rs.getString("submitter")));
				completedString.add(Text.of(TextColors.GOLD, "Resolved: ", TextColors.GRAY, Utilities.boolToString(rs.getBoolean("resolved"))));
				completedString.add(Text.of(TextColors.GOLD, "Message: ", TextColors.GRAY, rs.getString("message")));
			}
			rs.close();
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return completedString;
	}
	
	public static ArrayList<Text> getAllOpenTickets(String resolved ) {
		ArrayList<Text> completedString = new ArrayList<Text>();
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs;
			if(resolved.equals("Resolved") || resolved.equals("resolved")) {
				rs = statement.executeQuery("Select * from helpop WHERE resolved = 1;");
			}else if(resolved.equals("all") || resolved.equals("All")) {
				rs = statement.executeQuery("Select * from helpop;");
			}else {
				rs = statement.executeQuery("Select * from helpop WHERE resolved = 0;");
			}
			while(rs.next()) {
				completedString.add(Text.of(TextColors.GOLD, "ID: ", TextColors.GRAY, rs.getInt("ID"), TextColors.GOLD, " Submitter: ", TextColors.GRAY, rs.getString("submitter"),
											" Resolved: ", TextColors.GRAY, Utilities.boolToString(rs.getBoolean("resolved")), TextColors.GOLD, " Message: ", TextColors.GRAY, 
											rs.getString("message"), TextColors.GOLD));
			}
			rs.close();
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return completedString;
	}
	
	public static ArrayList<Text> getAllOpenTickets() {
		ArrayList<Text> completedString = new ArrayList<Text>();
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("Select * from helpop WHERE resolved = 0;");
			while(rs.next()) {
				completedString.add(Text.of(TextColors.GOLD, "ID: ", TextColors.GRAY, rs.getInt("ID"), TextColors.GOLD, " Submitter: ", TextColors.GRAY, rs.getString("submitter"), TextColors.GOLD,
											" Resolved: ", TextColors.GRAY, Utilities.boolToString(rs.getBoolean("resolved")), TextColors.GOLD, " Message: ", TextColors.GRAY, 
											rs.getString("message"), TextColors.GOLD));
			}
			rs.close();
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return completedString;
	}
	
	public static String getLastSeen(int id) {
		
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT lastseen from players WHERE id = " + id + ";");
			String lastseen = "";
			
			while(rs.next()) {
				lastseen = rs.getString("lastseen");
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return lastseen;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static MailCore getOneMail(int id) {
		
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from mail WHERE MailID = " + id + ";");
			MailCore mail = new MailCore();
			
			//Database Layout <RecepientID> <RecepientName> <MailID> <SenderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
			while(rs.next()) {
				mail.setRecepientID(rs.getInt("RecepientID"));
				mail.setRecepientName(rs.getString("RecepientName"));
				mail.setMailID(rs.getInt("MailID"));
				mail.setSenderID(rs.getInt("SenderID"));
				mail.setSenderName(rs.getString("SenderName"));
				mail.setMessage(rs.getString("Message"));
				mail.setSentTime(rs.getString("Sent"));
				mail.setRead(rs.getBoolean("Read"));
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return mail;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<MailCore> getMail(int id) {
	
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from mail WHERE RecepientID = " + id + " ORDER BY MailID DESC;");
			ArrayList<MailCore> m = new ArrayList<MailCore>();
			
			//Database Layout <RecepientID> <RecepientName> <MailID> <SenderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
			while(rs.next()) {
				MailCore mail = new MailCore();
				mail.setRecepientID(rs.getInt("RecepientID"));
				mail.setRecepientName(rs.getString("RecepientName"));
				mail.setMailID(rs.getInt("MailID"));
				mail.setSenderID(rs.getInt("SenderID"));
				mail.setSenderName(rs.getString("SenderName"));
				mail.setMessage(rs.getString("Message"));
				mail.setSentTime(rs.getString("Sent"));
				mail.setRead(rs.getBoolean("Read"));
				m.add(mail);
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return m;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

