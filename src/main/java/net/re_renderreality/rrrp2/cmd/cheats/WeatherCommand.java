package net.re_renderreality.rrrp2.cmd.cheats;

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
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.weather.Weather;
import org.spongepowered.api.world.weather.Weathers;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;

public class WeatherCommand extends CommandExecutorBase
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Optional<Integer> weatherNum = ctx.<Integer> getOne("weather");
		Optional<Integer> duration = ctx.<Integer> getOne("duration");

		if (src instanceof Player) {
			Player player = (Player) src;
			int numWeather = weatherNum.get();
			Weather weather;
			
			if (numWeather == 1) {
				weather = Weathers.CLEAR;
				player.sendMessage(Text.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "sunny."));
			} else if (numWeather == 2) {
				weather = Weathers.RAIN;
				player.sendMessage(Text.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "rain."));
			} else if (numWeather == 3) {
				weather = Weathers.THUNDER_STORM;
				player.sendMessage(Text.of(TextColors.GOLD, "Changing weather to ", TextColors.GRAY, "storm."));
			} else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Input invalid! /weather <clear|rain|storm> (duration)"));
				return CommandResult.success();
			}

			if (duration.isPresent()) {
				player.getWorld().setWeather(weather, duration.get());
			}
			else {
				player.getWorld().setWeather(weather);
			}
			return CommandResult.success();
		} else {
			src.sendMessage(Text.of(TextColors.RED, "ERROR! You must be a in-game player to do /weather!"));
			return CommandResult.empty();
		}
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "weather", "weather" };
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("clear", 1);
		map.put("rain", 2);
		map.put("storm", 3);
		return CommandSpec
				.builder()
				.description(Text.of("Weather Command"))
				.permission("rrr.cheat.weather")
				.arguments(GenericArguments.seq(GenericArguments.choices(Text.of("Command"), map)),
							GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.integer(Text.of("duration")))))
				.executor(this)
				.build();
	}
}
