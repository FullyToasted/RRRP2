package net.re_renderreality.rrrp2.api.util.config.readers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.config.Announcements;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigAnnouncements {
	private static Logger l = RRRP2.getRRRP2().getLogger();
	private static Configurable announcementsConfig = Announcements.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	public static int getNumAnnouncements() {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Announcement Count");
		if (configManager.getInt(node).isPresent())
			return node.getInt();
		setNumAnnouncements(0);
		return 1;
	}
	
	public static void setNumAnnouncements(int num) {
		Configs.setValue(announcementsConfig, new Object[] { "Announcement Count" }, num);
	}
	
	public static Iterable<Text> getAnnouncements() {
		ArrayList<Text> array = new ArrayList<Text>();
		int numOfAnnouncements = getNumAnnouncements();
		CommentedConfigurationNode node;
		if(!(numOfAnnouncements == 0)) {
			for(int i = 1; i <= numOfAnnouncements; ++i) {
				String number = "" + i;
				node = Configs.getConfig(announcementsConfig).getNode("Announcements" , number );
				if(configManager.getString(node).isPresent()) {
					String message = "&6- " + node.getString();
					Text newMessage = TextSerializers.formattingCode('&').deserialize(message);
					array.add(newMessage);
				}
			}
		} else {
			String message = "&4 There are no Announcements on this yet!";
			Text newMessage = TextSerializers.formattingCode('&').deserialize(message);
			array.add(newMessage);
		}
		Iterable<Text> text = array;
		return text;
	}
	
	public static String getAnnouncement(int id) {
		if(id <= getNumAnnouncements()) {
			String num = "" + id;
			CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Announcements", num);
			return node.getString();
		}
		else
			return "";
	}
	
	public static void addAnnouncement(String announcement) {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Announcement Count");
		int newnumber = node.getInt() + 1;
		node.setValue(newnumber);
		l.info(newnumber + " " + announcement );
		String sNumber = newnumber + "";
		Configs.getConfig(announcementsConfig).getNode("Announcements" , sNumber).setValue(announcement);
		announcementsConfig.save();
	}
	
	public static void changeAnnouncement(int announcementNum, String announcement) {
		String number = "" + announcementNum;
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Announcements" , number );
		node.setValue(announcement);
		announcementsConfig.save();
		
	}
	
	public static void removeAnnouncement(int id) {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Announcements");
		String key = "" + id;
		node.removeChild(key);
		Configs.getConfig(announcementsConfig).getNode("Announcement Count").setValue(getNumAnnouncements()-1);
		announcementsConfig.save();
	}
	
	public static Text getHeader() {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Header");
		Text header = TextSerializers.formattingCode('&').deserialize(node.getString());
		return header;
	}
	
	public static void setHeader(String header) {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Header");
		node.setValue(header);
	}
	
	public static Text getFooter() {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Footer");
		Text header = TextSerializers.formattingCode('&').deserialize(node.getString());
		return header;
	}
	
	public static void setFooter(String footer) {
		CommentedConfigurationNode node = Configs.getConfig(announcementsConfig).getNode("Header");
		node.setValue(footer);
	}
}
