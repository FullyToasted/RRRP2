package net.re_renderreality.rrrp2.cmd.teleport.warp;

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
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.database.core.WarpCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class ManageWarpsCommand  extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tphere";
		description = "Manage the warp list";
		perm = "rrr.admin.manager.warp";
		useage = "/tphere (goto|delete|inspect) (WarpID)";
		notes = "Typing the command without arguments will list all warps";
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
	 * Explanation of what command does and if complicated how to do it
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<Integer> oCommand = ctx.<Integer> getOne("Command");
		Optional<Integer> hID = ctx.<Integer> getOne("HomeID");
		
		if(src instanceof Player) {
			Player player = (Player) src;
			PlayerCore players = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(player.getName());
			if(hID.isPresent() && oCommand.isPresent()) {
				int command = oCommand.get();
				if(command == 1 || command == 2 || command == 3) {
					WarpCore warp = Database.getWarpByID(hID.get());
					if(command == 1) {
						players.setLastlocationUpdate(Utilities.convertLocation(player));

						if (player.getWorld().getName().equals(warp.getWorld())) {
							player.setTransform(getWarpTransform(warp));
						} else {
							Transform<World> temp = getWarpTransform(warp);
							player.transferToWorld(temp.getExtent().getName(), temp.getPosition());
							player.setTransform(temp);
						}
						player.sendMessage(Text.of(TextColors.GOLD, "Teleported to Warp: ", TextColors.GRAY, warp.getID()));
						return CommandResult.success();						
					} else if (command == 2) {
						warp.delete();
						src.sendMessage(Text.of(TextColors.GOLD, "Warp deleted sucessfully!"));
						return CommandResult.success();
					} else if (command == 3) {
						src.sendMessage(warp.toText());
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
				src.sendMessage(Text.of(TextColors.RED, "Error! Correct Useage /ManageWarps <Command> <WarpID>"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Must be a Player to issue this command"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}
	
	private void getHomesList(Player src) {
		ArrayList<WarpCore> warps = new ArrayList<WarpCore>();
		warps = Database.getWarps();
		ArrayList<Text> text = new ArrayList<Text>();
		for(WarpCore w : warps) {
			text.add(Text.of(TextColors.GOLD, "WarpID: ", TextColors.GRAY, w.getID(), TextColors.GOLD,
											  " Warp Name: ", TextColors.GRAY, w.getWarpName(), TextColors.GOLD, 
											  " Creator: ", TextColors.GRAY, w.getCreator(), TextColors.GOLD,
											  " Time Created: ", TextColors.GRAY, w.getWorld(), TextColors.GOLD));
		}
		Iterable<Text> completedText = text;
		sendPagination(completedText, src);
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
	
	private void sendPagination(Iterable<Text> homes, CommandSource src) {
		Utilities.getPaginationService().builder()
	    	.title(Text.of(TextColors.GOLD, "Warps"))
	    	.contents(homes)
	    	.footer(Text.of(TextColors.GREEN, "To go to a home /warp <name>"))
	    	.sendTo(src);
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ManageWarps", "Managewarps", "managewarps", "manageWarps"};
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
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("goto", 1);
		map.put("delete", 2);
		map.put("inspect", 3);
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Command"), map)),
						GenericArguments.optional(GenericArguments.integer(Text.of("WarpID"))))
			.executor(this)
			.build();
	}
}
