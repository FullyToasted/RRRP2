package net.re_renderreality.rrrp2.cmd.home;

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
import net.re_renderreality.rrrp2.database.core.HomeCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class HomeCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/home";
		description = "Request a player to teleort to your location";
		perm = "rrr.general.home";
		useage = "/home <homename>";
		notes = "You can see your homes with /listhomes";
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
	
	//creates a new HomeCore and inserts it into database
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		Optional<String> homeName = ctx.<String> getOne("homename");
		Player source = (Player) src;
		PlayerCore player = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(source.getName());
		
		if(src instanceof Player) {
			if(homeName.isPresent()) {
				String homename = homeName.get();
				if(Database.getHomeExist(source, homename)) { //Import Transform and tp player
					HomeCore home = Database.getHome(source, homename);
					if(teleportPlayer(source, player, home)) {
						return CommandResult.success();
					}
				}
				else {
					src.sendMessage(Text.of(TextColors.RED, "You must first set your home!"));
					CommandResult.empty();
				}
			} else {
				String homename = "main";
				if(Database.getHomeExist(source, homename)) { //Import Transform and tp player
					HomeCore home = Database.getHome(source, homename);
					if(teleportPlayer(source, player, home)) {
						return CommandResult.success();
					}
				}
			}
		} else {
			source.sendMessage(Text.of(TextColors.RED, "You cant be teleported to a home"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}
	
	private boolean teleportPlayer(Player source, PlayerCore player, HomeCore home) {
		try {
			if (ReadConfigTeleport.isTeleportCooldownEnabled() && !source.hasPermission("rrr.general.teleport.cooldownoverride")) {
				RRRP2.teleportingPlayers.add(player.getID());
				source.sendMessage(Text.of(TextColors.GOLD, "Teleporting to Home. Please wait ",TextColors.GRAY, ReadConfigTeleport.getTeleportCooldown(), TextColors.GOLD, " seconds."));

				Sponge.getScheduler().createTaskBuilder().execute(() -> {
					if (RRRP2.teleportingPlayers.contains(player.getID())) {
						player.setLastlocationUpdate(Utilities.convertLocation(source));

						if (source.getWorld().getName().equals(home.getWorld())) {
							source.setTransform(getHomeTransform(home));
						}
						else {
							Transform<World> temp = getHomeTransform(home);
							source.transferToWorld(temp.getExtent().getName(), temp.getPosition());
							source.setTransform(temp);
						}

						source.sendMessage(Text.of(TextColors.GOLD, "Teleported to Home: ", TextColors.GRAY, home.getHomeName()));
						RRRP2.teleportingPlayers.remove(player.getID());
					}
				}).delay(ReadConfigTeleport.getTeleportCooldown(), TimeUnit.SECONDS).name("RRRP2 - Back Timer").submit(Sponge.getGame().getPluginManager().getPlugin(PluginInfo.ID).get().getInstance().get());
			} else {
				player.setLastlocationUpdate(Utilities.convertLocation(source));

				if (source.getWorld().getName().equals(home.getWorld())) {
					source.setTransform(getHomeTransform(home));
				}
				else {
					Transform<World> temp = getHomeTransform(home);
					source.transferToWorld(temp.getExtent().getName(), temp.getPosition());
					source.setTransform(temp);
				}
				source.sendMessage(Text.of(TextColors.GOLD, "Teleported to Home: ", TextColors.GRAY, home.getHomeName()));
			}
		}
		catch (PositionOutOfBoundsException e) {
			source.sendMessage(Text.of(TextColors.RED, "Error! Home is at invalid coordinates!"));
			return false;
		}
		return true;
	}
	
	private Transform<World> getHomeTransform(HomeCore home) {
		World world = Sponge.getServer().getWorld(home.getWorld()).orElse(null);
		double x = home.getX();
		double y = home.getY();
		double z = home.getZ();
		double pitch = home.getPitch();
		double yaw = home.getYaw();
		double roll = home.getRoll();

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
		return new String[] { "Home", "home" };
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
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("homename")))))
				.executor(this).build();
	}
}