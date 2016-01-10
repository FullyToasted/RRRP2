package net.re_renderreality.rrrp2.cmd;

import net.re_renderreality.rrrp2.utils.Utilities;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Avarai
 * @note Plans: To extend command to add "Last seen"
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
		
		ArrayList<String> players = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader("players.rrr"));
			while (reader.ready()) {
				String line = reader.readLine();
				players.add(line.substring(0, line.indexOf(':')));
			}
			reader.close();
		} catch (Exception e) {	e.printStackTrace(); }
		
		String name = args.<String>getOne("Player").get();
		Text status = Text.of("");
		
		if (!players.contains(name)) {
			src.sendMessage(Text.of("That player has not been seen on the server."));
			return;
		}
		if (Utilities.getPlayer(name).isPresent())
			status = Text.builder(Utilities.boolToString(true)).color(TextColors.GREEN).build();
		else
			status = Text.builder(Utilities.boolToString(false)).color(TextColors.RED).build(); 
		src.sendMessage(Text.joinWith(Text.of(""), Text.of(name + " -- Online: "), status));
	}
}