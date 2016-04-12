package net.re_renderreality.rrrp2.cmd.teleport.admintp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfigSpawn;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class TeleportWorldCommand extends CommandExecutorBase {
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException 
	{
		Optional<String> optionalWorld = ctx.<String> getOne("world");
		Optional<String> optionalWarp = ctx.<String> getOne("World");
		Player p = (Player) src;
		
		if(src instanceof Player) {
			if (optionalWorld.isPresent()) {
				Optional<World> world = Sponge.getServer().getWorld(optionalWorld.get());
				if(world.isPresent()) {
					tp(world.get(), p);
					return CommandResult.success();
				}
			} else if (!optionalWorld.isPresent() && optionalWarp.isPresent()) {
				String warp = optionalWarp.get();
				if (warp.equals("Overworld")) {
					String defaultW = Registry.getServer().getDefaultWorldName();
					Optional<World> defaultWorld = Registry.getServer().getWorld(defaultW);
					if(defaultWorld.isPresent()) {
						tp(defaultWorld.get(), p);
						return CommandResult.success();
					}
				} else if (warp.equals("Nether")) {
					String defaultW = "DIM-1";
					Optional<World> defaultWorld = Registry.getServer().getWorld(defaultW);
					if(defaultWorld.isPresent()) {
						tp(defaultWorld.get(), p);
						return CommandResult.success();
					}		
				} else if (warp.equals("End")) {
					String defaultW = "DIM1";
					Optional<World> defaultWorld = Registry.getServer().getWorld(defaultW);
					if(defaultWorld.isPresent()) {
						tp(defaultWorld.get(), p);
						return CommandResult.success();
					}
				} else {
					
				}
			} else {
				p.sendMessage(Text.of(TextColors.RED, "Error! World not found!"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Consoles cannot teleport..."));
		}
		return CommandResult.empty();
	}
			
	
	public void tp(World world, Player p) {
		Transform<World> spawn = ReadConfigSpawn.getSpawn(world.getName());
		if(!(spawn == null)) {
			if (!p.getWorld().getUniqueId().equals(world.getUniqueId())) {
				PlayerCore playerTarget = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(p.getName());
				playerTarget.setLastlocationUpdate(Utilities.convertLocation(p));
				p.transferToWorld(world.getUniqueId(), spawn.getPosition());
			}
			else {
				PlayerCore playerTarget = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(p.getName());
				playerTarget.setLastlocationUpdate(Utilities.convertLocation(p));
				p.setLocation(spawn.getLocation());
			}
	
			p.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to location!"));
		} else {
			p.sendMessage(Text.of(TextColors.RED, "Spawn not set in Dimension: ", TextColors.GRAY, world.getName()));
			p.sendMessage(Text.of(TextColors.RED, "Contact a server admin to set spawn"));
		}
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "TPworld", "TPWorld", "tpWorld", "tpWorld" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Overworld", "Overworld");
		map.put("Nether", "Nether");
		map.put("End", "End");
		for(World w : Registry.getServer().getWorlds()) {
			if(!(w.getName().equals("world") || w.getName().equals("DIM1") || w.getName().equals("DIM-1"))) {
				map.put(w.getName(), w.getName());
			}
		}
		return CommandSpec.builder()
				.description(Text.of("TP All Operator Command"))
				.permission("rrr.admin.tpworld")
				.arguments(GenericArguments.firstParsing(GenericArguments.choices(Text.of("World"), map),
								GenericArguments.onlyOne(GenericArguments.string(Text.of("world")))))
				.executor(this)
				.build();
	}
}
