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
	/**
	 * @return whether or not the join message is enabled
	 */
	public static boolean getJoinMsgEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "join", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	/**
	 * @param bool whether or not to use the join message
	 */
	public static void setJoinMsgEnabled(boolean bool) {	
			Configs.setValue(messageConfig, new Object[] { "events", "join", "enable" }, bool);	
	}
	
	/**
	 * @return The Join Message
	 * @note must replace %player with PlayerName. Color codes may be used.
	 */
	public static String getJoinMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "join", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%player &7has joined.");
		return "&e%player &7has joined.";
	}
	
	/**
	 * @param value value to set the join msg to. 
	 * @note Can use Color Codes.
	 */
	public static void setJoinMsg(String value) {
			Configs.setValue(messageConfig, new Object[] { "events", "join", "message" }, value);	
	}
	
	/**
	 * @return whether or not to use the join message
	 */
	public static boolean getLeaveMsgEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "leave", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	/**
	 * @param bool whether or not to use the join message True or False
	 */
	public static void setLeaveMsgEnabled(boolean bool) {
		Configs.setValue(messageConfig, new Object[] { "events", "leave", "enable" }, bool);	
	}
	
	/**
	 * @return The Leave Message
	 * @note must replace %player with PlayerName. Color codes may be used.
	 */
	public static String getLeaveMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "leave", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%player &7has left.");
		return "&e%player &7has left.";
	}
	
	/**
	 * @param value to set the Leave message to
	 * @note Can Use Color Codes
	 */
	public static void setLeaveMsg(String value) {
			Configs.setValue(messageConfig, new Object[] { "events", "leave", "message" }, value);
	}
	
	/**
	 * @return whether or not the fist join message is enabled
	 */
	public static boolean getFirstJoinMsgEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	/**
	 * @param bool sets whether or not to use the first join message. True or False
	 */
	public static void setFirstJoinMsgEnabled(boolean bool) {
		Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "enable" }, bool);
	}
	
	/**
	 * @return The First Join Message
	 * @note must replace %player with PlayerName. Color codes may be used.
	 */
	public static String getFirstJoinMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%player &7has joined for the first time!");
		return "&e%player &7has joined for the first time!";
	}
	
	/**
	 * @param value value to set the First Join Message To
	 * @Note Can use Color Codes
	 */
	public static void setFirstJoinMsg(String value) {
			Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "message" }, value);
	}
	
	/**
	 * @return whether or not to show a unique player count on first join
	 */
	public static boolean getUniqueMsgShow() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "uniqueplayers", "show");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setJoinMsgEnabled(true);
		return true;
	}
	
	/**
	 * @param bool sets whether or not to show unique player count on first join
	 */
	public static void setUniqueMsgShow(boolean bool) {
		Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "uniqueplayers", "show" }, bool);
	}
	
	/**
	 * @return the unique player join message.
	 * @note must replace %players with number. Color codes may be used.
	 */
	public static String getUniqueMsg() {
		CommentedConfigurationNode node = Configs.getConfig(messageConfig).getNode("events", "firstjoin", "uniqueplayers", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setJoinMsg("&e%players &7unique players already joined.");
		return "&e%players &7unique players already joined.";
	}
	
	/**
	 * @param value value to set the Unique Count First Join Message To
	 * @Note Can use Color Codes
	 */
	public static void setUniqueMsg(String value) {
		Configs.setValue(messageConfig, new Object[] { "events", "firstjoin", "uniqueplayers", "message" }, value);
	}
}
