package net.re_renderreality.rrrp2.cmd;

public class BaseCommand {

	private String name;
	private String description;
	private String permission;
	private String[] aliases;
	private String usage;
	private boolean isUniversal;
	private boolean isEnabled;
	private boolean hasMultiArgs;
	
	/**
	 * @param name String of name to set for command name.
	 * @param description String to set for command description.
	 * @param permission string to set for required command permission.
	 * @param aliases String array to set for command aliases.
	 * @param isUniversal True = The command can be used by any CommandSource : False = Command has a specified CommandSource
	 * @param isEnabled True = The command will be enabled : False = Command won't be registered.
	 */
	public void setInformation(String name, String description, String permission, String[] aliases, boolean isUniversal) { //constructor for basic command
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.aliases = aliases;
		this.isUniversal = isUniversal;
	}
	
	
	public void setInformation(String name, String description, String permission, String[] aliases, boolean isUniversal, boolean multiArgs, String usage) { //Constructor for command with multi args
		this.name = name;
		this.description = description;
		this.permission = permission;
		this.aliases = aliases;
		this.isUniversal = isUniversal;
	}
	
	/**
	 * @return usage Returns how the command should be used (If Applicable).
	 */
	public String getUsage() { //gets usage of command or returns that it isn't there. 
		return usage.isEmpty() ? "Tell the Dev's the command " + getName() + "'s usage is broken." : usage;
	}
	
	/**
	 * @return hasMultiArgs If the command has any additional args make this true (Used for further handling).
	 */
	public boolean hasMultiArgs() { //getter for multiargs
		return hasMultiArgs;
	}
	
	/**
	 * @return isEnabled Returns true or false depending if it will be enabled
	 */
	public boolean isEnabled(){ //gets if cmd is enabled
		return isEnabled;
	}
	
	/**
	 * @return isUniversal Returns true if the command can be used by all CommandSources
	 */
	public boolean isUniversal() { //returns if command can be used by any source
		return isUniversal;
	}
	
	/**
	 * @return permission String for command.
	 */
	public String getPermission() { //gets permission node
		return permission;
	}
	
	/**
	 * @return String of name for command.
	 */
	public String getName() { //gets command name
		return name;
	}
	
	/**
	 * @return String of description for command.
	 */
	public String getDescription() { //gets command description
		return description;
	}
	
	/**
	 * @return String array of command aliases.
	 */
	public String[] getAliases() { //Gets command aliases
		return aliases;
	}
}