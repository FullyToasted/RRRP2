package net.re_renderreality.rrrp2.cmd.teleport.warp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.WarpCore;

public class DeleteWarpCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/deletewarp";
		description = "Deletes a warp location";
		perm = "rrr.admin.warp";
		useage = "/deletewarp <WarpName>";
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
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<String> warpName = ctx.<String> getOne("warpname");
		Player source = (Player) src;
		
		if(src instanceof Player) {
			if(warpName.isPresent()) {
				String warpname = warpName.get();
				if(Database.getWarpExist(warpname)) {
					WarpCore warpcore = Database.getWarp(warpname);
					warpcore.delete();
					source.sendMessage(Text.of(TextColors.GOLD, "Warp deleted successfully!"));
					return CommandResult.empty();
				} else {
					source.sendMessage(Text.of(TextColors.RED, "Warp not found!"));
					return CommandResult.empty();
				}
			} else {
				source.sendMessage(Text.of(TextColors.RED, "Need to specify warp name"));
				return CommandResult.empty();
			}
		} else {
			source.sendMessage(Text.of(TextColors.RED, "If you want to delete a warp please use /managehomes"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "DeleteWarp", "Deletewarp", "deleteWarp", "deletewarp", "rmwarp", "RMwarp", "rmWarp", "RMWarp", "delwarp", "DelWarp", "Delwarp", "delWarp" };
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
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("warpname")))))
				.executor(this).build();
	}
}
