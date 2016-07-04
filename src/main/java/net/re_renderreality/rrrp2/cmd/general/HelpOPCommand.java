package net.re_renderreality.rrrp2.cmd.general;

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
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

public class HelpOPCommand extends CommandExecutorBase{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/helpop";
		description = "Opens a support ticket with the sever";
		perm = "rrr.general.helpop";
		useage = "/helpop <message>";
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
		//send a ticket to the ingame ticket system
		Optional<String> themsg = ctx.<String> getOne("Msg");
		if(src instanceof Player) {
			if(themsg.isPresent()) {
				String msg = themsg.get();
				int id = Database.findNextID("helpop");
				for (Player p:Registry.getServer().getOnlinePlayers()) {
					if (p.hasPermission("rrr.admin.helpop.recieve")) {
						p.sendMessage(Text.of("Player, " + src.getName() + ", requests help. Ticket# " + id + ": " + msg)); //sends them the message
						src.sendMessage(Text.of(TextColors.GREEN, "Message has been sent successfully!"));
					}
				}
				Player player = (Player) src;
				String command = "INSERT INTO helpop VALUES (" + id + ", '" + player.getName() + "', '" + msg + "', " + Utilities.boolToInt(false) + ")";
				Database.execute(command);
				return CommandResult.success();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Only a Player can Execute this command."));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "HelpOP", "helpOP", "helpop", "Helpop"};
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.remainingJoinedStrings(Text.of("Msg")))
				.executor(this).build();
	}
}
