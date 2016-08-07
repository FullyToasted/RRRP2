package net.re_renderreality.rrrp2.cmd.administration;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.Log;

public class SocialSpyCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/socialspy";
		description = "Allows you to see peoples private conversations";
		perm = "rrr.admin.socialspy";
		useage = "/socialspy";
		notes = "Will turn off when you log out";
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
		if(src instanceof Player) {
			Player player = (Player) src;
			Log.debug(RRRP2.socialSpy.toString());
			if(!RRRP2.socialSpy.contains(player)) {
				RRRP2.socialSpy.add(player);
				src.sendMessage(Text.of(TextColors.GOLD, "Social Spy: ", TextColors.GREEN, "ENABLED"));
			} else {
				RRRP2.socialSpy.remove(player);
				src.sendMessage(Text.of(TextColors.GOLD, "Social Spy: ", TextColors.RED, "DISABLED"));
			}
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Only a Player can use this command!"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "SocialSpy", "socialspy"};
		
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
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
}
