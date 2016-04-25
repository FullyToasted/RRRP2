package net.re_renderreality.rrrp2.cmd.warp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.WarpCore;

public class SetWarpCommand extends CommandExecutorBase {
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException	{
		Optional<String> warpName = ctx.<String> getOne("warpName");
		
		if(src instanceof Player) {
			Player source = (Player) src;
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String todaysDate = dateFormat.format(cal.getTime());
			
			String name = warpName.get();
			
			WarpCore warp = new WarpCore();
			if(Database.getWarpExist(name)) {
				warp = Database.getWarp(name);
				setWarp(warp, source);
				warp.update();
				src.sendMessage(Text.of(TextColors.GOLD, "Warp Overriden!"));
				return CommandResult.success();
			}
			int nextID = Database.findNextID("homes");
			
			warp.setID(nextID);
			warp.setCreator(source.getName());
			warp.setTimeCreated(todaysDate);
			warp.setWarpName(name);
			setWarp(warp, source);
			warp.insert();
			src.sendMessage(Text.of(TextColors.GOLD, "A new warp has been set!"));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

	private void setWarp(WarpCore home, Player source) {
		home.setWorld(source.getWorld().getName());
		home.setX(source.getTransform().getLocation().getX());
		home.setY(source.getTransform().getLocation().getY());
		home.setZ(source.getTransform().getLocation().getZ());
		home.setYaw(source.getTransform().getYaw());
		home.setPitch(source.getTransform().getPitch());	
		home.setRoll(source.getTransform().getRoll());
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "SetWarp", "Setwarp", "setWarp", "setwarp" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of("Set's Players Home"))
				.permission("rrr.admin.warp.set")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("warpName"))))
				.executor(this)
				.build();
	}
}