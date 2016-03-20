package net.re_renderreality.rrrp2.cmd;

import java.util.Hashtable;

import javax.annotation.Nonnull;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ListEntitiesCommand extends CommandExecutorBase{
	
	/**
	 * Lists all entities currently loaded on the server
	 */
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{ 
	
		Hashtable<String, Integer> entities = new Hashtable<String, Integer>();
		
		for (World w : Registry.getServer().getWorlds()) {
			for (Entity e:w.getEntities()) {
				entities.putIfAbsent(e.getType().getName(), 0);
				entities.replace(e.getType().getName(), entities.get(e.getType().getName()).intValue()+1);			
			}
		}
		src.sendMessage(Text.of(entities.toString().substring(1, entities.toString().length()-1)));
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ListEntities", "listEntities", "listentities", "Listentities", "lEntities", "LEntities", "LEntities", "li"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Lists all Entities on the Server"))
			.permission("rrrp2.listEntities")
			.executor(this)
			.build();
	}
}