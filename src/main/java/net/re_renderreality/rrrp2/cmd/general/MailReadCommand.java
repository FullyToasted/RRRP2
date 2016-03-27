package net.re_renderreality.rrrp2.cmd.general;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.MailCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;

public class MailReadCommand extends CommandExecutorBase
{
	/**
	 * Explanation of what command does and if complicated how to do it
	 */
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Integer> oCommand = ctx.<Integer> getOne("Command");
		Optional<Integer> OID = ctx.<Integer> getOne("mailID");
		
		if(src instanceof Player) {
			if(OID.isPresent() && oCommand.isPresent()) {
				int command = oCommand.get();
				int id = OID.get();
				Player player = (Player) src;
				int pid = Database.getIDFromDatabase(player.getUniqueId().toString());
				PlayerCore players = RRRP2.getRRRP2().getOnlinePlayer().getPlayer(pid);
				
				if(command == 1 || command == 2) {
					MailCore mail;
					//getmailcore by MailID
					if(command == 1) {
						m.getMessage();
					} else if (command == 2) {
						//delete
					}
				} else {
					ArrayList<MailCore> mail = new ArrayList<MailCore>();
					mail = Database.getMail(players.getID());
					//Get All The MailCores with RecepientID of the src.
					
					src.sendMessage(Text.of());
				}
				
				
			}
		}

	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ReadMail", "Readmail", "readmail", "MailRead", "mailRead", "Mailread"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("read", 1);
		map.put("delete", 2);
		return CommandSpec.builder()
			.description(Text.of("Read Mail that has been received"))
			.permission("rrr.general.mail.receive")
			.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("Command")))),
						GenericArguments.integer(Text.of("mailID")))
			.executor(this)
			.build();
	}
}
