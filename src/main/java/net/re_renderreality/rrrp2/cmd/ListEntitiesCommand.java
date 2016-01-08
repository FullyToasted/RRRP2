package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.Registry;

import java.util.Hashtable;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ListEntitiesCommand {
	
	private CommandSource source;
	private Hashtable<String, Integer> entities;
	
	public ListEntitiesCommand(CommandSource src) { source = src; }
	
	public void run() {
		
		entities = new Hashtable<String, Integer>();
		
		for (World w:Registry.getServer().getWorlds()) {
			for (Entity e:w.getEntities()) {
				entities.putIfAbsent(e.getType().getName(), 0);
				entities.replace(e.getType().getName(), entities.get(e.getType().getName()).intValue()+1);			
			}
		}
		source.sendMessage(Text.of(entities.toString()));
	}	
}