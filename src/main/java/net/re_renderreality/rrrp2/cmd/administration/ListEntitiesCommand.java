package net.re_renderreality.rrrp2.cmd.administration;

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
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/listentities";
		description = "List all entities loaded on the server";
		perm = "rrr.admin.listentities";
		useage = "/listentities";
		notes = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPerm() {
		return this.perm;
	}
	
	public String getUseage() {
		return this.useage;
	}
	
	public String getNotes() {
		return this.notes;
	}
	/**
	 * Lists all entities currently loaded on the server
	 */
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{ 
		setLocalVariables();
		
		Hashtable<String, Integer> entities = new Hashtable<String, Integer>();
		
		//counts up every entity
		for (World w : Registry.getServer().getWorlds()) {
			for (Entity e:w.getEntities()) {
				entities.putIfAbsent(e.getType().getName(), 0);
				entities.replace(e.getType().getName(), entities.get(e.getType().getName()).intValue()+1);			
			}
		}
		//prints counts
		src.sendMessage(Text.of(entities.toString().substring(1, entities.toString().length()-1)));
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ListEntities", "listEntities", "listentities", "Listentities", "lEntities", "LEntities", "LEntities", "li", "entities"};
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.executor(this)
			.build();
	}
}