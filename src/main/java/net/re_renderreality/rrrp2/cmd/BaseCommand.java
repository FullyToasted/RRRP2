package net.re_renderreality.rrrp2.cmd;

public class BaseCommand {

	private String name;
	private String description;
	private String permission;
	private String[] aliases;
	private boolean isUniversal;
	private boolean isEnabled;
	
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
	 * @return isEnabled Returns true or false depending if it will be enabled
	 */
	public boolean isEnabled(){
		return isEnabled;
	}
	
	/**
	 * @return isUniversal Returns true if the command can be used by all CommandSources
	 */
	public boolean isUniversal() {
		return isUniversal;
	}
	
	/**
	 * @return permission String for command.
	 */
	public String getPermission() {
		return permission;
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