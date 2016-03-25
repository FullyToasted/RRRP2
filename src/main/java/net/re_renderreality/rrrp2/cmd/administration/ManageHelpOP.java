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
import net.re_renderreality.rrrp2.utils.Utilities;

public class ManageHelpOP extends CommandExecutorBase{
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
{
	Optional<String> ids = ctx.<String> getOne("ID");
	Optional<Integer> choice = ctx.<Integer> getOne("Choice");
	
	if(choice.isPresent())
		if(choice.get() == 1) {
			if(ids.isPresent()) {
				int id = Integer.parseInt(ids.get());
				String command = "DELETE FROM helpop WHERE ID = " + id + ";";
				Database.execute(command);
				src.sendMessage(Text.of(TextColors.GOLD, "Ticket sucessfully deleted from database"));
				return CommandResult.success();
			}
		} else if (choice.get() == 2) {
			if(ids.isPresent()) {
				int id = Integer.parseInt(ids.get());
				String command = "UPDATE helpop SET Resolved = 1 WHERE ID = " + id + ";";
				Database.execute(command);
				src.sendMessage(Text.of(TextColors.GOLD, "Ticket sucessfully marked as resolved"));
				return CommandResult.success();
			}		
		} else if (choice.get() == 3) {
			if(ids.isPresent()) {
				int id = Integer.parseInt(ids.get());
				String command = "UPDATE helpop SET Resolved = 0 WHERE ID = " + id + ";";
				Database.execute(command);
				src.sendMessage(Text.of(TextColors.GOLD, "Ticket sucessfully marked as resolved"));
				return CommandResult.success();
			}	
		} else if (choice.get() == 4) {
			if(ids.isPresent()) {
				int id = Integer.parseInt(ids.get());
				ArrayList<Text> array = Database.getHelpOp(id);
				for(Text s: array) {
					src.sendMessage(s);
				}
				return CommandResult.success();
			}
		}else if (choice.get() == 5) {
			if(ids.isPresent()) {
				String choices = ids.get();
				Iterable<Text> allOpenTickets = Database.getAllOpenTickets(choices);
				if(choices.equals("All") || choices.equals("all")) {
					Utilities.getPaginationService().builder()
				    	.title(Text.of(TextColors.BLUE, "All Support Tickets"))
				    	.contents(allOpenTickets)
				    	.footer(Text.of(TextColors.GREEN, "To Remove a ticket type /ManageHelpOP remove <TicketNumber>"))
				    	.sendTo(src);
					return CommandResult.success();
				} else {
					Utilities.getPaginationService().builder()
						.title(Text.of(TextColors.BLUE, "All Closed Support Tickets"))
				    	.contents(allOpenTickets)
				    	.footer(Text.of(TextColors.GREEN, "Good Job Staff :)"))
				    	.sendTo(src);
					return CommandResult.success();
				}
			} else if(!(ids.isPresent())){
				Iterable<Text> allOpenTickets = Database.getAllOpenTickets();
				Utilities.getPaginationService().builder()
					.title(Text.of(TextColors.BLUE, "All Open Support Tickets"))
			    	.contents(allOpenTickets)
			    	.footer(Text.of(TextColors.GREEN, "To Close a ticket type /ManageHelpOP resolve <TicketNumber>"))
			    	.sendTo(src);
				return CommandResult.success();
				
			}
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
	public CommandSpec getSpec() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("remove", 1);
		map.put("resolve", 2);
		map.put("reopen", 3);
		map.put("get", 4);
		map.put("getall", 5);
		return CommandSpec.builder()
				.description(Text.of("Request help from Staff"))
				.permission("rrr.general.helpop.recieve") //remove<ID>/resolve<ID>/get<ID>/getall
				.arguments(GenericArguments.choices(Text.of("Choice"), map),(GenericArguments.optional(GenericArguments.string(Text.of("ID")))))
				.executor(this).build();
	}
}

