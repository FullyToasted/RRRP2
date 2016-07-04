package net.re_renderreality.rrrp2.cmd.general;

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
import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.AFK;

public class AFKCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/AFK";
		description = "Manually sets your status to away from keyboard";
		perm = "rrr.general.afk";
		useage = "/afk";
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
		//Overrides and instantly causes AFK to execute
		if (src instanceof Player) {
			Player source = (Player) src;
			PlayerCore player = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(source.getName());

			if (RRRP2.afkList.containsKey(player.getID())) {
				RRRP2.afkList.remove(player.getID());
			}

			int timeBeforeAFK = (int) ReadConfig.getAFKTime();
			long timeToSet = System.currentTimeMillis() - timeBeforeAFK - 1000;
			AFK afk = new AFK(timeToSet);
			RRRP2.afkList.put(player.getID(), afk);
		}
		else
		{
			src.sendMessage(Text.of(TextColors.RED, "Must be an in-game player to use /afk!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "afk", "AFK" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
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
