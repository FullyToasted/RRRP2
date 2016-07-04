package net.re_renderreality.rrrp2.cmd.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import net.re_renderreality.rrrp2.database.core.MailCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class ManageMailCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/managemail";
		description = "Allows an admin to manage all of the players' mail";
		perm = "rrr.admin.manager.mail";
		useage = "/managemail (read|delete) (MailID)";
		notes = "Using no arguments will list all mail";
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
	/**
	 * Explanation of what command does and if complicated how to do it
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<Integer> oCommand = ctx.<Integer> getOne("Command");
		Optional<Integer> OID = ctx.<Integer> getOne("mailID");
		
		if(src instanceof Player) {
			Player player = (Player) src; 
			PlayerCore players = Registry.getOnlinePlayers().getPlayerCorefromUsername(player.getName());
			if(OID.isPresent() && oCommand.isPresent()) {
				int command = oCommand.get();
				int id = OID.get();
				if(command == 1 || command == 2) {
					MailCore mail = Database.getOneMail(id);
					if(command == 1) { //read
						src.sendMessage(Text.of(TextColors.GOLD, "Sender: ", TextColors.GRAY, mail.getSenderName(), TextColors.GOLD, " Sent: ", TextColors.GRAY, mail.getSentTime(), TextColors.GOLD, " Message: ", TextColors.GRAY, mail.getMessage()));
						CommandResult.success();
					} else if (command == 2) { //delete
						mail.delete(players);
						src.sendMessage(Text.of(TextColors.GOLD, "Message deleted sucessfully!"));
						CommandResult.success();
					}
				} else {
					//lists mail in order or receiving and color codes them
					printMail((Player) src);
					return CommandResult.success();
				}
			} else if(!oCommand.isPresent()) {
				//lists mail in order or receiving and color codes them
				printMail((Player) src);
				return CommandResult.success();
			} else {
				src.sendMessage(Text.of(TextColors.RED, "Error! Correct Useage /ManageMail <Command> <MailID>"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Must be a Player to issue this command"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}

	private void printMail(Player src) {
		ArrayList<MailCore> mail = new ArrayList<MailCore>();
		mail = Database.getMail();
		ArrayList<Text> text = new ArrayList<Text>();
		for(MailCore m : mail) {
			if(m.getRead()) {
				text.add(Text.of(TextColors.GOLD, "MailID: ", TextColors.GRAY, m.getMailID(), TextColors.GOLD, " Receiver: ", TextColors.GRAY, m.getRecepientName(), TextColors.GOLD, " On: ", TextColors.GRAY, m.getSentTime()));
			} else {
				text.add(Text.of(TextColors.GOLD, "MailID: ", TextColors.GREEN, m.getMailID(), TextColors.GOLD, " Receiver: ", TextColors.GREEN, m.getRecepientName(), TextColors.GOLD, " On: ", TextColors.GREEN, m.getSentTime()));
			}
		}
		Iterable<Text> completedText = text;
		sendPagination(completedText, src);
	}
	
	private void sendPagination(Iterable<Text> mail, CommandSource src) {
		Utilities.getPaginationService().builder()
	    	.title(Text.of(TextColors.GOLD, "Server MailBox"))
	    	.contents(mail)
	    	.footer(Text.of(TextColors.GREEN, "To Read a mail type /ManageMail read <MailID>"))
	    	.sendTo(src);
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ManageMail", "Managemail", "managemail", "manageMail"};
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Admin;
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("read", 1);
		map.put("delete", 2);
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Command"), map)),
						GenericArguments.optional(GenericArguments.integer(Text.of("mailID"))))
			.executor(this)
			.build();
	}
}