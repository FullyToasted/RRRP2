package net.re_renderreality.rrrp2.database;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.sql.SqlService;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigDatabase;

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
				
				String host = ReadConfigDatabase.getMySQLHost();
				String port = String.valueOf(ReadConfigDatabase.getMySQLPort());
				String username = ReadConfigDatabase.getMySQLUsername();
				String password = ReadConfigDatabase.getMySQLPassword();
				String database = ReadConfigDatabase.getMySQLDatabase();
				
				datasource = sql.getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);
				
			}
			
			DatabaseMetaData metadata = datasource.getConnection().getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			List<String> tables = new ArrayList<String>();		
			while(resultset.next()) {
				tables.add(resultset.getString(3));
			}

			if(!tables.contains("bans")) {
				execute("CREATE TABLE bans (ID INT, uuid VARCHAR, sender VARCHAR, reason TEXT, time DOUBLE, duration DOUBLE)");
			}
			
			if(!tables.contains("homes")) {
				execute("CREATE TABLE homes (ID INT, uuid VARCHAR, name TEXT, world INT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE)");
			}
			
			if(!tables.contains("mutes")) {
				execute("CREATE TABLE mutes (ID INT, uuid VARCHAR, duration DOUBLE, reason TEXT)");
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (ID INT, uuid VARCHAR, name TEXT, nick TEXT, channel TEXT, money DOUBLE, god BOOL, fly BOOL, tptoggle BOOL, invisible BOOL, onlinetime DOUBLE, mails TEXT, lastlocation TEXT, lastdeath TEXT, firstseen String, lastseen String)");
			}
				
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	
	public static void load(Game game) {
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM bans");
			while(rs.next()) {
				BanCore ban = new BanCore(rs.getInt("ID"),rs.getString("uuid"), rs.getString("sender"), rs.getString("reason"), rs.getDouble("time"), rs.getDouble("duration"));
				Database.addBan(ban.getID(), ban);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM mutes");
			while(rs.next()) {
				MuteCore mute = new MuteCore(rs.getInt("ID"), rs.getString("uuid"), rs.getDouble("duration"), rs.getString("reason"));
				Database.addMute(mute.getID(), mute);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM players");
			while(rs.next()) {
				PlayerCore player = new PlayerCore(rs.getInt("ID"), rs.getString("uuid"), rs.getString("name"), rs.getString("nick"), rs.getString("channel"), rs.getDouble("money"), rs.getBoolean("god"), rs.getBoolean("fly"), rs.getBoolean("tptoggle"), rs.getBoolean("invisible"), rs.getDouble("onlinetime"), rs.getString("mails"), rs.getString("lastlocation"), rs.getString("lastdeath"), rs.getString("firstseen"), rs.getString("lastseen"));
				Database.addPlayer(player.getID(), player);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM homes");
			while(rs.next()) {
				HomeCore home = new HomeCore(rs.getInt("ID"), rs.getString("uuid"), rs.getString("name"), rs.getInt("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"));
				PlayerCore player = Database.getPlayer(home.getUUID());
				player.setHome(home.getName(), home);
				Database.removePlayer(home.getID());
				Database.addPlayer(home.getID(), player);
			}
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(String execute) {	
		try {
			
			Connection connection = datasource.getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void commit() {
		
		if(queue.isEmpty()) return;
		execute(queue);
		queue.clear();
		
	}
	
	public static void queue(String queue) { 
		Database.queue.add(queue); 
	}
	
	//Start Bans
	private static HashMap<Integer, BanCore> bans = new HashMap<Integer, BanCore>();
	
	public static void addBan(int ID, BanCore ban) { 
		if(!bans.containsKey(ID)) 
			bans.put(ID, ban); 
	}
	
	public static void removeBan(int ID) { 
		if(bans.containsKey(ID)) 
			bans.remove(ID); 
	}
	
	public static BanCore getBan(int ID) { 
		return bans.containsKey(ID) ? bans.get(ID) : null; 
	}
	
	public static HashMap<Integer, BanCore> getBans() { 
		return bans; 
	}
	//End Bans
	
	//Start Mute
	private static HashMap<Integer, MuteCore> mutes = new HashMap<Integer, MuteCore>();
	
	public static void addMute(int ID, MuteCore mute) { 
		if(!mutes.containsKey(ID)) mutes.put(ID, mute); 
	
	}
	public static void removeMute(int ID) { 
		if(mutes.containsKey(ID)) mutes.remove(ID); 
	}
	
	public static MuteCore getMute(int ID) { 
		return mutes.containsKey(ID) ? mutes.get(ID) : null; 
	}
	
	public static HashMap<Integer, MuteCore> getMutes() { 
		return mutes; 
	}
	//End Mute
	
	//Start Players
	private static HashMap<Integer, PlayerCore> players = new HashMap<Integer, PlayerCore>();
	
	public static void addPlayer(int ID, PlayerCore player) { 
		if(!players.containsKey(ID)) 
			players.put(ID, player); 
	}
	
	public static void setLastTimePlayerJoined(Player player, String time)
	{
		if(players.containsKey(getPlayer(player.getName()))) {
			players.get(player.getName()).setLastseen(time);
			if(players.get(player.getName()).getFirstseen() == null) {
				players.get(player.getName()).setFirstseen(time);
			}
		}
		
	}
	
	public static void removePlayer(String uuid) { 
		if(players.containsKey(uuid)) 
			players.remove(uuid); 
	}
	
	public static PlayerCore getPlayer(String uuid) { 
		return players.containsKey(uuid) ? players.get(uuid) : null; 
	
	}
	public static HashMap<Integer, PlayerCore> getPlayers() { return players; }
	//End Players
	
	//Start UUID
	private static HashMap<Integer, String> uuids = new HashMap<Integer, String>();
	
	public static void addUUID(int ID, String uuid) { 
		uuids.put(ID, uuid); 
	}
	
	public static void removeUUID(int ID) { 
		if(uuids.containsKey(ID)) 
			uuids.remove(ID); 
		}
	
	public static String getUUID(int ID) { return uuids.containsKey(ID) ? uuids.get(ID) : null; }	
	//End UUID
}
