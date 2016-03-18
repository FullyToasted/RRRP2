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
				execute("CREATE TABLE bans (uuid VARCHAR, sender VARCHAR, reason TEXT, time DOUBLE, duration DOUBLE)");
			}
			
			if(!tables.contains("homes")) {
				execute("CREATE TABLE homes (uuid VARCHAR, name TEXT, world INT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE)");
			}
			
			if(!tables.contains("mutes")) {
				execute("CREATE TABLE mutes (uuid VARCHAR, duration DOUBLE, reason TEXT)");
			}
			
			if(!tables.contains("players")) {
				execute("CREATE TABLE players (uuid VARCHAR, name TEXT, nick TEXT, channel TEXT, money DOUBLE, god BOOL, fly BOOL, tptoggle BOOL, invisible BOOL, onlinetime DOUBLE, mails TEXT, lastlocation TEXT, lastdeath TEXT, firstseen String, lastseen String)");
			}
				
		} catch (SQLException e) { e.printStackTrace(); }
			
		
	}
	
	public static void load(Game game) {
		
		try {
			Connection c = datasource.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM bans");
			while(rs.next()) {
				BanCore ban = new BanCore(rs.getString("uuid"), rs.getString("sender"), rs.getString("reason"), rs.getDouble("time"), rs.getDouble("duration"));
				Database.addBan(ban.getUUID(), ban);
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
				MuteCore mute = new MuteCore(rs.getString("uuid"), rs.getDouble("duration"), rs.getString("reason"));
				Database.addMute(mute.getUUID(), mute);
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
				PlayerCore player = new PlayerCore(rs.getString("uuid"), rs.getString("name"), rs.getString("nick"), rs.getString("channel"), rs.getDouble("money"), rs.getBoolean("god"), rs.getBoolean("fly"), rs.getBoolean("tptoggle"), rs.getBoolean("invisible"), rs.getDouble("onlinetime"), rs.getString("mails"), rs.getString("lastlocation"), rs.getString("lastdeath"), rs.getString("firstseen"), rs.getString("lastseen"));
				Database.addPlayer(player.getUUID(), player);
				Database.addUUID(player.getName(), player.getUUID());
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
				HomeCore home = new HomeCore(rs.getString("uuid"), rs.getString("name"), rs.getInt("world"), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getDouble("yaw"), rs.getDouble("pitch"));
				PlayerCore player = Database.getPlayer(home.getUUID());
				player.setHome(home.getName(), home);
				Database.removePlayer(home.getUUID());
				Database.addPlayer(home.getUUID(), player);
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
	private static HashMap<String, BanCore> bans = new HashMap<String, BanCore>();
	
	public static void addBan(String uuid, BanCore ban) { 
		if(!bans.containsKey(uuid)) 
			bans.put(uuid, ban); 
	}
	
	public static void removeBan(String uuid) { 
		if(bans.containsKey(uuid)) 
			bans.remove(uuid); 
	}
	
	public static BanCore getBan(String uuid) { 
		return bans.containsKey(uuid) ? bans.get(uuid) : null; 
	}
	
	public static HashMap<String, BanCore> getBans() { 
		return bans; 
	}
	//End Bans
	
	//Start Mute
	private static HashMap<String, MuteCore> mutes = new HashMap<String, MuteCore>();
	
	public static void addMute(String uuid, MuteCore mute) { 
		if(!mutes.containsKey(uuid)) mutes.put(uuid, mute); 
	
	}
	public static void removeMute(String uuid) { 
		if(mutes.containsKey(uuid)) mutes.remove(uuid); 
	}
	
	public static MuteCore getMute(String uuid) { 
		return mutes.containsKey(uuid) ? mutes.get(uuid) : null; 
	}
	
	public static HashMap<String, MuteCore> getMutes() { 
		return mutes; 
	}
	//End Mute
	
	//Start Players
	private static HashMap<String, PlayerCore> players = new HashMap<String, PlayerCore>();
	
	public static void addPlayer(String uuid, PlayerCore player) { 
		if(!players.containsKey(players)) 
			players.put(uuid, player); 
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
	public static HashMap<String, PlayerCore> getPlayers() { return players; }
	//End Players
	
	//Start UUID
	private static HashMap<String, String> uuids = new HashMap<String, String>();
	
	public static void addUUID(String name, String uuid) { 
		uuids.put(name, uuid); 
	}
	
	public static void removeUUID(String name) { 
		if(uuids.containsKey(name)) 
			uuids.remove(name); 
		}
	
	public static String getUUID(String name) { return uuids.containsKey(name) ? uuids.get(name) : null; }	
	//End UUID
}
