package net.re_renderreality.rrrp2.cmd.home;

import java.util.ArrayList;

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
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.HomeCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class ListHomesCommand extends CommandExecutorBase {
	//Lists all of a player's current homes
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		if (src instanceof Player) {
			Player source = (Player) src;
			ArrayList<HomeCore> homes = Database.getHomesByPlayer(source);
			ArrayList<Text> text = new ArrayList<Text>();
			for(HomeCore h : homes) {
				text.add(Text.of(TextColors.GOLD, "Home Name: ", TextColors.GRAY, h.getHomeName(), TextColors.GOLD, 
												  " World: ", TextColors.GRAY, h.getWorld(), TextColors.GOLD, 
												  " X: ", TextColors.GRAY, Math.floor(h.getX()), TextColors.GOLD, 
												  " Y: ", TextColors.GRAY, Math.floor(h.getY()), TextColors.GOLD,
												  " Z: ", TextColors.GRAY, Math.floor(h.getZ())));
			}
			Iterable<Text> completedText = text;
			sendPagination(completedText, src);
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Need to be a player to list your homes"));
			return CommandResult.success();
		}
	}

	private void sendPagination(Iterable<Text> homes, CommandSource src) {
		Utilities.getPaginationService().builder()
	    	.title(Text.of(TextColors.GOLD, "Homes"))
	    	.contents(homes)
	    	.footer(Text.of(TextColors.GREEN, "To go to a home /home <name>"))
	    	.sendTo(src);
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "ListHomes", "Listhomes", "listHomes", "listhomes" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of("Set's Players Home"))
				.permission("rrr.general.home")
				.executor(this).build();
	}
}
