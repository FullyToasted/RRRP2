package net.re_renderreality.rrrp2.cmd.cheats;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import net.re_renderreality.rrrp2.backend.CommandExecutorBase;
import net.re_renderreality.rrrp2.database.Registry;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SpeedCommand extends CommandExecutorBase {
	private String name;
	private String description;
	private String perm;
	private String useage;
	private String notes;
	
	protected void setLocalVariables() {
		name = "/speed";
		description = "Alters the targets speed";
		perm = "rrr.cheat.speed.self";
		useage = "/speed <speed> (player)";
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
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException {
		setLocalVariables();
		Optional<Player> optionalTarget = ctx.<Player> getOne("player");
		int multiplier = ctx.<Integer> getOne("speed").get();

		//updates flying speed key or walking speed key if on the ground
		if (!optionalTarget.isPresent()) {
			if (src instanceof Player) {
				Player player = (Player) src;
				multiplier = Math.min(multiplier, 20);

				if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get())	{
					double flySpeed = 0.05d * multiplier;
					player.offer(Keys.FLYING_SPEED, flySpeed);
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your flying speed has been updated."));
				} else {
					double walkSpeed = 0.1d * multiplier;
					player.offer(Keys.WALKING_SPEED, walkSpeed);
					src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your walking speed has been updated."));
				}
			} else {
				src.sendMessage(Text.of(TextColors.DARK_RED, "Error!", TextColors.RED, "You must be a player to do /speed"));
			}
		} else if(src.hasPermission("rrr.cheat.speed.others")) {
			Player player = optionalTarget.get();
			multiplier = Math.min(multiplier, 20);

			if (player.get(Keys.IS_FLYING).isPresent() && player.get(Keys.IS_FLYING).get()) {
				double flySpeed = 0.05d * multiplier;
				player.offer(Keys.FLYING_SPEED, flySpeed);
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your flying speed has been updated."));
			} else {
				double walkSpeed = 0.1d * multiplier;
				player.offer(Keys.WALKING_SPEED, walkSpeed);
				player.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Your walking speed has been updated."));
			}
			
			src.sendMessage(Text.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Updated player's speed."));
		}

		return CommandResult.success();
	}

	@Nonnull
	@Override
	public String[] getAliases() {
		return new String[] { "speed", "Speed" };
	}
	
	@Nonnull
	@Override
	public Registry.helpCategory getHelpCategory()
	{
		return Registry.helpCategory.Cheater;
	}

	@Nonnull
	@Override
	public CommandSpec getSpec() {
		return CommandSpec.builder()
				.description(Text.of(description))
				.permission(perm)
				.arguments(GenericArguments.seq(
						GenericArguments.onlyOne(GenericArguments.integer(Text.of("speed"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))))))
				.executor(this)
				.build();
	}
}

