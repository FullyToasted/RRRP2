package net.re_renderreality.rrrp2.api.util.config.readers;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
//import org.spongepowered.api.Sponge;
import net.re_renderreality.rrrp2.config.Messages;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigMesseges {

	private static Configurable messageConfig = Messages.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	//Start Messages
	public static boolean getJoinMsgEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "join", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	public static void setJoinMsgEnabled(boolean bool) {	
			Configs.setValue(messageConfig, new Object[] { "events", "join", "enable" }, bool);	
	}
	
	public static String getJoinMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "join", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%player &7has joined.");
		return "&e%player &7has joined.";
	}
	
	public static void setJoinMsg(String value) {
			Configs.setValue(messageConfig, new Object[] { "events", "join", "message" }, value);	
	}
	
	public static boolean getLeaveMsgEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "leave", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	public static void setLeaveMsgEnabled(boolean bool) {
		Configs.setValue(messageConfig, new Object[] { "events", "leave", "enable" }, bool);	
	}
	
	public static String getLeaveMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "join", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%player &7has left.");
		return "&e%player &7has left.";
	}
	
	public static void setLeaveMsg(String value) {
			Configs.setValue(messageConfig, new Object[] { "events", "leave", "message" }, value);
	}
	
	public static boolean getFirstJoinMsgEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	public static void setFirstJoinMsgEnabled(boolean bool) {
		Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "enable" }, bool);
	}
	
	public static String getFirstJoinMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%player &7has joined for the first time!");
		return "&e%player &7has joined for the first time!";
	}
	
	public static void setFirstJoinMsg(String value) {
			Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "message" }, value);
	}
		public static boolean getUniqueMsgShow() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "uniqueplayers", "show");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	public static void setUniqueMsgShow(boolean bool) {
		Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "uniqueplayers", "show" }, bool);
	}
		
	public static String getUniqueMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "uniqueplayers", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%players &7unique players already joined.");
		return "&e%players &7unique players already joined.";
	}
	
	public static void setUniqueMsg(String value) {
		Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "uniqueplayers", "message" }, value);
	}
}
