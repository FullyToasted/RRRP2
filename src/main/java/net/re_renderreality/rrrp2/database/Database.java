package net.re_renderreality.rrrp2.database;


import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.sql.SqlService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.utils.Utilities;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;
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
			if(!ReadConfig.useMySQL()) {
				
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
				String host = ReadConfig.getMySQLHost();
				String port = String.valueOf(ReadConfig.getMySQLPort());
				String username = ReadConfig.getMySQLUsername();
				String password = ReadConfig.getMySQLPassword();
				String database = ReadConfig.getMySQLDatabase();
				
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
				execute("CREATE TABLE bans (ID INT, bannedname VARCHAR(16), uuid VARCHAR(60), sender VARCHAR(16), reason TEXT, time TEXT, duration Text)");
			}
			
			if(!tables.contains("helpop")) {
				execute("CREATE TABLE helpop (ID INT, submitter VARCHAR(16), message TEXT, resolved Bool)");
			}
			
			if(!tables.contains("homes")) {
				execute("CREATE TABLE homes (ID INT, uuid VARCHAR(60), username VARCHAR(16), homename TEXT,  world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, roll DOUBLE)");
			}
			
			if(!tables.contains("warps")) {
				execute("CREATE TABLE warps (ID INT, warpname TEXT, creator VARCHAR(16), timecreated TEXT,  world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, roll DOUBLE)");
			}
			
			//Database Layout <RecepientID> <RecepientName> <MailID> <senderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
			if(!tables.contains("mail")) {
				execute("CREATE TABLE mail (RecepientID INT, RecepientName VARCHAR(16), MailID INT, SenderID INT, SenderName VARCHAR(16), Message TEXT, Sent Text, Read BOOL)");
			}
			
			if(!tables.contains("mutes")) {
				execute("CREATE TABLE mutes (ID INT, uuid VARCHAR(60), duration DOUBLE, reason TEXT)");
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (ID INT, uuid VARCHAR(60), name TEXT, IP VARCHAR(45), nick TEXT, channel TEXT, money DOUBLE, banned BOOL, god BOOL, fly BOOL, tptoggle BOOL, invisible BOOL, onlinetime DOUBLE, lastlocation TEXT, lastdeath TEXT, firstseen TEXT, lastseen TEXT)");
				execute("INSERT INTO players VALUES (0, '" + "uuid" + "', '" + "name" + "', '" + "192.168.1.1" + "', '" + "nick" + "', '" + "channel" + "', 123.0, 0,  1, 0, 1, 0, 123.0, '" + "LastLocation" + "', '" + "LastDeath" + "', '" + "FirstSeen" + "', '" + "LastSeen" + "');");
			}
				
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	//REPLACE EVERYTHING FROM HERE
	public static void load(Game game) {		
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
	}
		//Imports the entire PlayerList into hashmap
		
	
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
				player.setBanned(rs.getBoolean("banned"));
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
	
	public static ArrayList<MailCore> getMail() {
		
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from mail ORDER BY MailID DESC;");
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
	
	public static BanCore getOneBan(int id) {
		
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from bans WHERE ID = " + id + ";");
			BanCore ban = new BanCore();
			
			//Database Layout <RecepientID> <RecepientName> <MailID> <SenderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
			while(rs.next()) {
				ban.setID(rs.getInt("ID"));
				ban.setBannedName(rs.getString("bannedname"));
				ban.setUUID(rs.getString("uuid"));
				ban.setSender(rs.getString("sender"));
				ban.setReason(rs.getString("reason"));
				ban.setTime(rs.getString("time"));
				ban.setDuration(rs.getString("duration"));
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return ban;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<BanCore> getBans() {
		
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * from bans");
			ArrayList<BanCore> b = new ArrayList<BanCore>();
			
			//Database Layout <RecepientID> <RecepientName> <MailID> <SenderID> <SenderName> <Sent Date/Time> <Msg> <Read> 
			while(rs.next()) {
				BanCore ban = new BanCore();
				ban.setID(rs.getInt("ID"));
				ban.setBannedName(rs.getString("bannedname"));
				ban.setUUID(rs.getString("uuid"));
				ban.setSender(rs.getString("sender"));
				ban.setReason(rs.getString("reason"));
				ban.setTime(rs.getString("time"));
				ban.setDuration(rs.getString("duration"));
				b.add(ban);
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return b;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getPlayerHomeCount(String playerName) {
		
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT ID FROM homes WHERE username = '" + playerName + "' ORDER BY ID ASC ");
			int count = 0;
			while(rs.next()) {
				count++;
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static HomeCore getHome(Player p, String homeName) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM homes WHERE username = '" + p.getName() + "' AND homename = '" + homeName + "';");
			HomeCore home = new HomeCore();
			while(rs.next()) {
				home.setID(rs.getInt("ID"));
				home.setUUID(rs.getString("uuid"));
				home.setUsername(rs.getString("username"));
				home.setHomeName(rs.getString("homename"));
				home.setWorld(rs.getString("world"));
				home.setX(rs.getDouble("x"));
				home.setY(rs.getDouble("y"));
				home.setZ(rs.getDouble("z"));
				home.setYaw(rs.getDouble("yaw"));
				home.setPitch(rs.getDouble("pitch"));
				home.setRoll(rs.getDouble("roll"));
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return home;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean getHomeExist(Player p, String homeName) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM homes WHERE username = '" + p.getName() + "' AND homename = '" + homeName + "';");
			if(!rs.next()) {
				rs.close();
				statement.close();
				connection.close();
				return false;
			}
			rs.close();
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<HomeCore> getHomesByPlayer(Player player) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM homes WHERE username = '" + player.getName() + "';");
			ArrayList<HomeCore> homes = new ArrayList<HomeCore>();
			while(rs.next()) {
				HomeCore home = new HomeCore();
				home.setID(rs.getInt("ID"));
				home.setUUID(rs.getString("uuid"));
				home.setUsername(rs.getString("username"));
				home.setHomeName(rs.getString("homename"));
				home.setWorld(rs.getString("world"));
				home.setX(rs.getDouble("x"));
				home.setY(rs.getDouble("y"));
				home.setZ(rs.getDouble("z"));
				home.setYaw(rs.getDouble("yaw"));
				home.setPitch(rs.getDouble("pitch"));
				home.setRoll(rs.getDouble("roll"));
				homes.add(home);
			}
			rs.close();
			statement.close();
			connection.close();
			return homes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<HomeCore> getHomes() {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM homes;");
			ArrayList<HomeCore> homes = new ArrayList<HomeCore>();
			while(rs.next()) {
				HomeCore home = new HomeCore();
				home.setID(rs.getInt("ID"));
				home.setUUID(rs.getString("uuid"));
				home.setUsername(rs.getString("username"));
				home.setHomeName(rs.getString("homename"));
				home.setWorld(rs.getString("world"));
				home.setX(rs.getDouble("x"));
				home.setY(rs.getDouble("y"));
				home.setZ(rs.getDouble("z"));
				home.setYaw(rs.getDouble("yaw"));
				home.setPitch(rs.getDouble("pitch"));
				home.setRoll(rs.getDouble("roll"));
				homes.add(home);
			}
			rs.close();
			statement.close();
			connection.close();
			return homes;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static HomeCore getHomeByID(int id) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM homes WHERE ID = " + id + ";");
			HomeCore home = new HomeCore();
			while(rs.next()) {
				home.setID(rs.getInt("ID"));
				home.setUUID(rs.getString("uuid"));
				home.setUsername(rs.getString("username"));
				home.setHomeName(rs.getString("homename"));
				home.setWorld(rs.getString("world"));
				home.setX(rs.getDouble("x"));
				home.setY(rs.getDouble("y"));
				home.setZ(rs.getDouble("z"));
				home.setYaw(rs.getDouble("yaw"));
				home.setPitch(rs.getDouble("pitch"));
				home.setRoll(rs.getDouble("roll"));
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return home;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static WarpCore getWarp(String warpName) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM warps WHERE warpname = '" + warpName + "';");
			WarpCore home = new WarpCore();
			while(rs.next()) {
				home.setID(rs.getInt("ID"));
				home.setWarpName(rs.getString("warpname"));
				home.setCreator(rs.getString("creator"));
				home.setTimeCreated(rs.getString("timecreated"));
				home.setWorld(rs.getString("world"));
				home.setX(rs.getDouble("x"));
				home.setY(rs.getDouble("y"));
				home.setZ(rs.getDouble("z"));
				home.setYaw(rs.getDouble("yaw"));
				home.setPitch(rs.getDouble("pitch"));
				home.setRoll(rs.getDouble("roll"));
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return home;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean getWarpExist(String warpName) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM warps WHERE warpname = '" + warpName + "';");
			if(!rs.next()) {
				rs.close();
				statement.close();
				connection.close();
				return false;
			}
			rs.close();
			statement.close();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<WarpCore> getWarps() {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM warps;");
			ArrayList<WarpCore> warps = new ArrayList<WarpCore>();
			while(rs.next()) {
				WarpCore warp = new WarpCore();
				warp.setID(rs.getInt("ID"));
				warp.setWarpName(rs.getString("warpname"));
				warp.setCreator(rs.getString("creator"));
				warp.setTimeCreated(rs.getString("timecreated"));
				warp.setWorld(rs.getString("world"));
				warp.setX(rs.getDouble("x"));
				warp.setY(rs.getDouble("y"));
				warp.setZ(rs.getDouble("z"));
				warp.setYaw(rs.getDouble("yaw"));
				warp.setPitch(rs.getDouble("pitch"));
				warp.setRoll(rs.getDouble("roll"));
				warps.add(warp);
			}
			rs.close();
			statement.close();
			connection.close();
			return warps;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static WarpCore getWarpByID(int id) {
		try {
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM warps WHERE ID = " + id + ";");
			WarpCore warp = new WarpCore();
			while(rs.next()) {
				warp.setID(rs.getInt("ID"));
				warp.setWarpName(rs.getString("warpname"));
				warp.setCreator(rs.getString("creator"));
				warp.setTimeCreated(rs.getString("timecreated"));
				warp.setWorld(rs.getString("world"));
				warp.setX(rs.getDouble("x"));
				warp.setY(rs.getDouble("y"));
				warp.setZ(rs.getDouble("z"));
				warp.setYaw(rs.getDouble("yaw"));
				warp.setPitch(rs.getDouble("pitch"));
				warp.setRoll(rs.getDouble("roll"));
			}
			rs.close();
			
			statement.close();
			connection.close();
			
			return warp;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

