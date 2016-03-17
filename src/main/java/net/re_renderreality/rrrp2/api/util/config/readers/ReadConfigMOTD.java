package net.re_renderreality.rrrp2.api.util.config.readers;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.config.Messages;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigMOTD {

	private static Configurable motdConfig = Messages.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	//Start MOTD
	public static String getMOTD() {
		CommentedConfigurationNode node = Configs.getConfig(motdConfig).getNode("events", "join", "message");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setMOTD("6This is the default MOTD");
		return "6This is the default MOTD";
	}
	
	public static void setMOTD(String value) {
			Configs.setValue(motdConfig, new Object[] { "events", "join", "message" }, value);	
	}
	//EndMOTD
}
