package net.re_renderreality.rrrp2.cmd.warp;

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
import net.re_renderreality.rrrp2.database.core.WarpCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class ListWarpsCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player) {
			Player source = (Player) src;
			ArrayList<WarpCore> warps = Database.getWarps();
			ArrayList<Text> text = new ArrayList<Text>();
			for(WarpCore w : warps) {
				if(source.hasPermission("rrr.general.warp." + w.getWarpName())) {
					text.add(Text.of(TextColors.GOLD, "Warp Name: ", TextColors.GRAY, w.getWarpName(), TextColors.GOLD, 
													  " World: ", TextColors.GRAY, w.getWorld(), TextColors.GOLD, 
													  " X: ", TextColors.GRAY, Math.floor(w.getX()), TextColors.GOLD, 
													  " Y: ", TextColors.GRAY, Math.floor(w.getY()), TextColors.GOLD,
													  " Z: ", TextColors.GRAY, Math.floor(w.getZ())));
				}
			}
			Iterable<Text> completedText = text;
			sendPagination(completedText, src);
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Need to be a player to list warps"));
			return CommandResult.success();
		}
	}

	private void sendPagination(Iterable<Text> homes, CommandSource src) {
		Utilities.getPaginationService().builder()
	    	.title(Text.of(TextColors.GOLD, "Homes"))
	    	.contents(homes)
	    	.footer(Text.of(TextColors.GREEN, "To go to a warp /warp <name>"))
	    	.sendTo(src);
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "ListWarps", "Listwarps", "listWarps", "listwarps" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of("Set's Players Home"))
				.permission("rrr.general.warp.list")
				.executor(this).build();
	}
}