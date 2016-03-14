package net.re_renderreality.rrrp2.cmd;

import java.time.LocalDate;
import java.time.LocalTime;

import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.main.RRRP2;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * @author Avarai
 * @note Plans: Add nickname support and formerly known as support.
 */
public class WhoisCommand{
	
	private final CommandSource src; //user who used command
	private final CommandContext args; //should be Player Name
	
	/**
	 * @param src CommandSource object of command source.
	 * @param args Arguments given to command
	 */
	public WhoisCommand(CommandSource src, CommandContext args) { this.src = src; this.args = args; } //Constructor
	
	public void run() {
		
		PlayerRegistry register = RRRP2.plugin.getPlayerRegistry(); //imports the player register to use for this class
		String name = args.<String>getOne("Player").get(); //gets the args that were passed in and converts to string
		Text status = Text.of(""); 
		Text lastSeen = Text.of("");
		
		if (register.containsPlayer(name)) { //if player name is in the register 
			if (Utilities.getPlayer(name).isPresent()) {  //if they are present on the server
				status = Text.builder(Utilities.boolToString(true)).color(TextColors.GREEN).build(); //builds Green "TRUE"
				String time = LocalTime.now().toString(); //"Sets last seen to now"
				lastSeen = Text.builder(LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.'))).color(TextColors.BLUE).build(); //Lastseen = date time(in Blue)
			}
			else {
				status = Text.builder(Utilities.boolToString(false)).color(TextColors.RED).build(); //online false(RED)
				String time = register.getTime(register.getUuid(name)); //takes time from registry or sets last seen to "not seen"
				if (!time.isEmpty())
					lastSeen = Text.builder(time).color(TextColors.BLUE).build();
				else
					lastSeen = Text.builder("Not Seen").color(TextColors.BLUE).build();
			}
			src.sendMessage(Text.joinWith(Text.of(""), Text.of(name + " -- Online: "), status, Text.of(" Last seen: ", lastSeen))); //sends message to user
		}
		else {
			src.sendMessage(Text.of("That player has not been seen on the server.")); //tells user that user has not been seen on server
			return;
		}
	}
}