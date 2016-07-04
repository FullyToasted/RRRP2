package net.re_renderreality.rrrp2.cmd.home;

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
import net.re_renderreality.rrrp2.database.core.HomeCore;
public class SetHomeCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/sethome";
		description = "Sets a player home";
		perm = "rrr.general.home";
		useage = "/sethome (name)";
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
	
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<String> homeName = ctx.<String> getOne("homeName");
		
		if(src instanceof Player) {
			Player source = (Player) src;
			String name = "main";
			
			if(homeName.isPresent()) {
				name = homeName.get();
			}
			
			HomeCore home = new HomeCore();
			if(Database.getHomeExist(source, name)) {
				home = Database.getHome(source, name);
				sethome(home, source);
				home.update();
				src.sendMessage(Text.of(TextColors.GOLD, "Home Overriden!"));
				return CommandResult.success();
			}
			int nextID = Database.findNextID("homes");
			int playerHomeCount = Database.getPlayerHomeCount(source.getName());
			
			//Makes sure that a player doesn't set more homes then they are allowed
			int possible = 0;
			for(int i = 1; i <= 100; i++) {
				if(source.hasPermission("rrr.general.homes." + i)) {
					possible = i;
				}
			}

			//show this if player has reached their max limit on homes
			if(!source.hasPermission("rrr.general.unlimitedhomes") && possible <= playerHomeCount) {
				if(possible == 1) {
					source.sendMessage(Text.builder("You are only allowed to own " + possible + " home").color(TextColors.RED).build());
				} else {
					source.sendMessage(Text.builder("You are only allowed to own " + possible + " homes").color(TextColors.RED).build());
				}
				return CommandResult.empty();
			}
			
			//creates and inserts homecore object
			home.setID(nextID);
			home.setUsername(source.getName());
			home.setUUID(source.getUniqueId().toString());
			home.setHomeName(name);
			sethome(home, source);
			home.insert();
			int left = possible - playerHomeCount - 1;
			src.sendMessage(Text.of(TextColors.GOLD, "A new home has been set! Remaining: ", TextColors.GRAY, left));
			return CommandResult.success();
		}
		return CommandResult.empty();
	}

	private void sethome(HomeCore home, Player source) {
		home.setWorld(source.getWorld().getName());
		home.setX(source.getTransform().getLocation().getX());
		home.setY(source.getTransform().getLocation().getY());
		home.setZ(source.getTransform().getLocation().getZ());
		home.setYaw(source.getTransform().getYaw());
		home.setPitch(source.getTransform().getPitch());	
		home.setRoll(source.getTransform().getRoll());
	}
	
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "SetHome", "Sethome", "setHome", "sethome" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.General;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("homeName")))))
				.executor(this).build();
	}
}
