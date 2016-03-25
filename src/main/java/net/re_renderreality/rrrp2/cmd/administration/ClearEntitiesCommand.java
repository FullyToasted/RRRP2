package net.re_renderreality.rrrp2.cmd.administration;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

public class ClearEntitiesCommand extends CommandExecutorBase {
	
	/**
	 * arg = entity to be removed by the command
	 * 
	 * Removes only one entity at a time
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{	
		int count = 0;
		String entity = ctx.<String>getOne("Entity").get();
		
		if (entity.equalsIgnoreCase("player")) {
			src.sendMessage(Text.of("[WARNING] Do not use \"player\" as a parameter for this command. Command aborted."));
			return CommandResult.empty();
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
		src.sendMessage(result);
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "clearentities", "centities", "ClearEntities", "Clearentities", "clearEntities", "cEntities", "CEntities", "ClearEntity", "clearentity", "Clearentity" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Clears all the entities of the specified type"))
				.permission("rrr.admin.clearentities")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("Entity")))))
				.executor(this).build();
	}
}