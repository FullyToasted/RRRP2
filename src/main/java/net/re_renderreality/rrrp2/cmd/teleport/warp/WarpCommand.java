 package net.re_renderreality.rrrp2.cmd.teleport.warp;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.PositionOutOfBoundsException;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import net.re_renderreality.rrrp2.PluginInfo;
import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigTeleport;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.database.core.WarpCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class WarpCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/warp";
		description = "Warp to a warp point";
		perm = "rrr.teleport.warp";
		useage = "/warp <warppoint>";
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
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		setLocalVariables();
		Optional<String> warpName = ctx.<String> getOne("warpname");
		Player source = (Player) src;
		PlayerCore player = Registry.getOnlinePlayers().getPlayerCorefromUsername(source.getName());
		
		if(src instanceof Player) {
			if(warpName.isPresent()) {
				String warpname = warpName.get();
				if(Database.getWarpExist(warpname) && source.hasPermission("rrr.general.warp." + warpname)) { //Import Transform and tp player
					WarpCore warp = Database.getWarp(warpname);
					if(teleportPlayer(source, player, warp)) {
						return CommandResult.success();
					}
				} else {
					src.sendMessage(Text.of(TextColors.RED, "This warp does not exist!"));
					CommandResult.empty();
				}
			} 
		} else {
			source.sendMessage(Text.of(TextColors.RED, "You cant be teleported to this warp"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}
	
	private boolean teleportPlayer(Player source, PlayerCore player, WarpCore warp) {
		try {
			if (ReadConfigTeleport.isTeleportCooldownEnabled() && !source.hasPermission("rrr.general.teleport.cooldownoverride")) {
				RRRP2.teleportingPlayers.add(player.getID());
				source.sendMessage(Text.of(TextColors.GOLD, "Teleporting to Warp. Please wait ",TextColors.GRAY, ReadConfigTeleport.getTeleportCooldown(), TextColors.GOLD, " seconds."));

				Sponge.getScheduler().createTaskBuilder().execute(() -> {
					if (RRRP2.teleportingPlayers.contains(player.getID())) {
						player.setLastlocationUpdate(Utilities.convertLocation(source));

						if (source.getWorld().getName().equals(warp.getWorld())) {
							source.setTransform(getWarpTransform(warp));
						}
						else {
							Transform<World> temp = getWarpTransform(warp);
							source.transferToWorld(temp.getExtent().getName(), temp.getPosition());
							source.setTransform(temp);
						}

						source.sendMessage(Text.of(TextColors.GOLD, "Teleported to Warp: ", TextColors.GRAY, warp.getWarpName()));
						RRRP2.teleportingPlayers.remove(player.getID());
					}
				}).delay(ReadConfigTeleport.getTeleportCooldown(), TimeUnit.SECONDS).name("RRRP2 - Back Timer").submit(Sponge.getGame().getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
			} else {
				player.setLastlocationUpdate(Utilities.convertLocation(source));

				if (source.getWorld().getName().equals(warp.getWorld())) {
					source.setTransform(getWarpTransform(warp));
				}
				else {
					Transform<World> temp = getWarpTransform(warp);
					source.transferToWorld(temp.getExtent().getName(), temp.getPosition());
					source.setTransform(temp);
				}
				source.sendMessage(Text.of(TextColors.GOLD, "Teleported to Warp: ", TextColors.GRAY, warp.getWarpName()));
			}
		}
		catch (PositionOutOfBoundsException e) {
			source.sendMessage(Text.of(TextColors.RED, "Error! Warp is at invalid coordinates!"));
			return false;
		}
		return true;
	}
	
	private Transform<World> getWarpTransform(WarpCore warp) {
		World world = Sponge.getServer().getWorld(warp.getWorld()).orElse(null);
		double x = warp.getX();
		double y = warp.getY();
		double z = warp.getZ();
		double pitch = warp.getPitch();
		double yaw = warp.getYaw();
		double roll = warp.getRoll();

		if (world != null) {
			return new Transform<>(world, new Vector3d(x, y, z), new Vector3d(pitch, yaw, roll));
		} else {
			return null;
		}
		
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "Warp", "warp" };
	}

	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Teleport;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("warpname"))))
				.executor(this)
				.build();
	}
}
