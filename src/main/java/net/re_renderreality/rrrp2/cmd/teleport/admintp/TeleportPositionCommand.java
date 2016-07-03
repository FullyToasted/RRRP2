package net.re_renderreality.rrrp2.cmd.teleport.admintp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

//tp to a coorinate
public class TeleportPositionCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tppos";
		description = "Teleport to a coordinate";
		perm = "rrr.admin.tppos";
		useage = "/tppos <x> <y> <z> (player) (world)";
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
		Player source = (Player) src;
		Optional<Player> p = ctx.<Player> getOne("player");
		Optional<String> optionalWorld = ctx.<String> getOne("world");
		int x = ctx.<Integer> getOne("x").get();
		int y = ctx.<Integer> getOne("y").get();
		int z = ctx.<Integer> getOne("z").get();
		World world = null;
		if(optionalWorld.isPresent()) {
			world = Sponge.getServer().getWorld(optionalWorld.get()).orElse(null);
			if(world == null) {
				world = source.getWorld();
			}
		} else {
			world = source.getWorld();
		}

		if (p.isPresent()) {
			if (src.hasPermission("teleport.pos.others")) {
				int id = RRRP2.getRRRP2().getOnlinePlayer().getIDfromUsername(p.get().getName());
				PlayerCore player = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
				player.setLastlocationUpdate(Utilities.convertLocation(p.get()));

				if(world != null)
					p.get().setLocation(new Location<>(world, x, y, z));
				else
					p.get().setLocation(new Location<>(p.get().getWorld(), x, y, z));

				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player " + p.get().getName() + " to " + x + "," + y + "," + z + "."));
				p.get().sendMessage(Text.of(TextColors.GOLD, "You have been teleported by " + src.getName()));
			}
		} else {
			if (src instanceof Player) {
				Player player = (Player) src;
				int id = RRRP2.getRRRP2().getOnlinePlayer().getIDfromUsername(player.getName());
				PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
				playercore.setLastlocationUpdate(Utilities.convertLocation(player));

				if(world != null)
					player.setLocation(new Location<>(world, x, y, z));
				else
					player.setLocation(new Location<>(player.getWorld(), x, y, z));

				src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to coords."));
			}
			else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "tppos", "teleportpos", "teleportposition", "TPpos", "TPPos", "tpPos" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Teleport;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
			.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.seq(
				GenericArguments.onlyOne(GenericArguments.integer(Text.of("x"))),
				GenericArguments.onlyOne(GenericArguments.integer(Text.of("y"))),
				GenericArguments.onlyOne(GenericArguments.integer(Text.of("z"))),
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))),
				GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("world")))))
			.executor(this).build();
	}
}
