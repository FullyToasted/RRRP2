package net.re_renderreality.rrrp2.cmd.general;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.PluginInfo;
import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigTeleport;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class BackCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/back";
		description = "Teleports you back to your last teleport location";
		perm = "rrr.general.back";
		useage = "/back";
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
	
	//Teleports the player to their last known location
	//A last location is set at login, death, or a teleport
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		if (src instanceof Player) {
			Player player = (Player) src;
			int id = Database.getID(player.getUniqueId().toString());
			PlayerCore playerz = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
			String lastLocation = Utilities.convertLocation(player);

			if (!(playerz.getLastlocation() == null)) {
				Location<World> location = Utilities.convertLocation(playerz.getLastlocation());

				//teleport cool down if player doesn't have override
				if (ReadConfigTeleport.isTeleportCooldownEnabled() && !player.hasPermission("rrp2.teleport.cooldown.override")) {
					RRRP2.teleportingPlayers.add(playerz.getID());
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleporting to Last Location. Please wait " + ReadConfigTeleport.getTeleportCooldown() + " seconds."));
					
					Sponge.getScheduler().createTaskBuilder().execute(() -> {
						if(RRRP2.teleportingPlayers.contains(playerz.getID())) {
							if (player.getLocation().getExtent().getUniqueId().equals(location.getExtent().getUniqueId()))
								player.setLocation(location);
							else
								player.transferToWorld(location.getExtent().getUniqueId(), location.getPosition());
							RRRP2.teleportingPlayers.remove(playerz.getID());
						}
					}).delay(ReadConfigTeleport.getTeleportCooldown(), TimeUnit.SECONDS).name("RRRP2 - Back Timer").submit(Sponge.getGame().getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
				} else {
					if (player.getLocation().getExtent().getUniqueId().equals(location.getExtent().getUniqueId()))
						player.setLocation(location);
					else
						player.transferToWorld(location.getExtent().getUniqueId(), location.getPosition());
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to Last Location."));
				}
			} else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Last death location not found!"));
				return CommandResult.empty();
			}
			playerz.setLastlocation(lastLocation);
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /back!"));
		}
		
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "back", "Back" };
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
				.executor(this).build();
	}
}

