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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

public class ManageHelpOP extends CommandExecutorBase{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/managehelpop";
		description = "Manage helpop tickets";
		perm = "rrr.admin.manager.helpop";
		useage = "/managehelpop (remove|resolve|get) (HelpopID)";
		notes = "Passing no arguments will list the tickets";
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
		Optional<Integer> ids = ctx.<Integer> getOne("ID");
		Optional<Integer> choice = ctx.<Integer> getOne("Choice");
		
		if(choice.isPresent()) {
			if(choice.get() == 1) { //remove helpop
				if(ids.isPresent()) {
					int id = ids.get();
					String command = "DELETE FROM helpop WHERE ID = " + id + ";";
					Database.execute(command);
					src.sendMessage(Text.of(TextColors.GOLD, "Ticket sucessfully deleted from database"));
					return CommandResult.success();
				}
			} else if (choice.get() == 2) { //resolves helpop
				if(ids.isPresent()) {
					int id = ids.get();
					String command = "UPDATE helpop SET Resolved = 1 WHERE ID = " + id + ";";
					Database.execute(command);
					src.sendMessage(Text.of(TextColors.GOLD, "Ticket sucessfully marked as resolved"));
					return CommandResult.success();
				}		
			} else if (choice.get() == 3) { //reopen helpop
				if(ids.isPresent()) {
					int id = ids.get();
					String command = "UPDATE helpop SET Resolved = 0 WHERE ID = " + id + ";";
					Database.execute(command);
					src.sendMessage(Text.of(TextColors.GOLD, "Ticket sucessfully marked as resolved"));
					return CommandResult.success();
				}	
			} else if (choice.get() == 4) { //get helpop info
				if(ids.isPresent()) {
					int id = ids.get();
					ArrayList<Text> array = Database.getHelpOp(id);
					for(Text s: array) {
						src.sendMessage(s);
					}
					return CommandResult.success();
				}
			}else if (choice.get() == 5) { //shows all open helpops or all closed ones
				Iterable<Text> allOpenTickets = Database.getAllOpenTickets("All"); //choice gets handled by database.java
				Utilities.getPaginationService().builder()
				 	.title(Text.of(TextColors.BLUE, "All Support Tickets"))
			    	.contents(allOpenTickets)
			    	.footer(Text.of(TextColors.GREEN, "To Remove a ticket type /ManageHelpOP remove <TicketNumber>"))
				   	.sendTo(src);
				return CommandResult.success();
			} else if (choice.get() == 6) { //gets all closed tickets
				Iterable<Text> allOpenTickets = Database.getAllOpenTickets("Resolved");
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.BLUE, "All Closed Support Tickets"))
					   .contents(allOpenTickets)
					   .footer(Text.of(TextColors.GREEN, "Good Job Staff :)"))
					   .sendTo(src);
				return CommandResult.success();
			} else { //gets all open tickets
				src.sendMessage(Text.of(TextColors.RED, "You did not pick a valid option!"));					
			}
		} else { //gets all open tickets
			Iterable<Text> allOpenTickets = Database.getAllOpenTickets();
			Utilities.getPaginationService().builder()
				.title(Text.of(TextColors.BLUE, "All Open Support Tickets"))
			    .contents(allOpenTickets)
			    .footer(Text.of(TextColors.GREEN, "To Close a ticket type /ManageHelpOP resolve <TicketNumber>"))
			    .sendTo(src);
			return CommandResult.success();
		}
		return CommandResult.empty();
	}
	

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ManageHelpOP", "Managehelpop", "managehelpop", "ManageHelpop"};
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
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("remove", 1);
		map.put("resolve", 2);
		map.put("reopen", 3);
		map.put("get", 4);
		map.put("getall", 5);
		map.put("getresolved", 6);
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm) //remove<ID>/resolve<ID>/get<ID>/getall
				.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Choice"), map)),(GenericArguments.optional(GenericArguments.integer(Text.of("ID")))))
				.executor(this).build();
	}
}

