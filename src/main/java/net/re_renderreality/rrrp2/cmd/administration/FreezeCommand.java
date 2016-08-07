package net.re_renderreality.rrrp2.cmd.administration;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.AsyncCommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class FreezeCommand extends AsyncCommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/freeze";
		description = "Freeze a player in place";
		perm = "rrr.admin.freeze";
		useage = "/freeze <player>";
		notes = "/freeze (all|freeall) can also be used";
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
	
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx) {
		setLocalVariables();
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");
		Optional<String> all = ctx.<String> getOne("all");
		
		//freezes one player
		if(targetPlayer.isPresent()) {
			Player target = targetPlayer.get();
			PlayerCore playercore = Registry.getOnlinePlayers().getPlayerCorefromUsername(target.getName());
		
			if(RRRP2.frozenPlayers.contains(playercore.getID())) {
				RRRP2.frozenPlayers.remove(playercore.getID());
				src.sendMessage(Text.of(TextColors.GRAY, playercore.getName(), TextColors.GOLD, " is now frozen."));
				target.sendMessage(Text.of(TextColors.GOLD, "You have been freed!"));
			}
			else {
				RRRP2.frozenPlayers.add(playercore.getID());
				src.sendMessage(Text.of(TextColors.GRAY, playercore.getName(), TextColors.GOLD, " is now frozen."));
				target.sendMessage(Text.of(TextColors.GOLD, "You have been frozen!"));
			}
		} else if(all.isPresent()) {
			//freezes all players
			if(all.get().equals("All") || all.get().equals("all")) {
				src.sendMessage(Text.of(TextColors.GOLD, "Freezing all online players"));
				for (Player target : Registry.getGame().getServer().getOnlinePlayers()) {
					PlayerCore playercore = Registry.getOnlinePlayers().getPlayerCorefromUsername(target.getName());
					
					if(target.equals(src)) {
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot freeze yourself!"));
					} else if(!RRRP2.frozenPlayers.contains(playercore.getID())) {
						RRRP2.frozenPlayers.add(playercore.getID());
						target.sendMessage(Text.of(TextColors.GOLD, "You have been frozen!"));
					}
				}
			//un-freezes all players
			} else if(all.get().equals("FreeAll") || all.get().equals("freeall")) {
				src.sendMessage(Text.of(TextColors.GOLD, "Freeing all online players"));
				for (Player target : Registry.getGame().getServer().getOnlinePlayers()) {
					PlayerCore playercore = Registry.getOnlinePlayers().getPlayerCorefromUsername(target.getName());
					
					if(RRRP2.frozenPlayers.contains(playercore.getID())) {
						RRRP2.frozenPlayers.remove(playercore.getID());
						target.sendMessage(Text.of(TextColors.GOLD, "You have been freed!"));
					}
				}
			} else {
				src.sendMessage(Text.of(TextColors.RED, "ERROR! Correct format /freeze <Player|all|freeall>"));
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "ERROR! Correct format /freeze <Player|all|freeall>"));
		}
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "freeze", "Freeze" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
							GenericArguments.onlyOne(GenericArguments.string(Text.of("all")))))
				.executor(this)
				.build();
	}
}
