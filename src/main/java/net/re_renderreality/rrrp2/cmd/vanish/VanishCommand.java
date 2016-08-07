package net.re_renderreality.rrrp2.cmd.vanish;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class VanishCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/vanish";
		description = "Allows a player to vanish";
		perm = "rrr.admin.vanish";
		useage = "/vanish";
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

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		setLocalVariables();
		if (src instanceof Player) {
			Player player = (Player) src;
			PlayerCore pPlayer = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
			if(player.get(Keys.INVISIBLE).isPresent()) {
				boolean invisible = player.get(Keys.INVISIBLE).get();
				player.offer(Keys.INVISIBLE, !invisible);
				if(!pPlayer.getInvisible()) {
					player.sendMessage(Text.of(TextColors.GOLD, "Toggled Visibillity: ", TextColors.GRAY, "off."));		
					if(RRRP2.invisiblePlayers.contains(player)) {
						RRRP2.invisiblePlayers.remove(player);
					}
				} else {
					player.sendMessage(Text.of(TextColors.GOLD, "Toggled Visibillity: ", TextColors.GRAY, "on."));
						if(!RRRP2.invisiblePlayers.contains(player)) {
							RRRP2.invisiblePlayers.add(player);
						}
					}
					pPlayer.setInvisibleUpdate(!pPlayer.getInvisible());
			}
		}
		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "vanish", "Vanish" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory() {
		return Registry.helpCategory.Admin;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec
				.builder()
				.description(Text.of(description))
				.permission(perm)
				.executor(this).build();
	}
	
}
