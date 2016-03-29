package net.re_renderreality.rrrp2.api.util.config.readers;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.config.Messages;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigChat {
	private static Configurable chatConfig = Messages.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	//Start Chat
	public static String getFirstCharactar() {
		CommentedConfigurationNode node = Configs.getConfig(chatConfig).getNode("chat", "firstcharacter");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setFirstCharactar("<");
		return "<";
	}
	
	public static void setFirstCharactar(String value) {
			Configs.setValue(chatConfig, new Object[] {"chat", "firstcharacter"}, value);	
	}
	
	public static String getLastCharactar() {
		CommentedConfigurationNode node = Configs.getConfig(chatConfig).getNode("chat", "lastcharacter");
		if (configManager.getString(node).isPresent())
			return node.getString();
		setLastCharactar(">");
		return ">";
	}
	
	public static void setLastCharactar(String value) {
			Configs.setValue(chatConfig, new Object[] {"chat", "lastcharacter"}, value);	
	}
	//End Chat
}
