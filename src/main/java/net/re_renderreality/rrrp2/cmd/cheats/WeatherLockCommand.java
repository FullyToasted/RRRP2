package net.re_renderreality.rrrp2.cmd.cheats;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import net.re_renderreality.rrrp2.api.util.config.readers.ReadConfig;
import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

public class WeatherLockCommand extends CommandExecutorBase
{
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/lockweather";
		description = "Locks World's weather";
		perm = "rrr.cheat.weatherlock";
		useage = "/weatherlock";
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
	
	private Game game = Registry.getGame();

	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		String name = ctx.<String> getOne("name").get();

		Optional<World> optWorld = game.getServer().getWorld(name);

		//locks a worlds weather by adding the world name to a config list. Listener handles rest.
		if (optWorld.isPresent()) {
			World world = optWorld.get();

			if (ReadConfig.getLockedWeatherWorlds().contains(world.getUniqueId().toString())) {
				src.sendMessage(Text.of(TextColors.GOLD, "Un-locked weather in world: " + world.getName()));
				ReadConfig.removeLockedWeatherWorld(world.getUniqueId());
			} else {
				src.sendMessage(Text.of(TextColors.GOLD, "Locked weather in world: " + world.getName()));
				ReadConfig.addLockedWeatherWorld(world.getUniqueId());
			}
		} else {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "World specified does not exist!"));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "lockweather", "killweather" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Cheater;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec()
	{
		return CommandSpec.builder()
			.description(Text.of(description))
			.permission(perm)
			.arguments(GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("name"))))
			.executor(this).build();
	}
}
