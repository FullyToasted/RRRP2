package net.re_renderreality.rrrp2.api.util.config.readers;

import net.re_renderreality.rrrp2.api.util.config.ConfigManager;
import net.re_renderreality.rrrp2.api.util.config.Configs;
import net.re_renderreality.rrrp2.api.util.config.Configurable;
import net.re_renderreality.rrrp2.config.Teleport;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ReadConfigTeleport {
	private static Configurable teleportConfig = Teleport.getConfig();
	private static ConfigManager configManager = new ConfigManager();
	
	public static boolean isTeleportCooldownEnabled() {
		CommentedConfigurationNode node = Configs.getConfig(teleportConfig).getNode("teleport", "cooldown", "enable");
		if (configManager.getBoolean(node).isPresent())
			return node.getBoolean();
		setTeleportCooldownEnabled(true);
		return true;
	}
	
	/**
	 * @param bool whether or not to use the join message
	 */
	public static void setTeleportCooldownEnabled(boolean bool) {	
			Configs.setValue(teleportConfig, new Object[] { "teleport", "cooldown", "enable" }, bool);	
	}
	
	/**
	 * @return The Join Message
	 * @note must replace %player with PlayerName. Color codes may be used.
	 */
	public static long getTeleportCooldown() {
		CommentedConfigurationNode node = Configs.getConfig(teleportConfig).getNode("teleport", "cooldown", "timer");
		if (configManager.getLong(node).isPresent())
			return node.getLong();
		setTeleportCooldown(10);
		return 10;
	}
	
	/**
	 * @param value value to set the join msg to. 
	 * @note Can use Color Codes.
	 */
	public static void setTeleportCooldown(long value) {
			Configs.setValue(teleportConfig, new Object[] { "teleport", "cooldown", "timer" }, value);	
	}
}
