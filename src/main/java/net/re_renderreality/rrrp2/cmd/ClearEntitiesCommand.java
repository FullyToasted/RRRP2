package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.Registry;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ClearEntitiesCommand {
	 
	private final CommandContext args; //command's args
	private final CommandSource src; //commands src
	
	public ClearEntitiesCommand(CommandSource src, CommandContext args) { this.src = src; this.args = args; }
	
	public void run() {
		
		int count = 0; 
		String entity = args.<String>getOne("Entity").get(); //turns entity string to string
		
		if (entity.equalsIgnoreCase("player")) { //If entity is player
			src.sendMessage(Text.of("[WARNING] Do not use \"player\" as a parameter for this command. Command aborted.")); //can't kill off players
			return;
		}
		
		for (World w : Registry.getServer().getWorlds()) { //for every world
			for (Entity e : w.getEntities()) { //loop through every entity in the world
				if (e.getType().getName().equals(entity)) { //if entity matches desired
					e.remove(); //remove it
					count += 1;
				}
			}
		}
		
		Text result = (count > 0) ? Text.of("[SUCCESS] Removed: " + count + " of entity: " + entity) : Text.of("[ERROR] Could not find entities of: " + entity); //returns how many entities were removed
		src.sendMessage(result);
	}
}