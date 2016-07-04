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

public class GetDimCommand extends CommandExecutorBase
{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/getdim";
		description = "Gets dimension name that you are in";
		perm = "rrr.general.getdim";
		useage = "/getdim";
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
	 * shows the dimension type the world a player is in
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		setLocalVariables();
		if(src instanceof Player) {
			Player player = (Player) src;
			src.sendMessage(Text.of(TextColors.GOLD, "You are currently in the: ", TextColors.GRAY, player.getWorld().getDimension().getContext().getName()));
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Only a Player can Execute this command."));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "GetDim", "Getdim", "getDim", "getdim" };
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
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
