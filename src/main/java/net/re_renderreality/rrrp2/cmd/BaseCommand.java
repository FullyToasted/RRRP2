package net.re_renderreality.rrrp2.cmd;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;

public class BaseCommand {

	private String name;
	private String description;
	private String permission;
	private String[] aliases;
	private boolean isUniversal;
	private boolean isEnabled;
	
	public CommandSource src;
	
	/**
	 * @return String of with value of source type
	 */
	public Class<?> getSourceType() {
		if (src instanceof Player) {
			return Player.class;
		}
		else if (src instanceof ConsoleSource) {
			return ConsoleSource.class;
		}
		else if (src instanceof CommandBlockSource) {
			return CommandBlockSource.class;
		}
		return null;
	}
	
	/**
	 * @param name String of name to set for command name.
	 * @param description String to set for command description.
	 * @param permission string to set for required command permission.
	 * @param aliases String array to set for command aliases.
	 * @param isUniversal True = The command can be used by any CommandSource : False = Command has a specified CommandSource
	 * @param isEnabled True = The command will be enabled : False = Command won't be registered.
	 */
	public void setInformation(String name, String description, String permission, String[] aliases, boolean isUniversal) {
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.aliases = aliases;
		this.isUniversal = isUniversal;
	}
	
	/**
	 * @return permission String for command.
	 */
	public boolean isUniversal() {
		return isUniversal;
	}
	
	public String getPermission() {
		return permission;
	}
	
	/**
	 * @return CommandSource calling command. 
	 */
	public CommandSource getCommandSource() {
		return src;
	}
	
	/**
	 * @return String of name for command.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return String of description for command.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return String array of command aliases.
	 */
	public String[] getAliases() {
		return aliases;
	}
}