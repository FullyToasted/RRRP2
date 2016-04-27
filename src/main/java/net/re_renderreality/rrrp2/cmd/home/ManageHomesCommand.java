package net.re_renderreality.rrrp2.cmd.home;

import java.util.ArrayList;
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

import com.flowpowered.math.vector.Vector3d;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.HomeCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class ManageHomesCommand extends CommandExecutorBase {
	/**
	 * Can do any of multiple things. For admins to manipulate other peoples' homes'
	 * TODO: Allow players to search for peoples home by playername
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		Optional<Integer> oCommand = ctx.<Integer> getOne("Command");
		Optional<Integer> hID = ctx.<Integer> getOne("HomeID");
		
		if(src instanceof Player) {
			Player player = (Player) src;
			PlayerCore players = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(player.getName());
			if(hID.isPresent() && oCommand.isPresent()) {
				int command = oCommand.get();
				if(command == 1 || command == 2 || command == 3) {
					HomeCore home = Database.getHomeByID(hID.get());
					if(command == 1) { //goto another players home
						players.setLastlocationUpdate(Utilities.convertLocation(player));

						if (player.getWorld().getName().equals(home.getWorld())) {
							player.setTransform(getHomeTransform(home));
						} else {
							Transform<World> temp = getHomeTransform(home);
							player.transferToWorld(temp.getExtent().getName(), temp.getPosition());
							player.setTransform(temp);
						}
						player.sendMessage(Text.of(TextColors.GOLD, "Teleported to Home: ", TextColors.GRAY, home.getID()));
						return CommandResult.success();						
					} else if (command == 2) { //delete someone elses home
						home.delete();
						src.sendMessage(Text.of(TextColors.GOLD, "Home deleted sucessfully!"));
						return CommandResult.success();
					} else if (command == 3) { //inspect the details of a home
						src.sendMessage(home.toText());
						return CommandResult.success();
					}
				} else {
					getHomesList(player);
					return CommandResult.success();
				}
			} else if(!oCommand.isPresent()){
				getHomesList(player);
				return CommandResult.success();
			} else {
				src.sendMessage(Text.of(TextColors.RED, "Error! Correct Useage /ManageHomes <Command> <HomeID>"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Must be a Player to issue this command"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}
	
	/**
	 * @param src player executing the command
	 */
	private void getHomesList(Player src) {
		ArrayList<HomeCore> homes = new ArrayList<HomeCore>();
		homes = Database.getHomes();
		ArrayList<Text> text = new ArrayList<Text>();
		for(HomeCore h : homes) {
			text.add(Text.of(TextColors.GOLD, "HomeID: ", TextColors.GRAY, h.getID(), TextColors.GOLD,
											  " Name: ", TextColors.GRAY, h.getHomeName(), TextColors.GOLD, 
											  " Player: ", TextColors.GRAY, h.getUsername(), TextColors.GOLD,
											  " World: ", TextColors.GRAY, h.getWorld(), TextColors.GOLD));
		}
		Iterable<Text> completedText = text;
		sendPagination(completedText, src);
	}

	/**
	 * @param home homecore to convert to TPable object
	 * @return Transform<World> for player to be sent to
	 */
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
	
	/**
	 * @param homes List of homes to send to player
	 * @param src player to send list to
	 */
	private void sendPagination(Iterable<Text> homes, CommandSource src) {
		Utilities.getPaginationService().builder()
	    	.title(Text.of(TextColors.GOLD, "Homes"))
	    	.contents(homes)
	    	.footer(Text.of(TextColors.GREEN, "To go to a home /home <name>"))
	    	.sendTo(src);
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ManageHomes", "Managehomes", "managehomes", "manageHomes"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("goto", 1);
		map.put("delete", 2);
		map.put("inspect", 3);
		return CommandSpec.builder()
			.description(Text.of("Manage other players gomes"))
			.permission("rrr.admin.homes.manage")
			.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Command"), map)),
						GenericArguments.optional(GenericArguments.integer(Text.of("HomeID"))))
			.executor(this)
			.build();
	}
}