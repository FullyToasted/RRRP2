package net.re_renderreality.rrrp2.cmd.teleport.admintp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.RRRP2;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;
import net.re_renderreality.rrrp2.database.core.PlayerCore;
import net.re_renderreality.rrrp2.utils.Utilities;

//force tp to a player
public class TeleportCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/tpo";
		description = "Force a teleport";
		perm = "rrr.admin.tpo";
		useage = "/tpo <player2tp> (target)";
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
		Optional<Player> optionalPlayer = ctx.<Player> getOne("player");
		Optional<Player> optionalTarget = ctx.<Player> getOne("target");

		if (optionalPlayer.isPresent())	{
			Player player = optionalPlayer.get();
			PlayerCore playercore = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(player.getName());
			Player s = (Player) src;
			PlayerCore source = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(s.getName());

			if (optionalTarget.isPresent())	{
				if (src.hasPermission("rrr.admin.tpo.others")) {
					playercore.setLastlocationUpdate(Utilities.convertLocation(player));
					player.setLocation(optionalTarget.get().getLocation());
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported player " + player.getName() + " to " + optionalTarget.get().getName()));
					player.sendMessage(Text.of(TextColors.GOLD, "You have been teleported to " + optionalTarget.get().getName() + " by " + src.getName()));
				}
				else {
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to teleport other players."));
				}
			}
			else {
				if (src instanceof Player) {
					Player targ = (Player) src;
					source.setLastlocationUpdate(Utilities.convertLocation(s));
					targ.setLocation(player.getLocation());
					targ.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to player " + player.getName()));
				}
				else {
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
				}
			}
		}
		else {
			int x = ctx.<Integer> getOne("x").get();
			int y = ctx.<Integer> getOne("y").get();
			int z = ctx.<Integer> getOne("z").get();
			Optional<String> optionalWorld = ctx.<String> getOne("world");
			Player target = null;

			if (optionalTarget.isPresent() && src.hasPermission("rrr.admin.tpo.others")) {
				target = optionalTarget.get();
			} else if (optionalTarget.isPresent()) {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You do not have permission to teleport other players."));
				return CommandResult.success();
			}
			else {
				if (src instanceof Player) {
					target = (Player) src;
				}
				else {
					src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot teleport, you are not a player!"));
					return CommandResult.success();
				}
			}

			if (!optionalWorld.isPresent()) {
				Location<World> location = new Location<>(target.getWorld(), x, y, z);
				PlayerCore playerTarget = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(target.getName());
				playerTarget.setLastlocationUpdate(Utilities.convertLocation(target));
				target.setLocation(location);
				target.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to location!"));
			}
			else {
				Optional<World> world = Sponge.getServer().getWorld(optionalWorld.get());

				if (world.isPresent()) {
					Location<World> location = new Location<>(world.get(), x, y, z);

					if (!target.getWorld().getUniqueId().equals(world.get().getUniqueId())) {
						PlayerCore playerTarget = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(target.getName());
						playerTarget.setLastlocationUpdate(Utilities.convertLocation(target));
						target.transferToWorld(world.get().getUniqueId(), location.getPosition());
					}
					else {
						PlayerCore playerTarget = RRRP2.getRRRP2().getOnlinePlayer().getPlayerCorefromUsername(target.getName());
						playerTarget.setLastlocationUpdate(Utilities.convertLocation(target));
						target.setLocation(location);
					}

					target.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Teleported to location!"));
				}
				else {
					target.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World not found!"));
				}
			}
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "tpo", "TPO" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Teleport;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec
			.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.firstParsing(
				GenericArguments.seq(
					GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))), 
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("target"))))), 
				GenericArguments.seq(
					GenericArguments.onlyOne(GenericArguments.integer(Text.of("x"))),
					GenericArguments.onlyOne(GenericArguments.integer(Text.of("y"))),
					GenericArguments.onlyOne(GenericArguments.integer(Text.of("z"))), 
					GenericArguments.optional(GenericArguments.string(Text.of("world"))),
					GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))))))
			.executor(this)
			.build();
	}
}
