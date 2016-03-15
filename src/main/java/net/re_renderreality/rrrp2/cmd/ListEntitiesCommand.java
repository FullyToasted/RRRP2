package net.re_renderreality.rrrp2.cmd;

import java.util.Hashtable;

import net.re_renderreality.rrrp2.main.Registry;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ListEntitiesCommand {
	
	private final CommandSource src;
	private Hashtable<String, Integer> entities;
	
	public ListEntitiesCommand(CommandSource src) { this.src = src; }
	
	public void run() {
		
		entities = new Hashtable<String, Integer>();
		
		for (World w : Registry.getServer().getWorlds()) {
			for (Entity e:w.getEntities()) {
				entities.putIfAbsent(e.getType().getName(), 0);
				entities.replace(e.getType().getName(), entities.get(e.getType().getName()).intValue()+1);			
			}
		}
		src.sendMessage(Text.of(entities.toString().substring(1, entities.toString().length()-1)));
	}	
}