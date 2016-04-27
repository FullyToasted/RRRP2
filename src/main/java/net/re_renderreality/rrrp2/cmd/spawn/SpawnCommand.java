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
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigSpawn;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigTeleport;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class SpawnCommand extends CommandExecutorBase {
	
	/**
	 * TPs a player to the server spawn
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		if (src instanceof Player)
		{
			Player player = (Player) src;
			int id = Database.getID(player.getUniqueId().toString());
			PlayerCore playerz = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
			Transform<World> spawn;
			if(ReadConfigSpawn.isSpawnInConfig(Registry.getServer().getDefaultWorldName())) {
				spawn = ReadConfigSpawn.getSpawn(Registry.getServer().getDefaultWorldName());
			} else {
				Utilities.broadcastMessage("Error! No Spawn set. Have server administrator type /setspawn");
				return CommandResult.empty();
			}
		
			//tp cooldown if enabled
			if (ReadConfigTeleport.isTeleportCooldownEnabled() && !player.hasPermission("rrrp2.teleport.cooldown.override")) {
				RRRP2.teleportingPlayers.add(playerz.getID());
				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleporting to Spawn. Please wait " + ReadConfigTeleport.getTeleportCooldown() + " seconds."));
				
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
				}).delay(ReadConfigTeleport.getTeleportCooldown(), TimeUnit.SECONDS).name("RRRP2 - Back Timer").submit(Sponge.getGame().getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
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
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("Brings Player to the set spawnpoint"))
			.permission("rrr.general.spawn")
			.executor(this)
			.build();
	}
}
