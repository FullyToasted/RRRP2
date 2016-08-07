package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class GodCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/god";
		description = "Makes the target invincible";
		perm = "rrr.cheat.god.self";
		useage = "/god (target)";
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
		//This command just toggles a player core boolean actual functionality is in the damage listener
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");
		if(!targetPlayer.isPresent() && src.hasPermission("rrr.cheat.god.self") && src instanceof Player) {
			Player player = (Player) src;
			PlayerCore playerz = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
			if(playerz.getGod()) {
				playerz.setGodUpdate(false);
				player.sendMessage(Text.of(TextColors.GOLD, "Toggled Godmode: ", TextColors.GRAY, "off."));
			} else {
				playerz.setGodUpdate(true);
				player.sendMessage(Text.of(TextColors.GOLD, "Toggled Godmode: ", TextColors.GRAY, "on."));
			}
		} else if (src.hasPermission("rrr.cheat.god.others")) {
			Player player = targetPlayer.get();
			PlayerCore playerz = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
			if (playerz.getGod())
			{
				src.sendMessage(Text.of(TextColors.GOLD, "Toggled Godmode for " + player.getName() + ": ", TextColors.GRAY, "off."));
				player.sendMessage(Text.of(TextColors.GOLD, "Toggled Godmode: ", TextColors.GRAY, "off."));
				playerz.setGodUpdate(false);
			} else {
				src.sendMessage(Text.of(TextColors.GOLD, "Toggled Godmode for " + player.getName() + ": ", TextColors.GRAY, "on."));
				player.sendMessage(Text.of(TextColors.GOLD, "Toggled Godmode: ", TextColors.GRAY, "on."));
				playerz.setGodUpdate(true);
			}
		
		}else if(!targetPlayer.isPresent() && src instanceof ConsoleSource) {
			src.sendMessage(Text.of(TextColors.RED, "Console can only set Players to Godmode"));
			return CommandResult.empty();
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to change others ability to enter Godmode."));
			return CommandResult.empty();
		}
		return CommandResult.success();
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "god", "GOD", "God" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Cheater;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of("description"))
			.permission(perm)
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
			.executor(this)
			.build();
	}
}
