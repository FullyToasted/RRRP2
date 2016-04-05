package net.re_renderreality.rrrp2.cmd.administration;

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
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class FreezeCommand extends AsyncCommandExecutorBase {
	@Override
	public void executeAsync(CommandSource src, CommandContext ctx) {
		Player targetPlayer = ctx.<Player> getOne("player").get();
		int id = RRRP2.getRRRP2().getOnlinePlayer().getIDfromUsername(targetPlayer.getName());
		PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(id);
		
		if(targetPlayer.equals(src)) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot freeze yourself!"));
			return;
		}
		
		if(RRRP2.frozenPlayers.contains(playercore.getID())) {
			RRRP2.frozenPlayers.remove(playercore.getID());
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Un-froze player."));
		}
		else {
			RRRP2.frozenPlayers.add(playercore.getID());
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Froze player."));
		}
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "playerfreeze", "freezeplayer" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of("Player Freeze Command"))
				.permission("essentialcmds.playerfreeze.use")
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))
				.executor(this)
				.build();
	}
}
