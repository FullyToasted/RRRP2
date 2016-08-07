package net.re_renderreality.rrrp2.api.util.config.readers;

import java.util.ArrayList;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.config.Rules;
import net.re_renderreality.rrrp2.utils.Log;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigRules {
	private static Configurable rulesConfig = Rules.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	/**
	 * @return number of rules added
	 */
	public static int getNumRules() {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Rule Count");
		if (configManager.getInt(node).isPresent())
			return node.getInt();
		setNumRules(0);
		return 1;
	}
	
	/**
	 * @param num set number of rules added
	 */
	public static void setNumRules(int num) {
		Configs.setValue(rulesConfig, new Object[] { "Rule Count" }, num);
	}
	
	/**
	 * @return get added rules
	 */
	public static Iterable<Text> getRules() {
		ArrayList<Text> array = new ArrayList<Text>();
		int numOfRules = getNumRules();
		CommentedConfigurationNode node;
		if(!(numOfRules == 0)) {
			for(int i = 1; i <= numOfRules; ++i) {
				String number = "" + i;
				node = Configs.getConfig(rulesConfig).getNode("Rules" , number );
				if(configManager.getString(node).isPresent()) {
					String message = "&6" + i + ": " + node.getString();
					Text newMessage = TextSerializers.formattingCode('&').deserialize(message);
					array.add(newMessage);
				}
			}
		} else {
			String message = "&4 There are no rules on this server!";
			Text newMessage = TextSerializers.formattingCode('&').deserialize(message);
			array.add(newMessage);
		}
		Iterable<Text> text = array;
		return text;
	}
	
	/**
	 * @param id get rule with this id
	 * @return the rule
	 */
	public static String getRule(int id) {
		if(id <= getNumRules()) {
			String num = "" + id;
			CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Rules", num);
			return node.getString();
		}
		else
			return "";
	}
	
	/**
	 * @param rule adds rule to list
	 */
	public static void addRule(String rule) {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Rule Count");
		int newnumber = node.getInt() + 1;
		node.setValue(newnumber);
		Log.info("New Rule ID: " + newnumber + " Rule: " + rule );
		String sNumber = newnumber + "";
		Configs.getConfig(rulesConfig).getNode("Rules" , sNumber).setValue(rule);
		rulesConfig.save();
	}
	
	/**
	 * @param ruleNum rule ID to change
	 * @param rule new rule
	 */
	public static void changeRule(int ruleNum, String rule) {
		String number = "" + ruleNum;
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Rules" , number );
		node.setValue(rule);
		Log.info("Rule Change ID: " + ruleNum + " Rule: " + rule );
		rulesConfig.save();
		
	}
	
	/**
	 * @param id rule ID to remove
	 */
	public static void removeRule(int id) {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Rules");
		String key = "" + id;
		node.removeChild(key);
		Log.info("Removed Rule ID: " + id);
		Configs.getConfig(rulesConfig).getNode("Rule Count").setValue(getNumRules()-1);
		rulesConfig.save();
	}
	
	/**
	 * @return rule header
	 */
	public static Text getHeader() {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Header");
		Text header = TextSerializers.formattingCode('&').deserialize(node.getString());
		return header;
	}
	
	/**
	 * @param header set rule header
	 */
	public static void setHeader(String header) {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Header");
		node.setValue(header);
	}
	
	/**
	 * @return footer message
	 */
	public static Text getFooter() {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Footer");
		Text header = TextSerializers.formattingCode('&').deserialize(node.getString());
		return header;
	}
	
	/**
	 * @param footer set footer message
	 */
	public static void setFooter(String footer) {
		CommentedConfigurationNode node = Configs.getConfig(rulesConfig).getNode("Header");
		node.setValue(footer);
	}
}
