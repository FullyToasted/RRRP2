package net.re_renderreality.rrrp2.cmd.teleport.special;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
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

import com.flowpowered.math.vector.Vector3i;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class TopCommand extends CommandExecutorBase {
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player) {
			Player player = (Player) src;
			Location<World> playerLoc = player.getLocation();
			World world = player.getWorld();
			int x = playerLoc.getBlockX();
			int z = playerLoc.getBlockZ();
			int y = 255;
			while(y > playerLoc.getBlockY() ) {
				Vector3i pos = new Vector3i(x, y, z);
		        if (!(world.getBlock(pos).getType().equals(BlockTypes.AIR))) {
		        	Location<World> loc = new Location<World>(world, x, y+1, z);
		            player.setLocation(loc);
		            player.sendMessage(Text.of(TextColors.GOLD, "Teleported to top."));
		            return CommandResult.success();
		        }
		        y--;
			}
			player.sendMessage(Text.of(TextColors.GOLD, "No valid spots found"));
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error: Need to be a player to execute this command"));
		}
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "top", "Top" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("God Command"))
			.permission("rrr.teleport.top")
			.executor(this)
			.build();
	}
}
