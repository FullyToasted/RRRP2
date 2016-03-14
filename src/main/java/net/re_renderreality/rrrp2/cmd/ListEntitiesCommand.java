package net.re_renderreality.rrrp2.cmd;

import java.util.Hashtable;

import net.re_renderreality.rrrp2.main.Registry;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ListEntitiesCommand {
	
	private final CommandSource src;
	private Hashtable<String, Integer> entities; //Hashtable of entity and count
	
	public ListEntitiesCommand(CommandSource src) { this.src = src; }
	
	public void run() {
		
		entities = new Hashtable<String, Integer>();
		
		for (World w : Registry.getServer().getWorlds()) { //for every world
			for (Entity e:w.getEntities()) { //for every entity in that world
				entities.putIfAbsent(e.getType().getName(), 0); //if not in hash table initialize it
				entities.replace(e.getType().getName(), entities.get(e.getType().getName()).intValue()+1);	//or add one to existing entity		
			}
		}
		src.sendMessage(Text.of(entities.toString().substring(1, entities.toString().length()-1))); //sends results to user
	}	
}