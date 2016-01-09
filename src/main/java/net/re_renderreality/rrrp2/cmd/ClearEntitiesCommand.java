package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.Registry;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ClearEntitiesCommand {
	 
	private final CommandContext arguments;
	private final CommandSource source;
	
	public ClearEntitiesCommand(CommandSource src, CommandContext args) { arguments = args; source = src; }
	
	public void run() {
		
		int count = 0;
		String entity = arguments.<String>getOne("Entity").get();
		
		if (entity.equalsIgnoreCase("player")) {
			source.sendMessage(Text.of("[WARNING] Do not use \"player\" as a parameter for this command. Command aborted."));
			return;
		}
		
		for (World w : Registry.getServer().getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e.getType().getName().equals(entity)) {
					e.remove();
					count += 1;
				}
			}
		}
		
		Text result = (count > 0) ? Text.of("[SUCCESS] Removed: " + count + " of entity: " + entity) : Text.of("[ERROR] Could not find entities of: " + entity);
		source.sendMessage(result);
	}
}