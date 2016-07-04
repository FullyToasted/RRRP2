package net.re_renderreality.rrrp2.cmd.general;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

public class GetPosCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/getpos";
		description = "Gets your current coordinates";
		perm = "rrr.general.getpos";
		useage = "/getpos";
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
	
	/**
	 * Get player coordinates
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		if(src instanceof Player) {
			Player player = (Player) src;
			src.sendMessage(Text.of(TextColors.GOLD, "You cuurent Location is: ", TextColors.GRAY, Utilities.convertLocation(player)));
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Only a Player can Execute this command."));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "GetPos", "getpos", "Getpos", "getPos" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(perm))
			.permission(description)
			.executor(this)
			.build();
	}

	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}
}
