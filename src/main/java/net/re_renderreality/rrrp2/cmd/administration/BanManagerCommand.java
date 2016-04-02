package net.re_renderreality.rrrp2.cmd.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Database;
import net.re_renderreality.rrrp2.database.core.BanCore;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

public class BanManagerCommand extends CommandExecutorBase {

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Integer> oCommand = ctx.<Integer> getOne("Command");
		Optional<Integer> OID = ctx.<Integer> getOne("BanID");
		
		if(src instanceof Player || src instanceof ConsoleSource) {
			if(OID.isPresent() && oCommand.isPresent()) {
				int command = oCommand.get();
				int id = OID.get();
				if(command == 1 || command == 2) {
					BanCore ban = Database.getOneBan(id);
					if(command == 1) {
						src.sendMessage(Text.of(TextColors.GOLD, "-----------------------------------------------------\n",
										TextColors.GOLD,  "Ban ID: ", TextColors.GRAY, ban.getID() + "\n", 
										TextColors.GOLD, "Player: ", TextColors.GRAY, ban.getbannedName() + "\n", 
										TextColors.GOLD, "Banner: ", TextColors.GRAY, ban.getSender() + "\n", 
										TextColors.GOLD, "Reason: ", TextColors.GRAY, ban.getReason() + "\n",
										TextColors.GOLD, "Banned Time: ", TextColors.GRAY, ban.getTime() + "\n",
										TextColors.GOLD, "Duration: ", TextColors.GRAY, ban.getDuration() + "\n",
										TextColors.GOLD, "-----------------------------------------------------"));
						CommandResult.success();
					} else if (command == 2) {//TODO:
						PlayerCore playercore = Database.getPlayerCore(id);
						User players = null;
						UserStorageService uss = Sponge.getGame().getServiceManager().provide(UserStorageService.class).get();
						if(playercore.getUUID().equals("uuid")) {
							src.sendMessage(Text.of(TextColors.RED, "This Player has never joined the server"));
							return CommandResult.empty();
						}
						Optional<User> ogp = uss.get(UUID.fromString(playercore.getUUID()));
						if (ogp.isPresent())
						{
							players = ogp.get();
						}
						BanService srv = RRRP2.getRRRP2().getGame().getServiceManager().provide(BanService.class).get();
						
						if (!playercore.getBanned()) {
							src.sendMessage(Text.of(TextColors.RED, "That player is not currently banned."));
							return CommandResult.empty();
						}
						
						srv.removeBan(srv.getBanFor(players.getProfile()).get());
						ban.delete();
						playercore.setBannedUpdate(false);
						Database.execute("DELETE FROM bans WHERE ID = " + id + ";");
						src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, playercore.getName() + " has been unbanned."));
						return CommandResult.success();
						
					}
				} else {
					ArrayList<BanCore> bans = new ArrayList<BanCore>();
					bans = Database.getBans();
					ArrayList<Text> text = new ArrayList<Text>();
					for(BanCore b : bans) {
						text.add(Text.of(TextColors.GOLD, "Ban ID: ", TextColors.GRAY, b.getID(), TextColors.GOLD, " Player: ", TextColors.GRAY, b.getbannedName(), TextColors.GOLD, " Banner: ", TextColors.GRAY, b.getSender()));
					}
					Iterable<Text> completedText = text;
					sendPagination(completedText, src);
					CommandResult.success();
				}
			} else if(!oCommand.isPresent()){
				ArrayList<BanCore> bans = new ArrayList<BanCore>();
				bans = Database.getBans();
				ArrayList<Text> text = new ArrayList<Text>();
				for(BanCore b : bans) {
					text.add(Text.of(TextColors.GOLD, "Ban ID: ", TextColors.GRAY, b.getID(), TextColors.GOLD, " Player: ", TextColors.GRAY, b.getbannedName(), TextColors.GOLD, " Banner: ", TextColors.GRAY, b.getSender()));
				}
				Iterable<Text> completedText = text;
				sendPagination(completedText, src); 
				return CommandResult.success();
			} else {
				src.sendMessage(Text.of(TextColors.RED, "Error! Correct Useage /ManageBans <Command> <BanID>"));
				return CommandResult.empty();
			}
		} else {
			src.sendMessage(Text.of(TextColors.RED, "Error! Must be a Player to issue this command"));
			return CommandResult.empty();
		}
		return CommandResult.empty();
	}

	private void sendPagination(Iterable<Text> bans, CommandSource src) {
		Utilities.getPaginationService().builder()
	    	.title(Text.of(TextColors.GOLD, "Banned Player List"))
	    	.contents(bans)
	    	.footer(Text.of(TextColors.GREEN, "To veiw a ban type /Banmanager view <BanID>"))
	    	.sendTo(src);
	}
	
	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "ManageBans", "Managebans", "managebans", "manageBans", "BanManager", "banManager", "Banmanager", "banmanager"};
	}
	
	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("view", 1);
		map.put("delete", 2);
		return CommandSpec.builder()
			.description(Text.of("Read Mail that has been received"))
			.permission("rrr.admin.ban.manage")
			.arguments(GenericArguments.optional(GenericArguments.choices(Text.of("Command"), map)),
						GenericArguments.optional(GenericArguments.integer(Text.of("BanID"))))
			.executor(this)
			.build();
	}
}
