package net.re_renderreality.rrrp2.cmd.general;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;

import javax.annotation.Nonnull;

public class CompassCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/compass";
		description = "Tells Direction you are facing";
		perm = "rrr.general.compass";
		useage = "/compass";
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
		if (src instanceof Player) {
			Player player = (Player) src;
			// Gives the direction the player is looking
			String direction = Direction.getClosest(player.getTransform().getRotationAsQuaternion().getDirection()).toString();
			player.sendMessage(Text.of(TextColors.GOLD, "You are facing: ", TextColors.GRAY, direction));
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! You must be an in-game player to use /direction!"));
		}
		
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "direction", "compass" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.executor(this)
				.build();
	}
}
