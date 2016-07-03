package net.re_renderreality.rrrp2.cmd.spawn;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigSpawn;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class SetSpawnCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/setspawn";
		description = "Sets the spawn of a world";
		perm = "rrr.admin.setspawn";
		useage = "/setspawn";
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
	 * Sets spawn for the world the player is in. Spawns set in other other worlds can be tp'd to using /tpworld
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		if (src instanceof Player) {
			Player player = (Player) src;
			ReadConfigSpawn.setSpawn(player.getWorld().getName(), player.getTransform(), player.getWorld().getName());
			player.getWorld().getProperties().setSpawnPosition(player.getLocation().getBlockPosition());
			src.sendMessage(Text.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "Spawn set."));
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /setspawn!"));
		}

		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "SetSpawn", "setspawn", "setSpawn" };
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
			.description(Text.of("Sets the Server Spawn"))
			.permission("rrr.admin.setspawn")
			.executor(this)
			.build();
	}
}