package net.re_renderreality.rrrp2.cmd.spawn;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.PluginInfo;
import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigWorld;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class SpawnCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/spawn";
		description = "Teleport to spawn";
		perm = "rrr.general.spawn";
		useage = "/spawn";
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
	 * TPs a player to the server spawn
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		if (src instanceof Player) {
			Player player = (Player) src;
			PlayerCore playerz = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
			Transform<World> spawn;
			ReadConfigWorld.setWorld(Registry.getServer().getDefaultWorldName());
			spawn = ReadConfigWorld.getSpawn();
			
			//tp cooldown if enabled
			if (ReadConfig.getTeleportCooldownEnabled() && !player.hasPermission("rrrp2.teleport.cooldown.override")) {
				RRRP2.teleportingPlayers.add(playerz.getID());
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleporting to Spawn. Please wait " + ReadConfig.getTeleportCooldown() + " seconds."));
				
				Sponge.getScheduler().createTaskBuilder().execute(() -> {
					if(RRRP2.teleportingPlayers.contains(playerz.getID()))
					{
						playerz.setLastlocationUpdate(Utilities.convertLocation(player));
						if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId())) {
							player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
							player.setTransform(spawn);
						} else {
							player.setTransform(spawn);
						}
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Spawn"));
						RRRP2.teleportingPlayers.remove(playerz.getID());
					}
				}).delay(ReadConfig.getTeleportCooldown(), TimeUnit.SECONDS).name("RRRP2 - Back Timer").submit(Sponge.getGame().getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
			} else {
				playerz.setLastlocationUpdate(Utilities.convertLocation(player));
			
				if (!Objects.equals(player.getWorld().getUniqueId(), spawn.getExtent().getUniqueId())) {
					player.transferToWorld(spawn.getExtent().getUniqueId(), spawn.getPosition());
					player.setTransform(spawn);
				} else {
					player.setTransform(spawn);
				}
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Spawn."));
			}
			
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /back!"));
		}
		
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "spawn", "Spawn" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
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
