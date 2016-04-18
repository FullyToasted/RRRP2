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
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx) {
		Optional<Player> targetPlayer = ctx.<Player> getOne("player");
		Optional<String> all = ctx.<String> getOne("all");
		
		if(targetPlayer.isPresent()) {
			Player target = targetPlayer.get();
			int id = RRRP2.getRRRP2().getOnlinePlayer().getIDfromUsername(target.getName());
			PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
		
			if(RRRP2.frozenPlayers.contains(playercore.getID())) {
				RRRP2.frozenPlayers.remove(playercore.getID());
				src.sendMessage(Text.of(TextColors.GRAY, playercore.getName(), TextColors.GOLD, "is now frozen."));
				target.sendMessage(Text.of(TextColors.GOLD, "You have been freed!"));
			}
			else {
				RRRP2.frozenPlayers.add(playercore.getID());
				src.sendMessage(Text.of(TextColors.GRAY, playercore.getName(), TextColors.GOLD, "is now frozen."));
				target.sendMessage(Text.of(TextColors.GOLD, "You have been frozen!"));
			}
		} else if(all.isPresent()) {
			if(all.get().equals("All") || all.get().equals("all")) {
				src.sendMessage(Text.of(TextColors.GOLD, "Freezing all online players"));
				for (Player target : Registry.getGame().getServer().getOnlinePlayers()) {
					int id = RRRP2.getRRRP2().getOnlinePlayer().getIDfromUsername(target.getName());
					PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
					
					if(target.equals(src)) {
						src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot freeze yourself!"));
					} else if(!RRRP2.frozenPlayers.contains(playercore.getID())) {
						RRRP2.frozenPlayers.add(playercore.getID());
						target.sendMessage(Text.of(TextColors.GOLD, "You have been frozen!"));
					}
				}
			} else if(all.get().equals("FreeAll") || all.get().equals("freeall")) {
				src.sendMessage(Text.of(TextColors.GOLD, "Freeing all online players"));
				for (Player target : Registry.getGame().getServer().getOnlinePlayers()) {
					int id = RRRP2.getRRRP2().getOnlinePlayer().getIDfromUsername(target.getName());
					PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
					
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
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Player Freeze Command"))
				.permission("rrr.admin.freeze")
				.arguments(GenericArguments.firstParsing(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
							GenericArguments.onlyOne(GenericArguments.string(Text.of("all")))))
				.executor(this)
				.build();
	}
}
