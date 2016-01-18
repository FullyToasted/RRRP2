package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.main.PlayerRegistry;
import net.re_renderreality.rrrp2.main.Registry;
import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Avarai
 * @note Plans: Add nickname support and formerly known as support.
 */
public class WhoisCommand{
	
	private CommandSource src;
	private CommandContext args;
	
	/**
	 * @param src CommandSource object of command source.
	 * @param args Arguments given to command
	 */
	public WhoisCommand(CommandSource src, CommandContext args) { this.src = src; this.args = args; }
	
	public void run() {
		
		PlayerRegistry register = Registry.getPlugin().getPlayerRegistry();
		String name = args.<String>getOne("Player").get();
		Text status = Text.of("");
		Text lastSeen = Text.of("");
		
		if (register.containsPlayer(name)) {
			if (Utilities.getPlayer(name).isPresent()) {
				status = Text.builder(Utilities.boolToString(true)).color(TextColors.GREEN).build();
				String time = LocalTime.now().toString();
				lastSeen = Text.builder(LocalDate.now().toString() + " " + time.substring(0, time.indexOf('.'))).color(TextColors.BLUE).build();
			}
			else {
				status = Text.builder(Utilities.boolToString(false)).color(TextColors.RED).build();
				String time = register.getTime(register.getUuid(name));
				if (!time.isEmpty())
					lastSeen = Text.builder(time).color(TextColors.BLUE).build();
				else
					lastSeen = Text.builder("Not Seen").color(TextColors.BLUE).build();
			}
			src.sendMessage(Text.joinWith(Text.of(""), Text.of(name + " -- Online: "), status, Text.of(" Last seen: ", lastSeen)));
		}
		else {
			src.sendMessage(Text.of("That player has not been seen on the server."));
			return;
		}
	}
}